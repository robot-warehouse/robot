package rp.assignments.team.warehouse.robot.motioncontrol;

public interface IRobotMotionController {

    /**
     * Moves the robot fowards to the next junction
     */
    public void moveForwards();

    /**
     * Rotates the robot 90 degrees to the left
     */
    public void turnLeft();

    /**
     * Rotates the robot 90 degrees to the right
     */
    public void turnRight();

    /**
     * Rotates the robot 180 degrees
     */
    public void turnAround();

    /**
     * Stops the robot at the junction (for a specified time maybe?)
     */
    public void stop();

}
