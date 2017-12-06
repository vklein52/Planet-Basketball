package edu.illinois.finalproject.SimulationFiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 11/28/2017.
 */

public class Team {

    //Name will be some identifier of the user
    private String name;
    private List<Player> players;
    private int wins;

    public Team() {
        this(StringGenerator.genRandomString(10));
    }

    public Team(String name) {
        this.name = name;
        populatePlayers();
        wins = 0;
    }

    private void populatePlayers() {
        players = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            players.add(new Player());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
