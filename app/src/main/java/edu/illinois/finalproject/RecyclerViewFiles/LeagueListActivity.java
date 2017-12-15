package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.League;
import edu.illinois.finalproject.SimulationFiles.User;

public class LeagueListActivity extends AppCompatActivity {

    private static final String TAG = LeagueListActivity.class.getSimpleName();

    private Context context;
    private LeagueAdapter leagueAdapter;
    private User user;
    private Button createLeagueButton;
    private List<League> leagues = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_list);

        setTitle("Your Leagues");
        context = this;

        setUpButton();
        initializeRecyclerView();
    }


    /**
     * Initializes the RecyclerView by setting the LeagueAdapter and setting the LayoutManager
     */
    private void initializeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.league_recycler_view);

        downloadUser();

        leagueAdapter = new LeagueAdapter(leagues, this);
        recyclerView.setAdapter(leagueAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Sets up a callback for updating the league if it changed, as well as the initial download
     */
    private void downloadLeagues() {
        Log.d(TAG, "Attempt to download league");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        for (String leagueId : user.getLeagueIds()) {
            DatabaseReference myRef = database.getReference("Leagues").child(leagueId);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    League league = dataSnapshot.getValue(League.class);
                    replaceLeague(league);
                    Log.d(TAG, "Downloaded a league");
                    leagueAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "Failed to download a league");
                }
            });
        }


    }

    /**
     * Sets up a callback for updating the user if it changed, as well as the initial download
     */
    private void downloadUser() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            Log.d(TAG, "UID: " + fbUser.getUid());
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(fbUser.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    createLeagueButton.setEnabled(true);
                    User temp = user;
                    user = dataSnapshot.getValue(User.class);
                    if ((user != null) && (!user.equals(temp)) && (user.getLeagueIds() != null)) {
                        Log.d(TAG, "Downloaded a user");
                        downloadLeagues();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, databaseError.getMessage());
                    Log.d(TAG, "Failed to download user");
                }
            });
        } else {
            user = null;
        }
    }

    /**
     * Sets up the call back for the Button to create a new League
     */
    private void setUpButton() {
        createLeagueButton = (Button) findViewById(R.id.create_league_button);
        createLeagueButton.setEnabled(false);
        createLeagueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnlineUsersListActivity.class);
                intent.putExtra(OnlineUsersListActivity.USER, user);
                startActivity(intent);
            }
        });
    }

    /**
     * Method intending to add the league if it doesn't exist, and overwrite the old league in the
     * local list if it does
     *
     * @param league
     */
    private void replaceLeague(League league) {
        String id = league.getId();
        for (int i = 0; i < leagues.size(); i++) {
            if (leagues.get(i).getId().equals(id)) {
                leagues.remove(i);
                leagues.add(i, league);
                return;
            }
        }
        leagues.add(league);
    }

}

