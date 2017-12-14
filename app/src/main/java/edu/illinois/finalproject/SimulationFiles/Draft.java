package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 12/13/2017.
 */

public class Draft implements Parcelable {

    private final static int DRAFT_SIZE = 40;

    private List<Player> availablePlayers;
    private List<Player> firstTeamPlayers;
    private List<Player> secondTeamPlayers;
    private String firstTeamId;
    private String secondTeamId;
    private String currTeamSelectingId;

    public Draft() {

    }

    public Draft(String firstTeamId, String secondTeamId) {
        genPlayers();
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        firstTeamPlayers = new ArrayList<>();
        secondTeamPlayers = new ArrayList<>();
        currTeamSelectingId = firstTeamId;
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

    private void genPlayers() {
        availablePlayers = new ArrayList<>();
        for (int i = 0; i < DRAFT_SIZE; i++) {
            availablePlayers.add(new Player());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.availablePlayers);
        dest.writeTypedList(this.firstTeamPlayers);
        dest.writeTypedList(this.secondTeamPlayers);
        dest.writeString(this.firstTeamId);
        dest.writeString(this.secondTeamId);
        dest.writeString(this.currTeamSelectingId);
    }

    protected Draft(Parcel in) {
        this.availablePlayers = in.createTypedArrayList(Player.CREATOR);
        this.firstTeamPlayers = in.createTypedArrayList(Player.CREATOR);
        this.secondTeamPlayers = in.createTypedArrayList(Player.CREATOR);
        this.firstTeamId = in.readString();
        this.secondTeamId = in.readString();
        this.currTeamSelectingId = in.readString();
    }

    public static final Parcelable.Creator<Draft> CREATOR = new Parcelable.Creator<Draft>() {
        @Override
        public Draft createFromParcel(Parcel source) {
            return new Draft(source);
        }

        @Override
        public Draft[] newArray(int size) {
            return new Draft[size];
        }
    };
}
