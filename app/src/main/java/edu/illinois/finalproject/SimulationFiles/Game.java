package edu.illinois.finalproject.SimulationFiles;

/**
 * Created by vijay on 11/28/2017.
 */

public class Game {
    private String homeName;
    private String awayName;
    private int homeScore;
    private int awayScore;

    public Game() {

    }

    public Game(String home, String awayName) {
        this.homeName = home;
        this.awayName = awayName;
        homeScore = 0;
        awayScore = 0;
    }

    /**
     * Simulates this game object
     *
     * @return The name of the winning team
     */
    public String simulate() {
        homeScore = RandomUtils.randInt(30, 70);
        awayScore = RandomUtils.randIntExclude(40, 60, homeScore);

        if (homeScore > awayScore) {
            return homeName;
        } else {
            return awayName;
        }
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }
}
