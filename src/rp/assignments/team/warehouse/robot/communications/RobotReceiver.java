package rp.assignments.team.warehouse.robot.communications;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rp.assignments.team.warehouse.robot.RobotController;
import rp.assignments.team.warehouse.shared.Facing;
import rp.assignments.team.warehouse.shared.Instruction;
import rp.assignments.team.warehouse.shared.communications.Command;

/*
 * Receives messages from the server
 */
public class RobotReceiver extends Thread {

    private DataInputStream fromServer;
    private RobotController controller;

    private boolean connected;

    public RobotReceiver(DataInputStream fromServer, RobotController controller) {
        this.fromServer = fromServer;
        this.controller = controller;
        this.connected = true;
    }

    @Override
    public void run() {
        while (this.connected) {
            try {
                String s = this.fromServer.readUTF();
                Command command = Command.strToCommand(s);
                switch (command) {
                    case CANCEL:
                        this.controller.cancelJob();
                        break;
                    case SEND_ORDERS:
                        List<Instruction> instructions = new ArrayList<>();

                        String val = this.fromServer.readUTF();
                        while (!val.equals("-1")) {
                            instructions.add(Instruction.values()[Integer.parseInt(val)]);
                            val = this.fromServer.readUTF();
                        }

                        System.out.println("New orders sent");
                        System.out.println(instructions.toString());
                        this.controller.addInstructionToQueue(instructions);
                        break;
                    case SEND_PICKS:
                        this.controller.setCurrentPickCount(Integer.parseInt(this.fromServer.readUTF()));
                        break;
                    case DISCONNECT:
                        System.out.println("Disconnected");
                        this.connected = false;
                        break;
                    case SEND_POSITION:
                    	int x = Integer.parseInt(fromServer.readUTF());
                    	int y = Integer.parseInt(fromServer.readUTF());
                    	System.out.println("Received " + x + ", " + y);
                    	controller.setPosition(x, y);
                    	break;
                    case SEND_FACING:
                    	
                    	Facing facing = Facing.strToFacing(fromServer.readUTF());
                    	System.out.println("Recevied " + facing);
                    	controller.setFacing(facing);
                    	break;
                    default:
                        System.out.println("Unrecognised command");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Something went wrong with the server");
                this.connected = false;
                break;
            }
        }
    }

    /**
     * Returns whether the server is still connected
     *
     * @return true/false
     */
    public boolean isConnected() {
        return this.connected;
    }
}
