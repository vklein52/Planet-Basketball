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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import edu.illinois.finalproject.MenuFiles.PlayerComparator;
import edu.illinois.finalproject.MenuFiles.SettingsActivity;
import edu.illinois.finalproject.MenuFiles.SettingsFragment;
import edu.illinois.finalproject.MenuFiles.SortType;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Team;

public class PlayerListViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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

    @Override
    protected void onResume() {
        super.onResume();
        checkSortType();
    }

    /**
     * Initializes the RecyclerView by setting the SortType, populating the RecyclerView with a
     * PlayerAdapter, and setting the LayoutManager
     */
    private void initializeRecyclerView() {
        PlayerComparator.setSortType(getPreferencesSortType());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.player_list_recycler_view);

        playerAdapter = new PlayerAdapter(team.getPlayers(), new PlayerComparator(), false, this);
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
