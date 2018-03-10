package motioncontrol;

import java.util.List;
import java.util.ListIterator;
import rp.systems.RobotProgrammingDemo;
import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class LineFollow extends RobotProgrammingDemo implements SensorPortListener {
	
	private DifferentialPilot DP;
	private LightController light1;
	private LightController light2;
	private LightController light4;
	private Path path = new Path();
	private ListIterator<Integer> listIterate;
	private final int targetValue = 34;
	private final int junctionValue = 45;

	public LineFollow(DifferentialPilot DP, SensorPort port1, SensorPort port2, SensorPort port4) {
		this.DP = DP;
		light1 = new LightController(port1);
		light2 = new LightController(port2);
		light4 = new LightController(port4);
	}
	
	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		
	}
	
	public boolean junctionReached(int juncValue) {
		return light1.getLightValue() <= juncValue && light4.getLightValue() <= juncValue;
	}
	
	
	public void getAction(int currentAction) {
		//Turns Left
		if (currentAction == 0) {
			DP.rotate(-90);
		}
		
		//Turns Right
		else if (currentAction == 1) { 
			DP.rotate(90);
		}
		
		//Straight
		else if (currentAction == 2) {

		}
		
		//Backwards
		else if (currentAction == 3) {
			DP.rotate(180);
		}
		
		else if(currentAction == 4) {
			DP.stop();
		}
	}
	
	@Override
	public void run() {
		DP.setTravelSpeed(150);
		PIDController pid = new PIDController(targetValue, light2, (float) DP.getTravelSpeed(), DP);
		int currentAction = 0;
		List<Integer> instructionSet = path.getPathList();
		listIterate = instructionSet.listIterator();
		
		while (m_run) {
			pid.run();
			DP.forward();
			Motor.A.setSpeed(pid.rightSpeed);
			Motor.B.setSpeed(pid.leftSpeed);
			
			Boolean check = junctionReached(junctionValue);
			
			if(check) {
				DP.stop();
				DP.travel(8);
				
				if (listIterate.hasNext()) {
					currentAction = listIterate.next();
					getAction(currentAction);
				}

				else {
					getAction(-1);
				}
				
			}
			
		}
	}
}