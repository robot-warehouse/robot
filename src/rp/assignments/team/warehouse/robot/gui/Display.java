package rp.assignments.team.warehouse.robot.gui;

public class Display {

	static boolean noSpaceAfter = false;

	public static void writeToScreen(String s) {
		if (s.length() >= 16) { // size of one row
			String a = "";
			int firstIndex = 0;
			int endIndex = s.indexOf(" ");

			while (endIndex != -1) { // once there is no spaces index is -1

				a += s.substring(firstIndex, endIndex);
				if (a.length() >= 10) { // in case the other word will be long
					if (noSpaceAfter) {
						System.out.println(" "+ a.substring(1)+ " ") ;
						a = "";
					} else if (!noSpaceAfter) {
						noSpaceAfter = true;
						System.out.println(" " + a + " ");
						a = "";
					}
				}
				s = s.substring(endIndex);
				endIndex = s.indexOf(" ", s.indexOf(" ") + 1);
			}
			
			String last =a +s; // case for last 2 words because after index is -1, loop ends and goes to here so otherwise two words would be printed separately
			if(last.length() <=15)
				System.out.println(last);
			else{
			if(!a.equals(""))
			System.out.println(" "+ a.substring(1) + " ");
			if(!s.equals(""))
			System.out.println(" " + s.substring(1) + " ");
			}
		} 
		else {
			System.out.println(" " + s + " ");
		}
	}

}
