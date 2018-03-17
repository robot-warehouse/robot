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
        this.connected = true;
        this.orderQueue = new ArrayList<>();
        this.numOfPicks = 0;
    }

    @Override
    public void run() {
        while (true) {
            if (this.connected) {
                try {
                    String s = this.fromServer.readUTF();
                    Command command = Command.strToCommand(s);
                    switch (command) {
                        case CANCEL:
                            this.jobCancelled = true;
                            break;
                        case SEND_ORDERS:
                            this.orderQueue.clear();
                            List<Integer> tempCommands = new ArrayList<Integer>();
                            String val = this.fromServer.readUTF();
                            while (!val.equals("-1")) {
                                tempCommands.add((Integer.parseInt(val)));
                                val = this.fromServer.readUTF();
                            }
                            System.out.println("New orders sent");
                            System.out.println(tempCommands.toString());
                            this.orderQueue.addAll(tempCommands);
                            this.jobCancelled = false;
                            break;
                        case SEND_PICKS:
                            this.numOfPicks = Integer.valueOf(this.fromServer.readUTF());
                            break;
                        case DISCONNECT:
                            System.out.println("Disconnected");
                            this.communicationsManager.attemptReconnect();
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
        return this.orderQueue;
    }

    public void setDataInputStream(DataInputStream fromServer) {
        this.fromServer = fromServer;
    }

    public void resetOrders() {
        this.orderQueue = new ArrayList<>();
    }

    public int getNumOfPicks() {
        return this.numOfPicks;
    }

    public boolean isCancelled() {
        return this.jobCancelled;
    }

    public boolean getConnected() {
        return this.connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
