package rp.assignments.team.warehouse.robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;

import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;

public class Robot {

    public static void main(String[] args) throws InterruptedException {
        boolean continueRunning = true;

        while (continueRunning) {
            LCD.clear();

            System.out.println("Hello, I am " + Bluetooth.getFriendlyName());
            System.out.println("Connecting to server...");

            RobotCommunicationsManager communicationsManager = new RobotCommunicationsManager();

            System.out.println("Connected to server!");
            Thread.sleep(1000);

            RobotController robotController = new RobotController(communicationsManager);

            communicationsManager.setController(robotController);
            robotController.run();

            System.out.println("Server has been disconnect");
            System.out.println("Press ENTER button to restart, ESCAPE button to quit");

            Button.waitForAnyPress();
            if (Button.ESCAPE.isDown()) {
                continueRunning = false;
            }
        }
    }
}
