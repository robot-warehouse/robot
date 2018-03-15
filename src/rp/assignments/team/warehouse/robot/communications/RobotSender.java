package rp.assignments.team.warehouse.robot.communications;

import java.io.DataOutputStream;
import java.io.IOException;


public class RobotSender extends Thread {

    private DataOutputStream toServer;

    public RobotSender(DataOutputStream toServer) {
        this.toServer = toServer;
    }

    public void sendPosition(int x, int y) {
        try {
            toServer.writeUTF(Command.SEND_POSITION.toString());
            toServer.writeUTF(Integer.toString(x));
            toServer.writeUTF(Integer.toString(y));
            toServer.flush();
            System.out.println("Sent (" + x + "," + y + ")");
        } catch (IOException e) {
            System.err.println("Something went wrong with the server");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("General exception");
        }
    }

    public void sendDone() {
        try {
            toServer.writeUTF(Command.FINISHED_JOB.toString());
            toServer.flush();
        } catch (IOException e) {
            System.out.println("io");
        }
    }
}
