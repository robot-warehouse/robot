package rp.assignments.team.warehouse.robot.communications;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

import rp.assignments.team.warehouse.shared.communications.Command;


public class RobotSender {
	private DataOutputStream toServer;
	public RobotSender(DataOutputStream toServer) {
		this.toServer = toServer;
	}
	/**
	 * Send position of robot to the server
	 */
	public void sendPosition(int x, int y) {
		try {
			toServer.writeUTF(Command.SEND_POSITION.toString());	
			toServer.writeUTF(Integer.toString(x));
			toServer.writeUTF(Integer.toString(y));
			toServer.flush();
			System.out.println("Sent (" + x + "," + y);
		}
		catch(IOException e) {
			System.err.println("Something went wrong with the server");
			e.printStackTrace();
		}
		catch(Exception e) {
			System.err.println("Exception");
		}
		
	}
	
}
