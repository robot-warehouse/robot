package rp.assignments.team.warehouse.robot.communications;

public class RobotDisconnect {

    public static void main(String[] args) {
        RobotCommunicationsManager manager = new RobotCommunicationsManager();
        while (true) {
            try {
                while (manager.isConnected()) {

                }
                System.out.println("Disconnected from robot");
                manager.attemptReconnect();
                if (manager.isConnected()) {
                    System.out.println("Reconnected");
                } else {
                    System.out.println("Could not reconnect");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
