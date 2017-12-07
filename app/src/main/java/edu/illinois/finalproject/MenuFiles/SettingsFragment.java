package edu.illinois.finalproject.MenuFiles;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import edu.illinois.finalproject.R;

//No code copied, however, used https://developer.android.com/guide/topics/ui/settings.html as
//reference
public class SettingsFragment extends PreferenceFragment {

    public static final String SORT_TYPE = "list_sort_type";

    /**
     * Creates the SettingsFragment from the template Preferences.xml
     *
     * @param savedInstanceState A bundle representing a possible state from which this activity
     *                           could be recreated from
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
