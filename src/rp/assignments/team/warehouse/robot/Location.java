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

    /**
     * Increases the X value by 1
     *
     * @return New location object with the new values
     */
    public Location incrementX() {
        return new Location(getX() + 1, getY());
    }

    /**
     * Decrease the X value by 1
     *
     * @return New location object with the new values
     */
    public Location decrementX() {
        return new Location(getX() - 1, getY());
    }

    /**
     * Increases the Y value by 1
     *
     * @return New location object with the new values
     */
    public Location incrementY() {
        return new Location(getX(), getY() + 1);
    }

    /**
     * Decreases the Y value by 1
     *
     * @return New location object with the new values
     */
    public Location decrementY() {
        return new Location(getX(), getY() - 1);
    }
}
