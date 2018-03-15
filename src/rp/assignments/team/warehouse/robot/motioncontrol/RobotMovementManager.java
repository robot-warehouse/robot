package rp.assignments.team.warehouse.robot.motioncontrol;

import java.util.List;

/**
 * Class to manage the movements of the Robot
 *
 * @author Obaid Ur-Rahmaan
 */
public class RobotMovementManager {

    protected List<Integer> route;

    //private Path path;
    private boolean shouldExecuteRoute;
    private int numOfPicks;
    private boolean isRouteComplete;
    private boolean atPickup;

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

    public int getNumberOfPicks() {
        return this.numOfPicks;
    }

    public void setNumberOfPicks(int readInt) {
        this.numOfPicks = readInt;
    }

    public synchronized boolean getIsRouteComplete() {
        return isRouteComplete;
    }

    public synchronized void setIsRouteComplete(boolean value) {
        isRouteComplete = value;
    }

    public boolean getIsAtPickupLocation() {
        return this.atPickup;
    }

    public void setIsAtPickupLocation(boolean atPickup) {
        this.atPickup = atPickup;
    }
}
