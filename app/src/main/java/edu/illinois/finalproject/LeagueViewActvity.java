package edu.illinois.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.League;
import edu.illinois.finalproject.SimulationFiles.Team;

public class LeagueViewActvity extends AppCompatActivity {

    public static final String LEAGUE = "LEAGUE";

    private League league;

    private TextView firstTeamName;
    private TextView firstTeamRecord;
    private TextView firstTeamOverall;

    private TextView secondTeamName;
    private TextView secondTeamRecord;
    private TextView secondTeamOverall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_view_actvity);

        league = getIntent().getParcelableExtra(LEAGUE);

        initializeViews();
        populateViews();
    }

    private void initializeViews() {
        firstTeamName = (TextView) findViewById(R.id.first_team_list_name);
        firstTeamRecord = (TextView) findViewById(R.id.first_team_list_record);
        firstTeamOverall = (TextView) findViewById(R.id.first_team_list_overall);

        secondTeamName = (TextView) findViewById(R.id.second_team_list_name);
        secondTeamRecord = (TextView) findViewById(R.id.second_team_list_record);
        secondTeamOverall = (TextView) findViewById(R.id.second_team_list_overall);
    }

    private void populateViews() {
        Team first = league.firstTeam();
        Team second = league.secondTeam();

        firstTeamName.setText(first.getName());
        firstTeamRecord.setText(league.firstTeamRecord());
        String firstOvrText = "Overall: " + first.calculateOverall();
        firstTeamOverall.setText(firstOvrText);

        secondTeamName.setText(second.getName());
        secondTeamRecord.setText(league.secondTeamRecord());
        String secondOvrText = "Overall: " + second.calculateOverall();
        secondTeamOverall.setText(secondOvrText);
    }
}
