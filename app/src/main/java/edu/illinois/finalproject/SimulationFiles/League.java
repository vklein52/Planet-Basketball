package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vijay on 11/28/2017.
 */

public class League implements Parcelable {

    public static final String FIRST_TEAM = "First Team", SECOND_TEAM = "Second Team";
    private Map<String, Team> teams;
    private List<Game> games;
    private String id;

    public League() {

    }

    public League(Draft draft) {
        this(draft.getFirstTeamEmail(), draft.getFirstTeamPlayers(), draft.getSecondTeamEmail(), draft.getSecondTeamPlayers());
    }

    public League(String firstEmail, List<Player> firstPlayers, String secondEmail, List<Player> secondPlayers) {
        Team first = new Team(firstEmail, firstPlayers);
        Team second = new Team(secondEmail, secondPlayers);
        populateTeams(first, second);
        populateGames();

        this.id = StringGenerator.genRandomString(15);
    }

    private void populateTeams(Team first, Team second) {
        teams = new HashMap<>();
        teams.put(FIRST_TEAM, first);
        teams.put(SECOND_TEAM, second);
    }

    private void populateGames() {
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
        Game game = new Game(home, away);
        String winTeamName = game.simulate();
        if (teams.get(FIRST_TEAM).getName().equals(winTeamName)) {
            winTeamName = FIRST_TEAM;
        } else {
            winTeamName = SECOND_TEAM;
        }
        Team winner = teams.get(winTeamName);
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

    public String firstTeamRecord() {
        return "(" + firstTeam().getWins() + ", " + secondTeam().getWins() + ")";
    }

    public String secondTeamRecord() {
        return "(" + secondTeam().getWins() + ", " + firstTeam().getWins() + ")";
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.teams.size());
        for (Map.Entry<String, Team> entry : this.teams.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
        dest.writeTypedList(this.games);
        dest.writeString(this.id);
    }

    protected League(Parcel in) {
        int teamsSize = in.readInt();
        this.teams = new HashMap<String, Team>(teamsSize);
        for (int i = 0; i < teamsSize; i++) {
            String key = in.readString();
            Team value = in.readParcelable(Team.class.getClassLoader());
            this.teams.put(key, value);
        }
        this.games = in.createTypedArrayList(Game.CREATOR);
        this.id = in.readString();
    }

    public static final Parcelable.Creator<League> CREATOR = new Parcelable.Creator<League>() {
        @Override
        public League createFromParcel(Parcel source) {
            return new League(source);
        }

        @Override
        public League[] newArray(int size) {
            return new League[size];
        }
    };
}
