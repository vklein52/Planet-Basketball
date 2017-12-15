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
    private static final int NUM_GAMES = 11;

    private Map<String, Team> teams;
    private List<Game> games;
    private String id;

    /**
     * Default constructor required for Firebase compatibility
     */
    public League() {

    }

    /**
     * @param draft The draft from which to create this League
     */
    public League(Draft draft) {
        this(draft.getFirstTeamEmail(), draft.getFirstTeamPlayers(), draft.getSecondTeamEmail(), draft.getSecondTeamPlayers());
    }

    /**
     * @param firstEmail    The first team's owner's email
     * @param firstPlayers  The first team's players
     * @param secondEmail   The second team's owner's email
     * @param secondPlayers The second team's players
     */
    public League(String firstEmail, List<Player> firstPlayers, String secondEmail, List<Player> secondPlayers) {
        Team first = new Team(firstEmail, firstPlayers);
        Team second = new Team(secondEmail, secondPlayers);
        populateTeams(first, second);
        populateGames();

        this.id = StringGenerator.genRandomString(15);
    }

    /**
     * Populates the team map with the two passed teams
     *
     * @param first  The first team
     * @param second The second team
     */
    private void populateTeams(Team first, Team second) {
        teams = new HashMap<>();
        teams.put(FIRST_TEAM, first);
        teams.put(SECOND_TEAM, second);
    }

    /**
     * Sims NUM_GAMES number of games and populates the list of games accordingly
     */
    private void populateGames() {
        games = new ArrayList<>();
        for (int i = 0; i < NUM_GAMES; i++) {
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

    /**
     * Simulates a game given the home and away team
     *
     * @param home The home team
     * @param away The away team
     */
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

    /**
     * Required for Firebase compatibility
     *
     * @param teams The new teams
     */
    public void setTeams(Map<String, Team> teams) {
        this.teams = teams;
    }

    /**
     * @return The list of games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param games The new games
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    /**
     * Odd name to avoid Firebase issues with methods that start with get
     *
     * @return The first team
     */
    public Team firstTeam() {
        return teams.get(FIRST_TEAM);
    }

    /**
     * Odd name to avoid Firebase issues with methods that start with get
     *
     * @return The first team's record
     */
    public String firstTeamRecord() {
        return "(" + firstTeam().getWins() + ", " + secondTeam().getWins() + ")";
    }

    /**
     * Odd name to avoid Firebase issues with methods that start with get
     *
     * @return The second team's record
     */
    public String secondTeamRecord() {
        return "(" + secondTeam().getWins() + ", " + firstTeam().getWins() + ")";
    }

    /**
     * Odd name to avoid Firebase issues with methods that start with get
     *
     * @return The second team
     */
    public Team secondTeam() {
        return teams.get(SECOND_TEAM);
    }

    /**
     * @return The id of the league
     */
    public String getId() {
        return id;
    }

    /**
     * Required for Firebase compatibility
     *
     * @param id The new id
     */
    public void setId(String id) {
        this.id = id;
    }

    //Below is generic Parcelable implementation
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
