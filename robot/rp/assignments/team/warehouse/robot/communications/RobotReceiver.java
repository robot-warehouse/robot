package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.shared.communications.Command;

/*
 * Receives messages from the server
 */
public class RobotReceiver extends Thread {

	// temporary until type of orders known
	private List<Integer> orders;
	private boolean jobCancelled;
	private DataInputStream fromServer;


	public RobotReceiver(DataInputStream fromServer) {
		orders = new ArrayList<Integer>();
		this.fromServer = fromServer;
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
					orders.clear(); //remove previous orders
					String val = fromServer.readUTF();
					int i = 0;
					while (!val.equals("-1")) {
						i++;
						orders.add(Integer.parseInt(val));
						val = fromServer.readUTF();
					}
					System.out.println("Finished ");
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
		return orders;
	}

	public boolean isCancelled() {
		return jobCancelled;
	}

}
