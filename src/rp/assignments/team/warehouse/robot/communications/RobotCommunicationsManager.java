package rp.assignments.team.warehouse.robot.communications;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import rp.assignments.team.warehouse.robot.Location;
import rp.assignments.team.warehouse.robot.RobotController;
import rp.assignments.team.warehouse.shared.Facing;

public class RobotCommunicationsManager {

    private RobotReceiver receiver;
    private RobotSender sender;

    public RobotCommunicationsManager(RobotController controller) {
        try {
            NXTConnection connection = Bluetooth.waitForConnection();

            this.receiver = new RobotReceiver(connection.openDataInputStream(), controller);
            this.sender = new RobotSender(connection.openDataOutputStream());

            this.receiver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends to the server that the current job has been completed
     */
    public void sendDone() {
        this.sender.sendDone();
    }

    /**
     * Send the current position of the robot back to the server
     *
     * @param location The location of the robot
     */
    public void sendLocation(Location location) {
        this.sender.sendLocation(location);
    }

    /**
     * Send the current facing of the robot back to the server
     *
     * @param facing The direction the robot is facing
     */
    public void sendFacing(Facing facing) {
        this.sender.sendFacing(facing);
    }

    /**
     * Returns whether the server is still connected
     *
     * @return boolean
     */
    public boolean isConnected() {
        return this.receiver.isConnected();
    }
}
