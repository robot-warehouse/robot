package rp.assignments.team.warehouse.robot;

import java.util.List;
import java.util.Queue;

import lejos.util.Delay;
import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;
import rp.assignments.team.warehouse.robot.gui.IRobotInterface;
import rp.assignments.team.warehouse.robot.gui.RobotInterface;
import rp.assignments.team.warehouse.robot.motioncontrol.IRobotMotionController;
import rp.assignments.team.warehouse.robot.motioncontrol.RobotMotionController;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.Instruction;

public class RobotController {

	/**
	 * The number of milliseconds to wait when executing a {@link Instruction#STOP}.
	 */
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

	/** The current location of the robot in the grid */
	private Location currentLocation;

	/** The current direction the robot is facing */
	private Facing currentFacing;

	/** The current total weight of the items the robot is carrying */
	private int currentWeight;

	/** The number of items to pick up for the current pick */
	private int currentPickCount;

	/** The current number of items the robot is carrying */
	private int currentCarryingCount;

	/** Flag for whether a job has been cancelled */
	private boolean cancelledJob;

	/**
	 * Initialises RobotController, setting up robot motion controller and robot
	 * interface classes
	 */
	public RobotController() {
		this.robotMotionController = new RobotMotionController();
		this.robotInterface = new RobotInterface();
		this.instructionQueue = new Queue<>();
		this.currentWeight = 0;
	}

	public void setPosition(int x, int y) {
		this.currentLocation = new Location(x, y);
	}

	public void setFacing(Facing f) {
		this.currentFacing = f;
		System.out.println("Set " + f);
	}

	/**
	 * Sets the instance of the communications manager
	 *
	 * @param communicationsManager
	 *            The instance of the communications manager
	 */
	public void setCommunicationsManager(RobotCommunicationsManager communicationsManager) {
		this.communicationsManager = communicationsManager;
	}

	/**
	 * Sets the current number of items for the robot to pick up
	 *
	 * @param pickCount
	 *            The number of items for the robot to pick up
	 */
	public void setCurrentPickCount(int pickCount) {
		this.currentPickCount = pickCount;
	}

	/**
	 * Adds a list of instruction objects to the instruction queue in order of the
	 * list
	 *
	 * @param instructions
	 *            The list
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

	/**
	 * The loop for the robot to run through while running in the warehouse
	 */
	public void startRunningRobot() {
		// TODO run localisation here?

		while (this.communicationsManager.isConnected()) {

			assert this.currentWeight <= this.MAXIMUM_WEIGHT;

			if (this.cancelledJob) {
				this.instructionQueue = new Queue<>();
				this.cancelledJob = false;
				this.currentCarryingCount = 0;
			}

			if (!this.instructionQueue.isEmpty()) {
				communicationsManager.sendLocation(currentLocation);
				
				Instruction instruction = (Instruction) this.instructionQueue.pop();
				System.out.println("New instruction popped");
				switch (instruction) {
				case FORWARDS:
					this.robotMotionController.moveForwards();
					this.currentLocation = getNewCurrentLocation();
					break;
				case LEFT:
					this.robotMotionController.takeLeftExit();
					this.currentFacing = this.currentFacing.turnLeft();
					this.currentLocation = getNewCurrentLocation();
					break;
				case RIGHT:
					this.robotMotionController.takeRightExit();
					this.currentFacing = this.currentFacing.turnRight();
					this.currentLocation = getNewCurrentLocation();
					break;
				case BACKWARDS:
					this.robotMotionController.takeRearExit();
					this.currentFacing = this.currentFacing.turnRight();
					this.currentFacing = this.currentFacing.turnRight();
					this.currentLocation = getNewCurrentLocation();
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
				this.communicationsManager.sendFacing(this.currentFacing);
				if(instructionQueue.isEmpty()) {
					this.communicationsManager.sendLocation(this.currentLocation);
				}
			
				
				Delay.msDelay(100);
				
			}
		}
	}

	/**
	 * Uses the current facing+ direction to figure out the new current location
	 *
	 * @return The new current location
	 */
	private Location getNewCurrentLocation() {
		switch (this.currentFacing) {
		case NORTH:
			return this.currentLocation.incrementY();
		case SOUTH:
			return this.currentLocation.decrementY();
		case EAST:
			return this.currentLocation.incrementX();
		case WEST:
			return this.currentLocation.decrementX();
		}

		return this.currentLocation;
	}
}
