package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.robotics.navigation.DifferentialPilot;

import rp.systems.RobotProgrammingDemo;
import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;
import rp.assignments.team.warehouse.robot.gui.rbtInterface;

/**
 * @author Qasim Nawaz
 */
public class LineFollow extends RobotProgrammingDemo implements SensorPortListener {

    private final int targetValue = 34; //NAMELESS 35; //TRIHARD 35; //JOHNCENA 34;
    private final int junctionValue = 37; //NAMELESS 34; //TRIHARD 34; //JOHNCENA 45;
    private DifferentialPilot DP;
    private LightController light1;
    private LightController light2;
    private LightController light4;
    private rbtInterface gui;
    private RobotCommunicationsManager robotCommunicationsManager;
    private Boolean isAtPickupLocation;
    private Path path;
    private Picks picks;
    private ListIterator<Integer> listIterate;
    private int pickNumber;
    private List<Integer> instructionSet;

    public LineFollow(DifferentialPilot DP, SensorPort port1, SensorPort port2, SensorPort port4,
                      RobotCommunicationsManager commsManager) {
        this.DP = DP;
        this.robotCommunicationsManager = commsManager;
        this.path = new Path(this.robotCommunicationsManager);
        this.picks = new Picks(this.robotCommunicationsManager);
        this.light1 = new LightController(port1);
        this.light2 = new LightController(port2);
        this.light4 = new LightController(port4);
        this.gui = new rbtInterface(this.robotCommunicationsManager);
        this.instructionSet = new ArrayList<>();
    }

    @Override
    public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {}

    public boolean junctionReached(int juncValue) {
        return this.light1.getLightValue() <= juncValue && this.light4.getLightValue() <= juncValue;
    }

    public void getAction(int currentAction) {
        //Turns Left
        if (currentAction == 0) {
            this.DP.travel(8);
            this.DP.rotate(-90);
        }

        //Turns Right
        else if (currentAction == 1) {
            this.DP.travel(8);
            this.DP.rotate(90);
        }

        //Straight
        else if (currentAction == 2) {
            this.DP.travel(8);
        }

        //Backwards
        else if (currentAction == 3) {
            this.DP.travel(8);
            this.DP.rotate(180);
        }

        //Stop    
        else if (currentAction == 4) {
            this.robotCommunicationsManager.setRobotAtPickUpLocation(true);
            if (this.gui.getAmountToPickInLocation() == this.robotCommunicationsManager.getNumOfPicks()) {
                this.robotCommunicationsManager.setRobotAtDropOutLocation(true);
                this.robotCommunicationsManager.setRobotAtPickUpLocation(false);
            }

            this.gui.updateLCDScreen();
            System.out.println("finished stopping");
            System.out.println("Starting stopping");
            this.robotCommunicationsManager.resetOrders();
            this.robotCommunicationsManager.sendDone();
            this.path.refreshPath();
            this.instructionSet = this.path.getPathList();
            System.out.println(this.path.getPathList());
            this.listIterate = this.instructionSet.listIterator();
        }
    }

    @Override
    public void run() {
        this.DP.setTravelSpeed(150);
        PIDController pid = new PIDController(this.targetValue, this.light2, (float) this.DP.getTravelSpeed(), this.DP);
        int currentAction = 0;
        this.instructionSet = this.path.getPathList();
        this.listIterate = this.instructionSet.listIterator();
        System.out.println(this.instructionSet.get(0));
        while (this.m_run) {
            if (currentAction != 4) {
                pid.run();
                this.DP.forward();
                Motor.A.setSpeed(pid.rightSpeed);
                Motor.B.setSpeed(pid.leftSpeed);
            }
            Boolean check = junctionReached(this.junctionValue);
            if (check) {
                this.DP.stop();

                if (this.listIterate.hasNext()) {
                    currentAction = this.listIterate.next();
                    getAction(currentAction);

                    if (currentAction == 4) {
                        System.out.println("Successfully stopped");
                        currentAction = 0;
                    }
                } else {
                    continue;
                }
            }
        }
    }
}
