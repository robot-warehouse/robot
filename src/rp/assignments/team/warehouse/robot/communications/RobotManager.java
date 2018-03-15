package rp.assignments.team.warehouse.robot.communications;

public class RobotManager {

    boolean robotAtPickUpLocation = true;
    boolean robotAtDropOutLocation = false;
    int numberOfPicks;

    public int getNumberOfPicks() {
        return numberOfPicks;
    }

    public void setNumberOfPicks(int numberOfPicks) {
        this.numberOfPicks = numberOfPicks;
    }

    public boolean isRobotAtPickUpLocation() {
        return robotAtPickUpLocation;
    }

    public void setRobotAtPickUpLocation(boolean robotAtPickUpLocation) {
        this.robotAtPickUpLocation = robotAtPickUpLocation;
    }

    public boolean isRobotAtDropOutLocation() {
        return robotAtDropOutLocation;
    }

    public void setRobotAtDropOutLocation(boolean robotAtDropOutLocation) {
        this.robotAtDropOutLocation = robotAtDropOutLocation;
    }
}
