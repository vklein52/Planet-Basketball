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
    private final static int TEAM_SIZE = 13;

    private List<Player> availablePlayers;
    private List<Player> firstTeamPlayers;
    private List<Player> secondTeamPlayers;
    private String firstTeamEmail;
    private String secondTeamEmail;
    private String currTeamSelectingEmail;
    private String firstTeamId;
    private String secondTeamId;
    private String key;

    /**
     * Default constructor for Firebase purposes
     */
    public Draft() {

    }

    /**
     * @param firstTeamEmail  The email of the first Team's owner
     * @param firstTeamId     The uid of the first Team's owner
     * @param secondTeamEmail The email of the second Team's owner
     * @param secondTeamId    The uid of the second Team's owner
     * @param key             The draft key associated with this draft
     */
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

    /**
     * @return The current available players to draft
     */
    public List<Player> getAvailablePlayers() {
        return availablePlayers;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param availablePlayers The new list for availablePlayers
     */
    public void setAvailablePlayers(List<Player> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }

    /**
     * @return The list of players currently on the first team
     */
    public List<Player> getFirstTeamPlayers() {
        return firstTeamPlayers;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param firstTeamPlayers The new players to set the firstTeamPlayers to
     */
    public void setFirstTeamPlayers(List<Player> firstTeamPlayers) {
        this.firstTeamPlayers = firstTeamPlayers;
    }

    /**
     * @return The list of players currently on the second Team
     */
    public List<Player> getSecondTeamPlayers() {
        return secondTeamPlayers;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param secondTeamPlayers The new list of players
     */
    public void setSecondTeamPlayers(List<Player> secondTeamPlayers) {
        this.secondTeamPlayers = secondTeamPlayers;
    }

    /**
     * @return The first team's email
     */
    public String getFirstTeamEmail() {
        return firstTeamEmail;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param firstTeamEmail The new Email
     */
    public void setFirstTeamEmail(String firstTeamEmail) {
        this.firstTeamEmail = firstTeamEmail;
    }

    /**
     * @return The second Team's email
     */
    public String getSecondTeamEmail() {
        return secondTeamEmail;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param secondTeamEmail The new email
     */
    public void setSecondTeamEmail(String secondTeamEmail) {
        this.secondTeamEmail = secondTeamEmail;
    }

    /**
     * @return The current selecting team's email
     */
    public String getCurrTeamSelectingEmail() {
        return currTeamSelectingEmail;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param currTeamSelectingEmail The new Email
     */
    public void setCurrTeamSelectingEmail(String currTeamSelectingEmail) {
        this.currTeamSelectingEmail = currTeamSelectingEmail;
    }

    /**
     * @return The first team's id
     */
    public String getFirstTeamId() {
        return firstTeamId;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param firstTeamId The new ID
     */
    public void setFirstTeamId(String firstTeamId) {
        this.firstTeamId = firstTeamId;
    }

    /**
     * @return The second team's id
     */
    public String getSecondTeamId() {
        return secondTeamId;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param secondTeamId The new ID
     */
    public void setSecondTeamId(String secondTeamId) {
        this.secondTeamId = secondTeamId;
    }

    /**
     * @return This draft's key
     */
    public String getKey() {
        return key;
    }

    /**
     * Method required for Firebase compatibility
     *
     * @param key The new key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets availablePlayers to a new list and populates it with randomly generated players
     */
    private void genPlayers() {
        availablePlayers = new ArrayList<>();
        for (int i = 0; i < DRAFT_SIZE; i++) {
            availablePlayers.add(new Player(Position.getRandomPosition()));
        }
    }

    /**
     * Attempts to draft a player to the team corresponding to the email, succeeds if email is
     * currently selecting and tells the player it is not their turn if they are not.
     *
     * @param email  The email of the team trying to pick
     * @param player The player to be drafted
     */
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

    /**
     * Helper method for checking whose turn it is to pick
     *
     * @return true if it is the current user's turn to pick, false otherwise
     */
    public boolean usersTurnToPick() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        return currTeamSelectingEmail.equals(fbUser.getEmail());
    }

    /**
     * Helper method for checking whether the draft is over
     *
     * @return true if the draft is over, false otherwise
     */
    public boolean over() {
        return (firstTeamPlayers != null && firstTeamPlayers.size() >= TEAM_SIZE &&
                secondTeamPlayers != null && secondTeamPlayers.size() >= TEAM_SIZE);

    }
}
