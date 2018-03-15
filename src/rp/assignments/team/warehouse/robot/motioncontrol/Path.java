package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;


public class Path {

    protected List<Integer> pathList = new ArrayList<Integer>();
    RobotCommunicationsManager rm;

    public Path(RobotCommunicationsManager rm) {
        this.rm = rm;
        while (pathList.isEmpty()) {
            pathList.addAll(rm.getOrders());
        }
    }

    //Returns the pathList
    public List<Integer> getPathList() {
        return pathList;
    }

    public void refreshPath() {
        pathList.clear();
        while (pathList.isEmpty()) {
            pathList.addAll(rm.getOrders());
        }

        System.out.println("New path: " + pathList.toString());
    }
}
