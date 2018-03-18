package rp.assignments.team.warehouse.robot.communications;

import java.io.DataOutputStream;
import java.io.IOException;

import rp.assignments.team.warehouse.robot.Location;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.communications.Command;


public class RobotSender {

    private DataOutputStream toServer;

    public RobotSender(DataOutputStream toServer) {
        this.toServer = toServer;
    }

    public void sendLocation(Location location) {
        try {
            this.toServer.writeUTF(Command.SEND_POSITION.toString());
            this.toServer.writeUTF(Integer.toString(location.getX()));
            this.toServer.writeUTF(Integer.toString(location.getY()));
            this.toServer.flush();

            System.out.println("Sent (" + location.getX() + "," + location.getY() + ")");
        } catch (IOException e) {
            System.out.println("Something went wrong with the server");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General exception");
        }
    }

    public void sendDone() {
        try {
            this.toServer.writeUTF(Command.FINISHED_JOB.toString());
            this.toServer.flush();
        } catch (IOException e) {
            System.out.println("Something went wrong with the server");
        }
    }

    public void sendFacing(Facing facing) {
        try {
            this.toServer.writeUTF(Command.SEND_FACING.toString());
            this.toServer.writeUTF(facing.toString());
            this.toServer.flush();
        } catch (IOException e) {
            System.out.println("Something went wrong with the server");
        }
    }

}
