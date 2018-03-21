package rp.assignments.team.warehouse.robot.motioncontrol;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;
import rp.assignments.team.warehouse.robot.RobotConfiguration;
import rp.robotics.DifferentialDriveRobot;

public class RotateCalibrate {

	private DifferentialPilot differentialPilot;
	
	public RotateCalibrate() {
		DifferentialDriveRobot robot = new DifferentialDriveRobot(RobotConfiguration.ROBOT_CONFIG);
		this.differentialPilot = robot.getDifferentialPilot();
//		checkValuesRightRotation();
		checkValuesLeftRotation();
	}

	// Testing for Right and Left Rotations
	private void checkValuesRightRotation() {

		int rotationAngle = 90;
		
		while (true) {
			
			int buttonID = Button.waitForAnyPress();
			
			switch(buttonID) {
			case Button.ID_ENTER:
				differentialPilot.rotate(rotationAngle);
				System.out.println("Rotation Angle = " + rotationAngle);
				break;
			case Button.ID_LEFT:
				rotationAngle -= 1;
				differentialPilot.rotate(rotationAngle);
				System.out.println("Rotation Angle = " + rotationAngle);
				break;
			case Button.ID_RIGHT:
				rotationAngle += 1;
				differentialPilot.rotate(rotationAngle);
				System.out.println("Rotation Angle = " + rotationAngle);
				break;
		}
	
		}		
	}
	
	// Testing for Left Rotations
		private void checkValuesLeftRotation() {

			int rotationAngle = -90;
			
			while (true) {
				
				int buttonID = Button.waitForAnyPress();
				
				switch(buttonID) {
				case Button.ID_ENTER:
					differentialPilot.rotate(rotationAngle);
					System.out.println("Rotation Angle = " + rotationAngle);
					break;
				case Button.ID_LEFT:
					rotationAngle -= 1;
					differentialPilot.rotate(rotationAngle);
					System.out.println("Rotation Angle = " + rotationAngle);
					break;
				case Button.ID_RIGHT:
					rotationAngle += 1;
					differentialPilot.rotate(rotationAngle);
					System.out.println("Rotation Angle = " + rotationAngle);
					break;
			}
		
			}		
		}
		
		public static void main(String[] args) {
			RotateCalibrate robotCalibration = new RotateCalibrate();
		}		
	
}
