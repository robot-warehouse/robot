package rp.assignments.team.warehouse.robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;
import rp.assignments.team.warehouse.robot.gui.Display;

public class Robot {

    /**
     * Main class to run on the robot. Sets up Communications Manager and Controller and passes at instance of each into
     * each other. On the controller code ending we can assume the server disconnected so we check with the user if
     * they'd like to run the code again.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        boolean continueRunning = true;

        while (continueRunning) {
            LCD.clear();

            Display.writeToScreen("Hello, I am " + Bluetooth.getFriendlyName());

            RobotController robotController = new RobotController();

            Display.writeToScreen("Connecting to server...");
            RobotCommunicationsManager communicationsManager = new RobotCommunicationsManager(robotController);
            Display.writeToScreen("Connected to server!");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            robotController.setCommunicationsManager(communicationsManager);
            robotController.startRunningRobot();

            Display.writeToScreen("Server has been disconnect");
            Display.writeToScreen("Press ENTER button to restart, ESCAPE button to quit");

            Button.waitForAnyPress();
            if (Button.ESCAPE.isDown()) {
                continueRunning = false;
            }
        }
    }
}
