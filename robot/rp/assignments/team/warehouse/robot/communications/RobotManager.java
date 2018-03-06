package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class RobotManager {
	private RobotReceiver receiver;
	private RobotSender sender;

	public RobotManager() {
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

	public void start() {
		receiver.start();
	}

	public void sendPosition(int x, int y) {
		sender.sendPosition(x, y);
	}

	public List<Integer> getOrders() {
		return receiver.getOrders();
		
	}
	
	public boolean isCancelled() {
		return receiver.isCancelled();
	}

}
