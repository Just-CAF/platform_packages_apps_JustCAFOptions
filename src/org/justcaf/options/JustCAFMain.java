/*
 * Copyright (C) 2020 Just-CAF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.justcaf.options;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.Surface;
import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;


import com.android.settings.SettingsPreferenceFragment;

public class JustCAFMain extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private Context mContext;
    private ListPreference mClockFace;
    private ContentResolver mContentResolver;

    private static final String TAG = JustCAFMain.class.getSimpleName();

    private static final String CLOCK_FACE = "android.theme.customization.clock_Face";

    private static final String KEY_CLOCK_FACE = "lock_screen_custom_clock_face";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.main);
        mContentResolver = getActivity().getContentResolver();

        final PreferenceScreen ps = getPreferenceScreen();
        mClockFace = (ListPreference) ps.findPreference(CLOCK_FACE);
        mClockFace.setOnPreferenceChangeListener(this);

        // Initialize default/selected values
        mClockFace.setValue(Settings.Secure.getString(mContentResolver, KEY_CLOCK_FACE));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

	if (preference == mClockFace) {
            String newFace = (String) newValue;

            Log.i(TAG, "onPreferenceChange");
            Log.i(TAG, newFace);
            Settings.Secure.putString(mContentResolver, KEY_CLOCK_FACE, newFace);
        }
        return true;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.JUSTCAF_OPTIONS;
    }
}
