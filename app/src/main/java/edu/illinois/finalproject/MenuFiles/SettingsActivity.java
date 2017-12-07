package edu.illinois.finalproject.MenuFiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//No code copied, however, used https://developer.android.com/guide/topics/ui/settings.html as
//reference
public class SettingsActivity extends AppCompatActivity {

    /**
     * Creates the SettingsActivity and loads the SettingsFragment into the UI
     *
     * @param savedInstanceState A bundle representing a possible state from which this activity
     *                           could be recreated from
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }

}
