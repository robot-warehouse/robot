package rp.assignments.team.warehouse.robot;

public class Location {

    /** The x coordinate. */
    private final int x;
    
    /** The y coordinate. */
    private final int y;

    /**
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the x coordinate.
     *
     * @return The x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the y coordinate.
     *
     * @return The y coordinate.
     */
    public int getY() {
        return this.y;
    }
}
