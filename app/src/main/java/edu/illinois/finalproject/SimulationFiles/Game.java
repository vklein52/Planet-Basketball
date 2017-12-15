package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vijay on 11/28/2017.
 */

public class Game implements Parcelable {
    private Team homeTeam;
    private Team awayTeam;
    private String homeName;
    private String awayName;
    private int homeScore;
    private int awayScore;

    public Game() {

    }

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeName = homeTeam.getName();
        this.awayName = awayTeam.getName();
        homeScore = 0;
        awayScore = 0;
    }

    /**
     * Simulates this game object
     *
     * @return The name of the winning team
     */
    public String simulate() {
        double homeMean = homeTeam.calculateOverall();
        double homeStdDev = Math.abs(homeTeam.offensiveRating() - homeTeam.defensiveRating()) + 4;
        homeScore = (int) (RandomUtils.randGaussian(homeMean, homeStdDev) + 0.5);

        double awayMean = awayTeam.calculateOverall();
        double awayStdDev = Math.abs(awayTeam.offensiveRating() - awayTeam.defensiveRating()) + 4;
        awayScore = (int) (RandomUtils.randGaussian(awayMean, awayStdDev) + 0.5);

        if (homeScore == awayScore) {
            if (RandomUtils.randInt(0, 1) == 0) {
                homeScore += 1;
            } else {
                awayScore += 1;
            }
        }

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
