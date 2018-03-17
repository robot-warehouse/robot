package rp.assignments.team.warehouse.robot.communications;

import java.util.List;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import rp.assignments.team.warehouse.robot.RobotController;

public class RobotCommunicationsManager {

    private RobotController controller;
    private RobotReceiver receiver;
    private RobotSender sender;

    private boolean robotAtPickUpLocation = true;
    private boolean robotAtDropOutLocation = false;
    private boolean connected;

    public RobotCommunicationsManager() {
        try {
            this.connected = false;
            System.out.println("Waiting for connection");
            NXTConnection connection = Bluetooth.waitForConnection();

            this.connected = true;
            System.out.println("Connected");

            receiver = new RobotReceiver(connection.openDataInputStream(),this);
            sender = new RobotSender(connection.openDataOutputStream());

            receiver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setController(RobotController controller) {
        this.controller = controller;
    }

    /**
     * Returns whether the server is still connected
     *
     * @return boolean
     */
    public boolean isConnected() {
        return receiver.getConnected();
    }

    /**
     * Send the current position of the robot back to the server
     *
     * @param x X co-ordinate of robot
     * @param y Y co-ordinate of robot
     */
    public void sendPosition(int x, int y) {
        sender.sendPosition(x, y);
    }

    /**
     * Returns a list of orders which have been sent from the server
     *
     * @return A list of orders sent from the server. Returns empty if no orders have been sent
     */
    public List<Integer> getOrders() {
        return receiver.getOrders();

    }

    /**
     * Returns the number of picks that the robot should take
     *
     * @return The number of picks the robot should take.
     */
    public int getNumOfPicks() {
        return receiver.getNumOfPicks();
    }

    /**
     * Returns whether the current job has been cancelled.s
     *
     * @return true if the current job is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return receiver.isCancelled();
    }
    
    public void attemptReconnect() {
    	this.connected = false;
    	System.out.println("PC closed the connection, waiting for new connection...");
    	NXTConnection connection = Bluetooth.waitForConnection();
    	if(connection.openDataInputStream() != null) {
    		System.out.println("Reconnected to PC");
        	receiver.setDataInputStream(connection.openDataInputStream());
        	receiver.setConnected(true);
        	this.connected = true;
    	}
    	else {
    		System.out.println("Could not reconnect to the server");
    	}
    	
    }

    //------------------------------------------------------------------

    /**
     * Sends to the server that the current job has been completed
     */
    public void sendDone() {
        sender.sendDone();
    }

    public void resetOrders() {
        receiver.resetOrders();
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
