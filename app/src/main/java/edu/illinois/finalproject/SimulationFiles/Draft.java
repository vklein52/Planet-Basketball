package edu.illinois.finalproject.SimulationFiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 12/13/2017.
 */

public class Draft {

    private final static int DRAFT_SIZE = 40;

    //Todo: Rename the ids to emails
    private List<Player> availablePlayers;
    private List<Player> firstTeamPlayers;
    private List<Player> secondTeamPlayers;
    private String firstTeamId;
    private String secondTeamId;
    private String currTeamSelectingId;
    private String key;

    public Draft() {

    }

    public Draft(String firstTeamId, String secondTeamId, String key) {
        genPlayers();
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        firstTeamPlayers = new ArrayList<>();
        secondTeamPlayers = new ArrayList<>();
        currTeamSelectingId = firstTeamId;
        this.key = key;
    }

    public List<Player> getAvailablePlayers() {
        return availablePlayers;
    }

    public void setAvailablePlayers(List<Player> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }

    public List<Player> getFirstTeamPlayers() {
        return firstTeamPlayers;
    }

    public void setFirstTeamPlayers(List<Player> firstTeamPlayers) {
        this.firstTeamPlayers = firstTeamPlayers;
    }

    public List<Player> getSecondTeamPlayers() {
        return secondTeamPlayers;
    }

    public void setSecondTeamPlayers(List<Player> secondTeamPlayers) {
        this.secondTeamPlayers = secondTeamPlayers;
    }

    public String getFirstTeamId() {
        return firstTeamId;
    }

    public void setFirstTeamId(String firstTeamId) {
        this.firstTeamId = firstTeamId;
    }

    public String getSecondTeamId() {
        return secondTeamId;
    }

    public void setSecondTeamId(String secondTeamId) {
        this.secondTeamId = secondTeamId;
    }

    public String getCurrTeamSelectingId() {
        return currTeamSelectingId;
    }

    public void setCurrTeamSelectingId(String currTeamSelectingId) {
        this.currTeamSelectingId = currTeamSelectingId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private void genPlayers() {
        availablePlayers = new ArrayList<>();
        for (int i = 0; i < DRAFT_SIZE; i++) {
            availablePlayers.add(new Player());
        }
    }

    public void draftPlayer(String email, Player player) {
        availablePlayers.remove(player);

        if (email.equals(firstTeamId)) {
            if (firstTeamPlayers == null) {
                firstTeamPlayers = new ArrayList<>();
            }
            firstTeamPlayers.add(player);
            currTeamSelectingId = secondTeamId;
        } else {
            if (secondTeamPlayers == null) {
                secondTeamPlayers = new ArrayList<>();
            }
            secondTeamPlayers.add(player);
            currTeamSelectingId = firstTeamId;
        }
    }

}
