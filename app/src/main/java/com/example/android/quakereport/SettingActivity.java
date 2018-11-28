package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    /*It consist of the support library that get updates time to time and
    Subclass AppCompatActivity, can give us many feature like, we can add the action bar to our app
    * It adds the backward compatibilty so that older device can perform all new features
    * It enables the material design to current activity*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
    }
        /*The PreferenceFragment is the abstract class that helps to store the preference of the user on the device via the SettingActivity. This will be
        * even after the app is close or phone is restarted. The data will be store on the memory of the device.
        * It provide a way to edit the preference. Thus, it provide the list of the editing widget*/
    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.setting_xml);
                Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
                bindPreferenceSummaryToValue(minMagnitude);
                Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
                bindPreferenceSummaryToValue(orderBy);
            }

            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {     String stringValue = value.toString();
                if (preference instanceof ListPreference) {
                    ListPreference listPreference = (ListPreference) preference;
                    int prefIndex = listPreference.findIndexOfValue(stringValue);
                    if (prefIndex >= 0) {
                        CharSequence[] labels = listPreference.getEntries();
                        preference.setSummary(labels[prefIndex]);
                    }
                } else {
                    preference.setSummary(stringValue);
                }
                return true;
            }

            private void bindPreferenceSummaryToValue(Preference preference) {
                preference.setOnPreferenceChangeListener(this);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
                String preferenceString = preferences.getString(preference.getKey(), "");
                onPreferenceChange(preference, preferenceString);
            }
    }
}