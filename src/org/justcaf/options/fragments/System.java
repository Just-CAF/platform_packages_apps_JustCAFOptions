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

package org.justcaf.options.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.provider.Settings;
import com.android.settings.R;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.SwitchPreference;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class System extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private SwitchPreference mEmergencyButtonPowerMenu;
    private SwitchPreference mQuickUnlock;
    private ContentResolver mContentResolver;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.system);
        PreferenceScreen ps = getPreferenceScreen();
        mContentResolver = getActivity().getContentResolver();

        mEmergencyButtonPowerMenu = (SwitchPreference) ps.findPreference("em_key_powermenu");
        mEmergencyButtonPowerMenu.setOnPreferenceChangeListener(this);
        mEmergencyButtonPowerMenu.setChecked(Settings.System.getInt(mContentResolver,
                        Settings.Global.SHOW_EMERGENCY_BUTTON_POWER_MENU, 0) == 1 ? true : false);

        mQuickUnlock = (SwitchPreference) ps.findPreference("quick_unlock");
        mQuickUnlock.setOnPreferenceChangeListener(this);
        mQuickUnlock.setChecked(Settings.System.getInt(mContentResolver,
                        Settings.Global.LOCKSCREEN_QUICK_UNLOCK_CONTROL, 0) == 1 ? true : false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mEmergencyButtonPowerMenu) {
            int show = ((Boolean) newValue) ? 1 : 0;
            Settings.Global.putInt(mContentResolver,
                    Settings.Global.SHOW_EMERGENCY_BUTTON_POWER_MENU, show);
            return true;
        }
        if (preference == mQuickUnlock) {
            int enable = ((Boolean) newValue) ? 1 : 0;
            Settings.Global.putInt(mContentResolver,
                    Settings.Global.LOCKSCREEN_QUICK_UNLOCK_CONTROL, enable);
            return true;
        }

        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.JUSTCAF_OPTIONS;
    }
}
