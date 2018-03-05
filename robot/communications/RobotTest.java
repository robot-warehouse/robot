package rp.assignments.team.warehouse.robot.communications;

import java.util.List;

public class RobotTest {
	public static void main(String[] args) throws InterruptedException {
		RobotManager manager = new RobotManager();
		manager.start();
		List<Integer> orders = manager.getOrders();
		manager.sendPosition(2, 3);
		while(true) {
			if(orders != null && !orders.isEmpty()) {
				for(int i = 0; i < orders.size() ;i++)
				{
					System.out.println("Order " + i + ": " + orders.get(i));
					Thread.sleep(500);
					manager.sendPosition(i, i + 1);
				}
			}
		}
		
	
		
		
	}
}
