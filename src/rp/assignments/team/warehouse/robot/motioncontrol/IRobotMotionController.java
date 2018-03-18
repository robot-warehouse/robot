package rp.assignments.team.warehouse.robot.motioncontrol;

public interface IRobotMotionController {

    /**
     * Moves the robot fowards to the next junction
     */
    public void moveForwards();

    /**
     * Rotates the robot 90 degrees to the left
     */
    public void takeLeftExit();

    /**
     * Rotates the robot 90 degrees to the right
     */
    public void takeRightExit();

    /**
     * Rotates the robot 180 degrees
     */
    public void takeRearExit();

    /**
     * Stops the robot at the junction for a specified time
     */
    public void holdUp();

}
