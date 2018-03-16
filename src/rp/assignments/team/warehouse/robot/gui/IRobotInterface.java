package rp.assignments.team.warehouse.robot.gui;

public interface IRobotInterface {

    /**
     * Asks the user to press the ENTER button to confirm picking up the items.
     * Waits till the user has picked up all the items before returning
     *
     * @param amount The amount of items to pick up
     */
    public void pickUpAmountInLocation(int amount);

    /**
     * Asks the user to press the ENTER button to confirm dropping off the items.
     * Waits till the user has dropped off all the items before returning
     *
     * @param amount The amount of items to drop off
     */
    public void dropOffAmountInLocation(int amount);
}
