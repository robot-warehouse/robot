package rp.assignments.team.warehouse.robot.gui;

public class Display {

    private static boolean noSpaceAfter = false;

    public static void writeToScreen(String message) {
        if (message.length() >= 16) { // size of one row
            String formattedMessage = "";
            int firstIndex = 0;
            int endIndex = message.indexOf(" ");

            while (endIndex != -1) { // once there is no spaces index is -1

                formattedMessage += message.substring(firstIndex, endIndex);
                if (formattedMessage.length() >= 10) { // in case the other word will be long
                    if (noSpaceAfter) {
                        System.out.println(" " + formattedMessage.substring(1));
                        formattedMessage = "";
                    } else if (!noSpaceAfter) {
                        noSpaceAfter = true;
                        System.out.println(" " + formattedMessage);
                        formattedMessage = "";
                    }
                }
                message = message.substring(endIndex);
                endIndex = message.indexOf(" ", message.indexOf(" ") + 1);
            }

            // case for last 2 words because after index is -1, loop ends and goes to here so otherwise two words
            // would be printed separately
            noSpaceAfter = false;
            String last = formattedMessage + message;
            if (last.length() <= 15) {
                System.out.println(last);
            } else {
                if (!formattedMessage.equals("")) {
                    System.out.println(" " + formattedMessage.substring(1));
                }

                if (!message.equals("")) {
                    System.out.println(" " + message.substring(1));
                }
            }
        } else {
            System.out.println(" " + message);
        }
    }

}
