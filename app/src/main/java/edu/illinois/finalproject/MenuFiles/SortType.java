package edu.illinois.finalproject.MenuFiles;

/**
 * Created by vijay on 11/13/2017.
 */

public enum SortType {
    ASCENDING, DESCENDING;

    /**
     * Helper function to translate a String representation of a SortType to a SortType
     *
     * @param key The string representation of the enum value
     * @return The SortType associated with key, null if none exists
     */
    public static SortType fromString(String key) {
        switch (key) {
            case "Ascending":
                return ASCENDING;
            case "Descending":
                return DESCENDING;
        }
        return null;
    }
}
