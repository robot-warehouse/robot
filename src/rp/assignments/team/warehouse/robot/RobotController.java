package rp.assignments.team.warehouse.robot;

import java.util.List;
import java.util.Queue;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;
import rp.assignments.team.warehouse.robot.gui.IRobotInterface;
import rp.assignments.team.warehouse.robot.gui.RobotInterface;
import rp.assignments.team.warehouse.robot.motioncontrol.IRobotMotionController;
import rp.assignments.team.warehouse.robot.motioncontrol.RobotMotionController;
import rp.assignments.team.warehouse.shared.Instruction;

public class RobotController {
    
    /** The number of milliseconds to wait when executing a {@link Instruction#STOP}. */
    public static final int STOP_WAIT_TIME = 0;

    /** The maximum weight a robot can carry at any one time */
    private final int MAXIMUM_WEIGHT = 50;

    /** Reference to communications manager */
    private RobotCommunicationsManager communicationsManager;

    /** Reference to motion controller */
    private IRobotMotionController robotMotionController;

    /** Reference to interface */
    private IRobotInterface robotInterface;

    /** Queue of instructions sent from the server */
    private Queue<Instruction> instructionQueue;

    /** The current total weight of the items the robot is carrying */
    private int currentWeight;

    /** The number of items to pick up for the current pick */
    private int currentPickCount;

    /** The current number of items the robot is carrying */
    private int currentCarryingCount;

    /** Flag for whether a job has been cancelled */
    private boolean cancelledJob;

    /**
     * Initialises RobotController, setting up robot motion controller and robot interface classes
     *
     * @param communicationsManager Instance of the communications manager to send messages to the server through
     */
    public RobotController(RobotCommunicationsManager communicationsManager) {
        this.communicationsManager = communicationsManager;

        this.robotMotionController = new RobotMotionController();
        this.robotInterface = new RobotInterface();

        this.instructionQueue = new Queue<>();
        this.currentWeight = 0;
    }

    /**
     * The loop for the robot to run through while running in the warehouse
     */
    public void startRunningRobot() {
        while (this.communicationsManager.isConnected()) {

            assert this.currentWeight <= this.MAXIMUM_WEIGHT;

            if (this.cancelledJob) {
                this.instructionQueue = new Queue<>();
                this.cancelledJob = false;
                this.currentCarryingCount = 0;
            }

            if (!this.instructionQueue.isEmpty()) {
                Instruction instruction = (Instruction) this.instructionQueue.pop();

                switch (instruction) {
                    case FORWARDS:
                        this.robotMotionController.moveForwards();
                        break;
                    case LEFT:
                        this.robotMotionController.takeLeftExit();
                        this.robotMotionController.moveForwards();
                        break;
                    case RIGHT:
                        this.robotMotionController.takeRightExit();
                        this.robotMotionController.moveForwards();
                        break;
                    case BACKWARDS:
                        this.robotMotionController.takeRearExit();
                        this.robotMotionController.moveForwards();
                        break;
                    case STOP:
                        this.robotMotionController.holdUp();
                        break;
                    case PICKUP:
                        this.robotInterface.pickUpAmountInLocation(this.currentPickCount);
                        this.communicationsManager.sendDone();
                        break;
                    case DROPOFF:
                        this.robotInterface.dropOffAmountInLocation(this.currentCarryingCount);
                        this.communicationsManager.sendDone();
                        break;
                }
            }
        }
    }

    /**
     * Adds an instruction object to the instruction queue
     *
     * @param instruction The instruction object to add to the queue
     */
    public void addInstructionToQueue(Instruction instruction) {
        this.instructionQueue.push(instruction);
    }

    /**
     * Adds a list of instruction objects to the instruction queue in order of the list
     *
     * @param instructions The list
     */
    public void addInstructionToQueue(List<Instruction> instructions) {
        for (Instruction i : instructions) {
            this.instructionQueue.push(i);
        }
    }

    /**
     * Flags the current job as cancelled
     */
    public void cancelJob() {
        this.cancelledJob = true;
    }
}
