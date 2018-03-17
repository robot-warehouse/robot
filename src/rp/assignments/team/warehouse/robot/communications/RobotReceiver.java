package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Receives messages from the server
 */
public class RobotReceiver extends Thread {

	private DataInputStream fromServer;
	private RobotCommunicationsManager communicationsManager;

	// temporary until type of orders known
	private List<Integer> orderQueue;
	private int numOfPicks;
	private boolean jobCancelled;
	private boolean connected;
	
	public RobotReceiver(DataInputStream fromServer, RobotCommunicationsManager manager) {
		this.fromServer = fromServer;
		this.communicationsManager = manager;
		connected = true;
		orderQueue = new ArrayList<>();
		numOfPicks = 0;
	}

	@Override
	public void run() {
		while (true) {
			if (connected) {
				try {
					String s = fromServer.readUTF();
					Command command = Command.strToCommand(s);
					switch (command) {
					case CANCEL:
						jobCancelled = true;
						break;
					case SEND_ORDERS:
						orderQueue.clear();
						List<Integer> tempCommands = new ArrayList<Integer>();
						String val = fromServer.readUTF();
						while (!val.equals("-1")) {
							tempCommands.add((Integer.parseInt(val)));
							val = fromServer.readUTF();
						}
						System.out.println("New orders sent");
						System.out.println(tempCommands.toString());
						orderQueue.addAll(tempCommands);
						jobCancelled = false;
						break;
					case SEND_PICKS:
						numOfPicks = Integer.valueOf(fromServer.readUTF());
						break;
					case DISCONNECT:
						System.out.println("Disconnected");
						communicationsManager.attemptReconnect();
						break;
					default:
						System.out.println("Unrecognised command");
						break;
					}
				} catch (IOException e) {
					System.out.println("Something went wrong with the server");
					break;
				}
			}

		}
	}

	public List<Integer> getOrders() {
		return orderQueue;
	}
	
	public void setDataInputStream(DataInputStream fromServer) {
		this.fromServer = fromServer;
	}

	public void resetOrders() {
		orderQueue = new ArrayList<>();
	}

	public int getNumOfPicks() {
		return numOfPicks;
	}

	public boolean isCancelled() {
		return jobCancelled;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public boolean getConnected() {
		return connected;
	}
}
