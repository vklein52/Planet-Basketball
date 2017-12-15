package edu.illinois.finalproject.SimulationFiles;

import java.util.Random;

/**
 * Created by vijay on 11/28/2017.
 */

public enum Position {
    POINT_GUARD, SHOOTING_GUARD, SMALL_FORWARD, POWER_FORWARD, CENTER;

    /**
     * @param position The position to represent as a String
     * @return A String representation of the position
     */
    public static String asString(Position position) {
        switch (position) {
            case POINT_GUARD:
                return "Point Guard";
            case SHOOTING_GUARD:
                return "Shooting Guard";
            case SMALL_FORWARD:
                return "Small Forward";
            case POWER_FORWARD:
                return "Power Forward";
            case CENTER:
                return "Center";
            default:
                return "";
        }
    }

    /**
     * @param position The position to represent as an abbreviated String
     * @return An abbreviated String representation of the position
     */
    public static String asAbbreviatedString(Position position) {
        switch (position) {
            case POINT_GUARD:
                return "PG";
            case SHOOTING_GUARD:
                return "SG";
            case SMALL_FORWARD:
                return "SF";
            case POWER_FORWARD:
                return "PF";
            case CENTER:
                return "C";
            default:
                return "";
        }
    }

    /**
     * @return A random position
     */
    public static Position getRandomPosition() {
        Random random = new Random();
        int pos = random.nextInt(5);

        switch (pos) {
            case 0:
                return POINT_GUARD;
            case 1:
                return SHOOTING_GUARD;
            case 2:
                return SMALL_FORWARD;
            case 3:
                return POWER_FORWARD;
            case 4:
                return CENTER;
            default:
                return POINT_GUARD;
        }
    }

}
