package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RobotCommunicationsManager {
	private RobotReceiver receiver;
	private RobotSender sender;

	public RobotCommunicationsManager() {
		try {
			System.out.println("Waiting for connection");
			NXTConnection connection = Bluetooth.waitForConnection();
			System.out.println("Connected");
			receiver = new RobotReceiver(connection.openDataInputStream());
			sender = new RobotSender(connection.openDataOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Start the server, allowing it to send and receive messages.
	 */
	public void start() {
		receiver.start();
	}

	/**
	 * Send the current position of the robot back to the server
	 * 
	 * @param x
	 *            X co-ordinate of robot
	 * @param y
	 *            Y co-ordinate of robot
	 */
	public void sendPosition(int x, int y) {
		sender.sendPosition(x, y);
	}

	/**
	 * Returns a list of orders which have been sent from the server
	 * 
	 * @return A list of orders sent from the server. Returns empty if no orders
	 *         have been sent
	 */
	public List<Integer> getOrders() {
		return receiver.getOrders();

	}
	
	/**
	 * Returns the number of picks that the robot should take
	 * @return The number of picks the robot should take.
	 */
	public int getNumOfPicks() {
		return receiver.getNumOfPicks();
	}

	/**
	 * Returns whether the current job has been cancelled.s
	 * @return true if the current job is cancelled, false otherwise
	 */
	public boolean isCancelled() {
		return receiver.isCancelled();
	}
	
	/**
	 * Sends to the server that the current job has been completed
	 */
	public void sendDone() {
		sender.sendDone();
	}

}
