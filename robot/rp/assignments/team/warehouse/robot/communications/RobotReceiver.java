package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import shared.communications.Command;

/*
 * Receives messages from the server
 */
public class RobotReceiver extends Thread {

	// temporary until type of orders known
	private List<Integer> orderQueue;
	private int numOfPicks;
	private boolean jobCancelled;
	private DataInputStream fromServer;


	public RobotReceiver(DataInputStream fromServer) {
		orderQueue = new ArrayList<Integer>();
		this.fromServer = fromServer;
		numOfPicks = 0;
	}
	
	@Override
	public void run() {
		while (true) {
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
					orderQueue.addAll(tempCommands);
					jobCancelled = false;
					break;
				case SEND_PICKS:
					numOfPicks = Integer.valueOf(fromServer.readUTF());
					break;
				default:
					System.out.println("Unrecognised command");
					break;
				}

			} catch (IOException e) {
				System.err.println("Something went wrong with the server");
				e.printStackTrace();
				break;
			}

		}
	}

	public List<Integer> getOrders() {
		return orderQueue;
	}
	
	public int getNumOfPicks() {
		return numOfPicks;
	}

	public boolean isCancelled() {
		return jobCancelled;
	}

}
