package rp.assignments.team.warehouse.robot.gui;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.util.Timer;
import lejos.util.TimerListener;

public class RobotInterface implements IRobotInterface {

	@Override
	public void pickUpAmountInLocation(int amount) {

		assert amount >= 0;

		int currentlyPickedUp = 0;

		while (currentlyPickedUp != amount) {

			LCD.clear();
			Display.writeToScreen("Pick up items");
			Display.writeToScreen("Needed: " + amount);
			Display.writeToScreen("Holding: " + currentlyPickedUp);
			Display.writeToScreen("< - Drop off");
			Display.writeToScreen("Pickup - >");

			int theDelay = 10000; // Waits 10 seconds for user
			TimerListener timerListener = new TimerListener() { // Plays 2 beeps

				@Override
				public void timedOut() {
					Sound.twoBeeps();
				}

			};

			Timer timer = new Timer(theDelay, timerListener);

			timer.start(); // Start timer

			int buttonID = Button.waitForAnyPress();

			switch (buttonID) {
			case Button.ID_LEFT:
				timer.stop(); // Stops timer
				if (currentlyPickedUp != 0) {
					currentlyPickedUp--;
				}
				break;
			case Button.ID_RIGHT:
				timer.stop(); // Stops timer
				currentlyPickedUp++;
				break;
			}
		}
	}

	@Override
	public void dropOffAmountInLocation(int amount) {
		int buttonID = -1;

		Display.writeToScreen("Press ENTER button to drop off items");
		while (buttonID != Button.ID_ENTER) {
			buttonID = Button.waitForAnyPress();
		}
	}
}
