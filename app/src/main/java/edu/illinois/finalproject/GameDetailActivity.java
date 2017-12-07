package edu.illinois.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.illinois.finalproject.SimulationFiles.Game;

public class GameDetailActivity extends AppCompatActivity {

    public static final String GAME = "GAME";

    private TextView awayName;
    private TextView awayScore;
    private TextView homeName;
    private TextView homeScore;

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        game = getIntent().getParcelableExtra(GAME);

        initializeViews();
        populateViews();
    }

    private void initializeViews() {
        awayName = (TextView) findViewById(R.id.game_detail_away_team_name);
        awayScore = (TextView) findViewById(R.id.game_detail_away_team_score);
        homeName = (TextView) findViewById(R.id.game_detail_home_team_name);
        homeScore = (TextView) findViewById(R.id.game_detail_home_team_score);
    }

    private void populateViews() {
        awayName.setText(game.getAwayName());
        String aScore = "" + game.getAwayScore();
        awayScore.setText(aScore);

        homeName.setText(game.getHomeName());
        String hScore = "" + game.getHomeScore();
        homeScore.setText(hScore);
    }
}
