package rp.assignments.team.warehouse.robot.motioncontrol;

public interface IRobotMotionController {
    public void moveForwards();

    public void turnLeft();

    public void turnRight();

    public void turnAround();

    public void stop();

}
