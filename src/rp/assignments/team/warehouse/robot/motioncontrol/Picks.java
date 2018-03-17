package rp.assignments.team.warehouse.robot.motioncontrol;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;

public class Picks {

    protected int pickNumber;
    RobotCommunicationsManager rm;

    public Picks(RobotCommunicationsManager rm) {
        this.rm = rm;
        this.pickNumber = rm.getNumOfPicks();
    }

    // Returns the number of picks
    public int getPickNumber() {
        this.pickNumber = this.rm.getNumOfPicks();
        return this.pickNumber;
    }
}
