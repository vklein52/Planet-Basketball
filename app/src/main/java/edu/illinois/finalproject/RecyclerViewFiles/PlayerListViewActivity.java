package edu.illinois.finalproject.RecyclerViewFiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Team;

public class PlayerListViewActivity extends AppCompatActivity {

    public static final String TEAM = "TEAM";

    private PlayerAdapter playerAdapter;
    private Team team;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list_view_actvity);

        team = getIntent().getParcelableExtra(TEAM);
        setTitle(team.getName());

        initializeRecyclerView();

    }

    private void initializeRecyclerView() {
//        StringComparator.setSortType(getPreferencesSortType());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.player_list_recycler_view);

        playerAdapter = new PlayerAdapter(team.getPlayers(), this);
        recyclerView.setAdapter(playerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
