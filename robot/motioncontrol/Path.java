package motioncontrol;

import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;

public class Path {

	private List<Integer> pathList = new ArrayList<Integer>();

	public Path() {
		RobotCommunicationsManager rm = new RobotCommunicationsManager();
		rm.start();
		while(pathList.isEmpty()) {
			pathList = rm.getOrders(); 	
		}
	}
	
	//Returns the pathList
	public List<Integer> getPathList() {
  		return pathList;
  	}
}