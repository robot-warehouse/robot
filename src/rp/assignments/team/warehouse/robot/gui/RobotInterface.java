package rp.assignments.team.warehouse.robot.gui;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class RobotInterface implements IRobotInterface{

    @Override
    public void pickUpAmountInLocation(int amount) {

        assert amount >= 0;

        int currentlyPickedUp = 0;

        while (currentlyPickedUp != amount) {
            LCD.clear();
            System.out.println("Pick up items");
            System.out.println("Needed: " + amount);
            System.out.println("Holding: " + currentlyPickedUp);
            System.out.println("< - Drop off");
            System.out.println("Pickup - >");

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

        System.out.println("Press ENTER button to drop off items");
        while (buttonID != Button.ID_ENTER) {
            buttonID = Button.waitForAnyPress();
        }
    }
}
