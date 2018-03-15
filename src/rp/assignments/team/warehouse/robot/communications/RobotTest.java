package rp.assignments.team.warehouse.robot.communications;

import java.util.EmptyQueueException;
import java.util.List;

public class RobotTest {

    public static void main(String[] args) throws InterruptedException {
        RobotCommunicationsManager manager = new RobotCommunicationsManager();
        manager.start();
        List<Integer> orders = manager.getOrders();
        while (true) {
            if (orders != null && !orders.isEmpty()) {
                try {
                    Integer order = orders.get(orders.size() - 1);
                    System.out.println("Received order :" + order);
                } catch (EmptyQueueException e) {}
            }
        }
    }
}
