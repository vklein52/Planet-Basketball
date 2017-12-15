package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.MenuFiles.PlayerComparator;
import edu.illinois.finalproject.MenuFiles.SettingsActivity;
import edu.illinois.finalproject.MenuFiles.SettingsFragment;
import edu.illinois.finalproject.MenuFiles.SortType;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Draft;
import edu.illinois.finalproject.SimulationFiles.League;
import edu.illinois.finalproject.SimulationFiles.Player;
import edu.illinois.finalproject.SimulationFiles.User;

public class DraftActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String DRAFT_KEY = "DRAFT_KEY";
    private static final String TAG = DraftActivity.class.getSimpleName();

    private PlayerAdapter playerAdapter;
    private List<Player> draftablePlayers = new ArrayList<>();
    private Draft draft;
    private String draftKey;

    private User firstUser;
    private User secondUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);

        setTitle("Draft");

        draftKey = getIntent().getStringExtra(DRAFT_KEY);

        initializeRecyclerView();
    }

    /**
     * Sets up the callback for whenever the draft is updated, including the initial download.
     * Also handles starting the creation of the league if the draft is over, and finishing the
     * draft activity if the draft no longer exists.
     */
    private void handleDraftChanges() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Drafts").child(draftKey);
        myRef.onDisconnect().removeValue();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                draft = dataSnapshot.getValue(Draft.class);
                if (draft == null) {
                    finish();
                } else if (draft.over() && draft.usersTurnToPick()) {
                    createLeague(draft);
                } else {
                    playerAdapter.setDraft(draft);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Failed to download a draft");
            }
        });
    }

    /**
     * Takes a draft and converts it to a League format, which indludes simming games, and then
     * uploads the League to firebase and adds the leagueId to the respective users' leagueId lists
     *
     * @param draft The draft from which to create the league
     */
    private void createLeague(Draft draft) {
        League league = new League(draft);
        String leagueId = league.getId();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference leagueRef = database.getReference("Leagues").child(leagueId);

        leagueRef.setValue(league);
        updateUserLeagueIds(leagueId);

        DatabaseReference draftRef = database.getReference("Drafts").child(draft.getKey());
        draftRef.removeValue();
    }

    /**
     * Adds the leagueId to the respective users' leagueId lists
     *
     * @param leagueId The leagueId to add
     */
    private void updateUserLeagueIds(final String leagueId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference firstRef = database.getReference("Users").child(draft.getFirstTeamId());
        firstRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstUser = dataSnapshot.getValue(User.class);
                firstUser.appendLeagueIds(leagueId);
                firstRef.setValue(firstUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference secondRef = database.getReference("Users").child(draft.getSecondTeamId());
        secondRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                secondUser = dataSnapshot.getValue(User.class);
                secondUser.appendLeagueIds(leagueId);
                secondRef.setValue(secondUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSortType();
    }

    /**
     * Initializes the RecyclerView by making the initial Draft download, creating the PlayerAdapter,
     * and populating the RecyclerView with it.
     */
    private void initializeRecyclerView() {
        PlayerComparator.setSortType(getPreferencesSortType());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.draft_list_recycler_view);

        handleDraftChanges();

        playerAdapter = new PlayerAdapter(draftablePlayers, new PlayerComparator(), true, this);
        recyclerView.setAdapter(playerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Helper method to check if the user has changed the SortType for the comparator
     */
    private void checkSortType() {
        SortType temp = getPreferencesSortType();
        SortType sortType = PlayerComparator.getSortType();
        if (temp != sortType) {
            PlayerComparator.setSortType(temp);
            playerAdapter.sortData();
        }
    }

    /**
     * @return The SortType the SharedPreferences holds.
     */
    private SortType getPreferencesSortType() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return SortType.fromString(sharedPreferences.getString(SettingsFragment.SORT_TYPE, ""));
    }

    /*In real app the following four methods would be included in a generic class such as a
    BaseActivity, which would extend AppCompatActivity and implement OnQueryChangedListener. Then,
    all other activities would extend BaseActivity, rather than AppCompatActivity, so that all classes
    are searchable and update the sort types appropriately.

    This seems to be the way to most easily generify this process across any comparable data type
    (i.e. in the real app, teams, players, etc).
    */

    /**
     * Creates and initializes the Options Menu by inflating the menu and adding the
     * QueryTextListener for the SearchView
     *
     * @param menu The Menu to be inflated
     * @return A boolean of whether the menu was successfully inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.search_item);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    /**
     * Defines the behaviour for the MenuItems when they are selected.
     *
     * @param item The item in the Menu that was selected.
     * @return True if the behaviour was handled by the method, false if the generic behaviour must
     * be used
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.search_item:
                return true;

            case R.id.settings_item: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Callback function for when a query is submitted through the SearchView
     *
     * @param query The query that was submitted
     * @return True if the callback was handled by the Listener, false if the SearchView must use
     * default functionality.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        playerAdapter.filter(query);
        return true;
    }

    /**
     * Callback function for when the query in the SearchView has changed
     *
     * @param query The query that was typed
     * @return True if the callback was handled by the Listener, false if the SearchView must use
     * default functionality.
     */
    @Override
    public boolean onQueryTextChange(String query) {
        playerAdapter.filter(query);
        return true;
    }
}
