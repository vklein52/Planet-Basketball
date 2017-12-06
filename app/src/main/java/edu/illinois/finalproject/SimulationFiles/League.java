package edu.illinois.finalproject.SimulationFiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vijay on 11/28/2017.
 */

public class League {

    public static final String FIRST_TEAM = "First Team", SECOND_TEAM = "Second Team";
    private Map<String, Team> teams;
    private List<Game> games;
    private String id;

    public League() {
        populateTeams();
        populateSampleGames();
        this.id = StringGenerator.genRandomString(15);
    }

    private void populateTeams() {
        teams = new HashMap<>();
        teams.put(FIRST_TEAM, new Team(FIRST_TEAM));
        teams.put(SECOND_TEAM, new Team(SECOND_TEAM));
    }

    private void populateSampleGames() {
        games = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            Team home, away;
            if ((i % 2) == 0) {
                home = teams.get(FIRST_TEAM);
                away = teams.get(SECOND_TEAM);
            } else {
                home = teams.get(SECOND_TEAM);
                away = teams.get(FIRST_TEAM);
            }
            simGame(home, away);
        }
    }

    public void simGame(Team home, Team away) {
        Game game = new Game(home.getName(), away.getName());
        Team winner = teams.get(game.simulate());
        games.add(game);

        winner.setWins(winner.getWins() + 1);
    }

    public Map<String, Team> getTeams() {
        return teams;
    }

    public void setTeams(Map<String, Team> teams) {
        this.teams = teams;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    //Helper functions for tests
    public Team firstTeam() {
        return teams.get(FIRST_TEAM);
    }

    public Team secondTeam() {
        return teams.get(SECOND_TEAM);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
