package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;

/**
 * @author Qasim Nawaz
 */
public class Path {

    protected List<Integer> pathList = new ArrayList<Integer>();
    RobotCommunicationsManager rm;

    public Path(RobotCommunicationsManager rm) {
        this.rm = rm;
        while (this.pathList.isEmpty()) {
            this.pathList.addAll(rm.getOrders());
        }
    }

    //Returns the pathList
    public List<Integer> getPathList() {
        return this.pathList;
    }

    public void refreshPath() {
        this.pathList.clear();
        while (this.pathList.isEmpty()) {
            this.pathList.addAll(this.rm.getOrders());
        }

        System.out.println("New path: " + this.pathList.toString());
    }
}
