package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.EmailAuthActivity;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.League;
import edu.illinois.finalproject.SimulationFiles.User;

public class LeagueListActivity extends AppCompatActivity {

    private static final String TAG = LeagueListActivity.class.getSimpleName();

    private LeagueAdapter leagueAdapter;
    private User user;
    private List<League> leagues = new ArrayList<>();

    private String[] menuTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_list);

        setTitle("Your Leagues");

        initializeRecyclerView();
//
//        menuTitles = getResources().getStringArray(R.array.league_options);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerList = (ListView) findViewById(R.id.left_drawer);
//
//        // Set the adapter for the list view
//        drawerList.setAdapter(new ArrayAdapter<String>(this,
//                R.layout.drawer_item, menuTitles));
//        // Set the list's click listener
//        drawerList.setOnItemClickListener(new DrawerItemClickListener());

    }


    /**
     * Initializes the RecyclerView by setting the SortType, generating strings to populate the
     * RecyclerView with and then sets the LayoutManager.
     */
    private void initializeRecyclerView() {
//        StringComparator.setSortType(getPreferencesSortType());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.league_recycler_view);

        downloadUser();

        leagueAdapter = new LeagueAdapter(leagues, this);
        recyclerView.setAdapter(leagueAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void downloadLeagues() {
        Log.d(TAG, "Attempt to download league");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        for (String leagueId : user.getLeagueIds()) {
            DatabaseReference myRef = database.getReference("Leagues").child(leagueId);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    leagues.add(dataSnapshot.getValue(League.class));
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

    private void downloadUser() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            Log.d(TAG, "UID: " + fbUser.getUid());
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Users").child(fbUser.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);
                    Log.d(TAG, "Downloaded a user");
                    downloadLeagues();
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
//
//    /**
//     * Checks the sortType to see whether updating the UI is necessary (bedause of a setting change)
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        checkSortType();
//    }
//
//    /**
//     * Checks to make sure the SortType held by SharedPreferences is the same as what the
//     * StringComparator was last updated to be. If there is a difference, updates the the Comparator's
//     * SortType and updates the adapter with this change resorting the data.
//     */
//    private void checkSortType() {
//        SortType temp = getPreferencesSortType();
//        SortType sortType = StringComparator.getSortType();
//        if (temp != sortType) {
//            StringComparator.setSortType(temp);
//            stringAdapter.sortData();
//        }
//    }
//
//    /**
//     * @return The SortType the SharedPreferences holds.
//     */
//    private SortType getPreferencesSortType() {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        return SortType.fromString(sharedPreferences.getString(SettingsFragment.SORT_TYPE, ""));
//    }
//
//    /*In real app the following four methods would be included in a generic class such as a
//    BaseActivity, which would extend AppCompatActivity and implement OnQueryChangedListener. Then,
//    all other activities would extend BaseActivity, rather than AppCompatActivity, so that all classes
//    are searchable and update the sort types appropriately.
//
//    This seems to be the way to most easily generify this process across any comparable data type
//    (i.e. in the real app, teams, players, etc).
//    */
//
//    /**
//     * Creates and initializes the Options Menu by inflating the menu and adding the
//     * QueryTextListener for the SearchView
//     *
//     * @param menu The Menu to be inflated
//     * @return A boolean of whether the menu was successfully inflated.
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_activity_menu, menu);
//
//        final MenuItem searchItem = menu.findItem(R.id.search_item);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(this);
//
//        return true;
//    }
//
//    /**
//     * Defines the behaviour for the MenuItems when they are selected.
//     *
//     * @param item The item in the Menu that was selected.
//     * @return True if the behaviour was handled by the method, false if the generic behaviour must
//     * be used
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        switch (itemId) {
//            case R.id.search_item:
//                return true;
//
//            case R.id.settings_item: {
//                Intent intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//
//                return true;
//            }
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//
//    /**
//     * Callback function for when a query is submitted through the SearchView
//     *
//     * @param query The query that was submitted
//     * @return True if the callback was handled by the Listener, false if the SearchView must use
//     * default functionality.
//     */
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        stringAdapter.filter(query);
//        return true;
//    }
//
//    /**
//     * Callback function for when the query in the SearchView has changed
//     *
//     * @param query The query that was typed
//     * @return True if the callback was handled by the Listener, false if the SearchView must use
//     * default functionality.
//     */
//    @Override
//    public boolean onQueryTextChange(String query) {
//        stringAdapter.filter(query);
//        return true;
//    }
//private class DrawerItemClickListener implements ListView.OnItemClickListener {
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        selectItem(position);
//    }

    /**
     * Swaps fragments in the main content view
     */
//    private void selectItem(int position) {
//        // Create a new fragment and specify the planet to show based on position
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
//    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getActionBar().setTitle(mTitle);
//    }
}


//}
