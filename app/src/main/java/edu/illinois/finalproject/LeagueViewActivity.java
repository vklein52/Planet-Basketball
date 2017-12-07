package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.illinois.finalproject.RecyclerViewFiles.GameListActivity;
import edu.illinois.finalproject.RecyclerViewFiles.PlayerListViewActivity;
import edu.illinois.finalproject.SimulationFiles.League;
import edu.illinois.finalproject.SimulationFiles.Team;

public class LeagueViewActivity extends AppCompatActivity {

    public static final String LEAGUE = "LEAGUE";
    private static final String TAG = LeagueViewActivity.class.getSimpleName();

    private League league;
    private Context context;

    private View firstTeamItem;
    private TextView firstTeamName;
    private TextView firstTeamRecord;
    private TextView firstTeamOverall;

    private View secondTeamItem;
    private TextView secondTeamName;
    private TextView secondTeamRecord;
    private TextView secondTeamOverall;

    private Button gamesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_view_activity);

        league = getIntent().getParcelableExtra(LEAGUE);
        context = this;

        initializeViews();
        populateViews();
        addOnClickListeners();
    }

    private void initializeViews() {
        firstTeamItem = findViewById(R.id.first_team_item);
        firstTeamName = (TextView) findViewById(R.id.first_team_list_name);
        firstTeamRecord = (TextView) findViewById(R.id.first_team_list_record);
        firstTeamOverall = (TextView) findViewById(R.id.first_team_list_overall);

        secondTeamItem = findViewById(R.id.second_team_item);
        secondTeamName = (TextView) findViewById(R.id.second_team_list_name);
        secondTeamRecord = (TextView) findViewById(R.id.second_team_list_record);
        secondTeamOverall = (TextView) findViewById(R.id.second_team_list_overall);

        gamesButton = (Button) findViewById(R.id.league_view_games_button);
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

    private void addOnClickListeners() {
        firstTeamItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerListViewActivity.class);
                intent.putExtra(PlayerListViewActivity.TEAM, league.firstTeam());
                startActivity(intent);
            }
        });

        secondTeamItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerListViewActivity.class);
                intent.putExtra(PlayerListViewActivity.TEAM, league.secondTeam());
                startActivity(intent);
            }
        });

        gamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameListActivity.class);
                intent.putExtra(GameListActivity.LEAGUE, league);
                startActivity(intent);
            }
        });
    }
}
