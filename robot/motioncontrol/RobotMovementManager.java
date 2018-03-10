package motioncontrol;


import java.util.ArrayList;
import java.util.List;

import utils.Direction;

/**
 * Class to manage the movements of the Robot
 * @author Obaid Ur-Rahmaan
 *
 */
public class RobotMovementManager {

	//private Path path;
	private boolean shouldExecuteRoute;
	private int numOfPicks;
	private boolean isRouteComplete;
	private boolean atPickup;
	
	protected List<Integer> route;

	public RobotMovementManager() {
		//path = new Path();
		//route = path.getPathList();
		shouldExecuteRoute = false;
		numOfPicks = 0;
		isRouteComplete = false;
		atPickup = false;
	}

//	public synchronized List<Integer> getRoute() {
//		return route;
//	}

//	public synchronized void setShouldExecuteRoute(boolean value) {  It is done by A* and Route execution
//		shouldExecuteRoute = value;
//	}
//
//	public synchronized boolean getShouldExecuteRoute() {
//		return shouldExecuteRoute;
//	}

	public void setNumberOfPicks(int readInt) {
		this.numOfPicks = readInt;
	}

	public int getNumberOfPicks() {
		return this.numOfPicks;
	}
	
	public synchronized void setIsRouteComplete(boolean value) {
		isRouteComplete = value;
	}

	public synchronized boolean getIsRouteComplete() {
		return isRouteComplete;
	}

	public void setIsAtPickupLocation(boolean atPickup) {
		this.atPickup = atPickup;
	}
	
	public boolean getIsAtPickupLocation(){
		return this.atPickup;
	}

}
