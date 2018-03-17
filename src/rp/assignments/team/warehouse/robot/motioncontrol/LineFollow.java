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
import rp.assignments.team.warehouse.robot.communications.RobotManager;
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
    private RobotManager manager;
    private ListIterator<Integer> listIterate;
    private int pickNumber;
    private List<Integer> instructionSet;

    public LineFollow(DifferentialPilot DP, SensorPort port1, SensorPort port2, SensorPort port4, RobotCommunicationsManager commsManager) {
        this.DP = DP;
        robotCommunicationsManager = commsManager;
        manager = new RobotManager();
        path = new Path(robotCommunicationsManager);
        picks = new Picks(robotCommunicationsManager);
        light1 = new LightController(port1);
        light2 = new LightController(port2);
        light4 = new LightController(port4);
        gui = new rbtInterface(robotCommunicationsManager);
        instructionSet = new ArrayList<>();
    }

    @Override
    public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {}

    public boolean junctionReached(int juncValue) {
        return light1.getLightValue() <= juncValue && light4.getLightValue() <= juncValue;
    }

    public void getAction(int currentAction) {
        //Turns Left
        if (currentAction == 0) {
            DP.travel(8);
            DP.rotate(-90);
        }

        //Turns Right
        else if (currentAction == 1) {
            DP.travel(8);
            DP.rotate(90);
        }

        //Straight
        else if (currentAction == 2) {
            DP.travel(8);
        }

        //Backwards
        else if (currentAction == 3) {
            DP.travel(8);
            DP.rotate(180);
        }
            
        //Stop    
        else if (currentAction == 4) {
            robotCommunicationsManager.setRobotAtPickUpLocation(true);
            if (gui.getAmountToPickInLocation() == robotCommunicationsManager.getNumOfPicks()) {
                robotCommunicationsManager.setRobotAtDropOutLocation(true);
                robotCommunicationsManager.setRobotAtPickUpLocation(false);
            }

            gui.updateLCDScreen();
            System.out.println("finished stopping");
            System.out.println("Starting stopping");
            robotCommunicationsManager.resetOrders();
            robotCommunicationsManager.sendDone();
            path.refreshPath();
            instructionSet = path.getPathList();
            System.out.println(path.getPathList());
            listIterate = instructionSet.listIterator();
        }
    }

    @Override
    public void run() {
        DP.setTravelSpeed(150);
        PIDController pid = new PIDController(targetValue, light2, (float) DP.getTravelSpeed(), DP);
        int currentAction = 0;
        instructionSet = path.getPathList();
        listIterate = instructionSet.listIterator();
        System.out.println(instructionSet.get(0));
        while (m_run) {
            if (currentAction != 4) {
                pid.run();
                DP.forward();
                Motor.A.setSpeed(pid.rightSpeed);
                Motor.B.setSpeed(pid.leftSpeed);
            }
            Boolean check = junctionReached(junctionValue);
            if (check) {
                DP.stop();

                if (listIterate.hasNext()) {
                    currentAction = listIterate.next();
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
