package edu.illinois.finalproject.RecyclerViewFiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.League;

public class GameListActivity extends AppCompatActivity {

    public static final String LEAGUE = "LEAGUE";
    private static final String TITLE = "Games";

    private GameAdapter gameAdapter;
    private League league;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        setTitle(TITLE);

        league = getIntent().getParcelableExtra(LEAGUE);
        initializeRecyclerView();

    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.game_list_recycler_view);

        gameAdapter = new GameAdapter(league.getGames(), this);
        recyclerView.setAdapter(gameAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
