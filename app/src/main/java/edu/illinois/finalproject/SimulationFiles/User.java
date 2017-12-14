package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 12/5/2017.
 */

public class User implements Parcelable {
    private String uid;
    private List<String> leagueIds;

    public User() {
    }

    public User(String uid) {
        this.uid = uid;
        leagueIds = null;
    }

    public void appendLeagueIds(String id) {
        if (leagueIds == null) {
            leagueIds = new ArrayList<>();
        }
        if (!leagueIds.contains(id)) {
            leagueIds.add(id);
        }
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getLeagueIds() {
        return leagueIds;
    }

    public void setLeagueIds(List<String> leagueIds) {
        this.leagueIds = leagueIds;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeStringList(this.leagueIds);
    }

    protected User(Parcel in) {
        this.uid = in.readString();
        this.leagueIds = in.createStringArrayList();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uid != null ? !uid.equals(user.uid) : user.uid != null) return false;
        return leagueIds != null ? leagueIds.equals(user.leagueIds) : user.leagueIds == null;

    }

    @Override
    public int hashCode() {
        int result = uid != null ? uid.hashCode() : 0;
        result = 31 * result + (leagueIds != null ? leagueIds.hashCode() : 0);
        return result;
    }
}
