package edu.illinois.finalproject.SimulationFiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 12/5/2017.
 */

public class User {
    private String uid;
    private List<String> leagueIds;

    public User() {
    }

    public User(String uid) {
        this.uid = uid;
        leagueIds = new ArrayList<>();
    }

    public void appendLeagueIds(String id) {
        leagueIds.add(id);
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
}
