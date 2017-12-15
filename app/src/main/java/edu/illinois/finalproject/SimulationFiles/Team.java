package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by vijay on 11/28/2017.
 */

public class Team implements Parcelable {

    private String name;
    private List<Player> players;
    private int wins;

    /**
     * Default constructor required for Firebase compatibility
     */
    public Team() {

    }

    /**
     * @param name    The name of this team
     * @param players The players of this team
     */
    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
        wins = 0;
    }

    /**
     * @return The name of this team
     */
    public String getName() {
        return name;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param name This team's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The team's players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param players This team's new players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * @return The team's win count
     */
    public int getWins() {
        return wins;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param wins This team's new win count
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * @return The overall rating of this team, calculated as the average of its offensive and defensive rating
     */
    public int calculateOverall() {
        return (int) (offensiveRating() + defensiveRating()) / 2;
    }

    /**
     * @return The offensive rating of this team, calculated as the average of its players' offensive ratings
     */
    public double offensiveRating() {
        double sum = 0.0;
        for (Player player : players) {
            sum += player.offensiveRating();
        }
        return sum / players.size();
    }

    /**
     * @return The defensive rating of this team, calculated as the average of its players' defensive ratings
     */
    public double defensiveRating() {
        double sum = 0.0;
        for (Player player : players) {
            sum += player.defensiveRating();
        }
        return sum / players.size();
    }

    //Below is generic implementation of Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.players);
        dest.writeInt(this.wins);
    }

    protected Team(Parcel in) {
        this.name = in.readString();
        this.players = in.createTypedArrayList(Player.CREATOR);
        this.wins = in.readInt();
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
