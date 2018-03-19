package rp.assignments.team.warehouse.robot.gui;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class RobotInterface implements IRobotInterface {
	
	Display display = new Display();

    @Override
    public void pickUpAmountInLocation(int amount) {

        assert amount >= 0;

        int currentlyPickedUp = 0;

        while (currentlyPickedUp != amount) {
            LCD.clear();
            display.writeToScreen("Pick up items");
            display.writeToScreen("Needed: " + amount);
            display.writeToScreen("Holding: " + currentlyPickedUp);
            display.writeToScreen("< - Drop off");
            display.writeToScreen("Pickup - >");

            int buttonID = Button.waitForAnyPress();

            switch (buttonID) {
                case Button.ID_LEFT:
                    if (currentlyPickedUp != 0) {
                        currentlyPickedUp--;
                    }
                    break;
                case Button.ID_RIGHT:
                    currentlyPickedUp++;
                    break;
            }
        }
    }

    @Override
    public void dropOffAmountInLocation(int amount) {
        int buttonID = -1;

        display.writeToScreen("Press ENTER button to drop off items");
        while (buttonID != Button.ID_ENTER) {
            buttonID = Button.waitForAnyPress();
        }
    }
}
