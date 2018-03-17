package rp.assignments.team.warehouse.robot;

import java.util.List;
import java.util.Queue;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;
import rp.assignments.team.warehouse.robot.gui.IRobotInterface;
import rp.assignments.team.warehouse.robot.motioncontrol.IRobotMotionController;
import rp.assignments.team.warehouse.robot.motioncontrol.RobotMotionController;

public class RobotController {

    private final int MAXIMUM_WEIGHT = 50;

    private RobotCommunicationsManager communicationsManager;
    private IRobotMotionController robotMotionController;
    private IRobotInterface robotInterface;

    private Queue<Instruction> instructionQueue;

    private int currentWeight;
    private int currentPickCount;
    private int currentCarryingCount;

    private boolean hasPickedUpAllItems;
    private boolean hasDroppedOffAllItems;
    private boolean cancelledJob;

    public RobotController(RobotCommunicationsManager communicationsManager) {
        this.communicationsManager = communicationsManager;
        this.robotMotionController = new RobotMotionController(communicationsManager);

//        this.robotInterface = new RobotInterface();

        this.instructionQueue = new Queue<>();
        this.currentWeight = 0;
        this.hasPickedUpAllItems = false;
        this.hasDroppedOffAllItems = true;
    }

    public void run() {
        while (this.communicationsManager.isConnected()) {

            assert this.currentWeight <= this.MAXIMUM_WEIGHT;

            if (cancelledJob) {
                this.instructionQueue = new Queue<>();
                this.cancelledJob = false;
                this.currentCarryingCount = 0;
            }

            if (!instructionQueue.isEmpty()) {
                Instruction instruction = (Instruction) instructionQueue.pop();

                switch (instruction) {
                    case FORWARDS:
                        this.robotMotionController.moveForwards();
                        break;
                    case LEFT:
                        this.robotMotionController.turnLeft();
                        this.robotMotionController.moveForwards();
                        break;
                    case RIGHT:
                        this.robotMotionController.turnRight();
                        this.robotMotionController.moveForwards();
                        break;
                    case BACKWARDS:
                        this.robotMotionController.turnAround();
                        this.robotMotionController.moveForwards();
                        break;
                    case STOP:
                        this.robotMotionController.stop();
                        break;
                    case PICKUP:
                        robotInterface.pickUpAmountInLocation(currentPickCount);
                        this.communicationsManager.sendDone();
                        break;
                    case DROPOFF:
                        robotInterface.dropOffAmountInLocation(currentCarryingCount);
                        this.communicationsManager.sendDone();
                        break;
                }
            }
        }
    }

    public void addInstructionToQueue(Instruction instruction) {
        this.instructionQueue.push(instruction);
    }

    public void addInstructionToQueue(List<Instruction> instructions) {
        for (Instruction i : instructions) {
            this.instructionQueue.push(i);
        }
    }

    public void cancelJob() {
        this.cancelledJob = true;
    }
}
