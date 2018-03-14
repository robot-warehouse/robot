package robot.gui;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import rp.assignments.team.warehouse.robot.communications.RobotCommunicationsManager;
import rp.assignments.team.warehouse.robot.communications.RobotManager;

public class rbtInterface {

	private RobotCommunicationsManager robotManager;
	public int amountToPickInLocation;

	public int getAmountToPickInLocation() {
		return amountToPickInLocation;
	}

	public void setAmountToPickInLocation(int amountToPickInLocation) {
		this.amountToPickInLocation = amountToPickInLocation;
	}

	public rbtInterface(RobotCommunicationsManager robotManager) {
		this.robotManager = robotManager;
	}

	public void updateLCDScreen() {
		while (true) {;
			if (robotManager.isRobotAtPickUpLocation()) {
				if (robotManager.getNumOfPicks() > 0) {
					System.out.println(
							"Robot arrived to pick up location. Please pick" + robotManager.getNumOfPicks());
					while (!robotManager.isRobotAtDropOutLocation()) {
						int i = Button.waitForAnyPress();
						if (i == Button.ID_ENTER) {
							if (amountToPickInLocation < robotManager.getNumOfPicks()) {
								LCD.clear();
								System.out.println("Incorrect amount");
								System.out.println("Picked: " + amountToPickInLocation);
								System.out.println("Please pick "
										+ (robotManager.getNumOfPicks() - amountToPickInLocation) + " more items.");
							} else if (amountToPickInLocation > robotManager.getNumOfPicks()) {
								LCD.clear();
								System.out.println("Incorrect amount");
								System.out.println("Picked: " + amountToPickInLocation);
								System.out.println("Please pick "
										+ (amountToPickInLocation - robotManager.getNumOfPicks()) + " less items.");
							} else if (amountToPickInLocation == robotManager.getNumOfPicks()) {
								LCD.clear();
								System.out.println("Right amount picked.");
								System.out.println("To the next location!");
								robotManager.setRobotAtPickUpLocation(false);
								robotManager.setRobotAtDropOutLocation(false);
								//break;
								return;
							}
						}
						if (i == Button.ID_LEFT) {
							if (amountToPickInLocation > 0) {
								amountToPickInLocation--;
							}
							LCD.clear();
							System.out.println("Picking:" + amountToPickInLocation);
						}
						if (i == Button.ID_RIGHT) {
							amountToPickInLocation++;
							LCD.clear();
							System.out.println("Picking:" + amountToPickInLocation);
						}
					}
				} 
			}
			else if (robotManager.getNumOfPicks() == amountToPickInLocation) {
				System.out.println("Robot arrived to drop off location");
				System.out.println("Please press ENTER to unload your items");
				while (robotManager.isRobotAtDropOutLocation()) {
					int i = Button.waitForAnyPress();
					if (i == Button.ID_ENTER) {
						LCD.clear();
						System.out.println("Items are unloaded.");
						System.out.println("To the next pick!");
						amountToPickInLocation = 0;
						robotManager.setRobotAtPickUpLocation(false);
						robotManager.setRobotAtDropOutLocation(false);
						break;
					}
				}
				break;
			} else if (robotManager.getNumOfPicks() == 0) {
				LCD.clear();
				System.out.println("Waiting for other robot");
				robotManager.setRobotAtPickUpLocation(false);
				robotManager.setRobotAtDropOutLocation(false);
				break;
			}
		}

	}
}