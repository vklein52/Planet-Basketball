package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 11/28/2017.
 */

public class Team implements Parcelable {

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

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
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

    public int calculateOverall() {
        return RandomUtils.randInt(30,80);
    }

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
