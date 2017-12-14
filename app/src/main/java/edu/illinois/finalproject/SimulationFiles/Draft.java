package edu.illinois.finalproject.SimulationFiles;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 12/13/2017.
 */

public class Draft {

    private final static int DRAFT_SIZE = 40;
    private final static int TEAM_SIZE = 5;

    private List<Player> availablePlayers;
    private List<Player> firstTeamPlayers;
    private List<Player> secondTeamPlayers;
    private String firstTeamEmail;
    private String secondTeamEmail;
    private String currTeamSelectingEmail;
    private String firstTeamId;
    private String secondTeamId;
    private String key;

    public Draft() {

    }

    public Draft(String firstTeamEmail, String firstTeamId, String secondTeamEmail, String secondTeamId, String key) {
        genPlayers();
        this.firstTeamEmail = firstTeamEmail;
        this.firstTeamId = firstTeamId;
        this.secondTeamEmail = secondTeamEmail;
        this.secondTeamId = secondTeamId;
        firstTeamPlayers = new ArrayList<>();
        secondTeamPlayers = new ArrayList<>();
        currTeamSelectingEmail = firstTeamEmail;
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

    public String getFirstTeamEmail() {
        return firstTeamEmail;
    }

    public void setFirstTeamEmail(String firstTeamEmail) {
        this.firstTeamEmail = firstTeamEmail;
    }

    public String getSecondTeamEmail() {
        return secondTeamEmail;
    }

    public void setSecondTeamEmail(String secondTeamEmail) {
        this.secondTeamEmail = secondTeamEmail;
    }

    public String getCurrTeamSelectingEmail() {
        return currTeamSelectingEmail;
    }

    public void setCurrTeamSelectingEmail(String currTeamSelectingEmail) {
        this.currTeamSelectingEmail = currTeamSelectingEmail;
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

        if (email.equals(firstTeamEmail)) {
            if (firstTeamPlayers == null) {
                firstTeamPlayers = new ArrayList<>();
            }
            firstTeamPlayers.add(player);
            currTeamSelectingEmail = secondTeamEmail;
        } else {
            if (secondTeamPlayers == null) {
                secondTeamPlayers = new ArrayList<>();
            }
            secondTeamPlayers.add(player);
            currTeamSelectingEmail = firstTeamEmail;
        }
    }

    public boolean usersTurnToPick() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        return currTeamSelectingEmail.equals(fbUser.getEmail());
    }

    public boolean over() {
        return (firstTeamPlayers != null && firstTeamPlayers.size() >= TEAM_SIZE &&
                secondTeamPlayers != null && secondTeamPlayers.size() >= TEAM_SIZE);

    }
}
