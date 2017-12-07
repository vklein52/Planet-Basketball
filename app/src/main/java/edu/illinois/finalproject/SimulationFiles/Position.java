package edu.illinois.finalproject.SimulationFiles;

import java.util.Random;

/**
 * Created by vijay on 11/28/2017.
 */

public enum Position {
    POINT_GUARD, SHOOTING_GUARD, SMALL_FORWARD, POWER_FORWARD, CENTER;

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

    public static int asInt(Position position) {
        switch (position) {
            case POINT_GUARD:
                return 1;
            case SHOOTING_GUARD:
                return 2;
            case SMALL_FORWARD:
                return 3;
            case POWER_FORWARD:
                return 4;
            case CENTER:
                return 5;
            default:
                return 0;
        }
    }

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
