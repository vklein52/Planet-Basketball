package edu.illinois.finalproject.MenuFiles;

import java.util.Comparator;

import edu.illinois.finalproject.SimulationFiles.Player;

/**
 * Created by vijay on 12/6/2017.
 */

public class PlayerComparator implements Comparator<Player> {

    private static SortType sortType;

    /**
     * @return Returns the SortType all StringComparators will use to sort the objects
     */
    public static SortType getSortType() {
        return sortType;
    }

    /**
     * Sets the SortType the Comparators should use in sorting the objects.
     *
     * @param sortType The new SortType to use
     */
    public static void setSortType(SortType sortType) {
        PlayerComparator.sortType = sortType;
    }

    //Todo 8: Add ability to sort on any stat they like

    @Override
    public int compare(Player p1, Player p2) {
        int out = p1.compareTo(p2);
        return sortType == SortType.DESCENDING ? -out : out;
    }
}
