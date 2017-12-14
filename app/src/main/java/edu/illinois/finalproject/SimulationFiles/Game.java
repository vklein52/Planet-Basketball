package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vijay on 11/28/2017.
 */

public class Game implements Parcelable {
    private String homeName;
    private String awayName;
    private int homeScore;
    private int awayScore;

    public Game() {

    }

    public Game(String homeName, String awayName) {
        this.homeName = homeName;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.homeName);
        dest.writeString(this.awayName);
        dest.writeInt(this.homeScore);
        dest.writeInt(this.awayScore);
    }

    protected Game(Parcel in) {
        this.homeName = in.readString();
        this.awayName = in.readString();
        this.homeScore = in.readInt();
        this.awayScore = in.readInt();
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
