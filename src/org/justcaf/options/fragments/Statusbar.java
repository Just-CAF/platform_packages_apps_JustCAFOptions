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
import android.content.Context;
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

import android.util.Log;
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

public class Statusbar extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String BATTERY_STYLE = "status_bar_battery_style";
    private static final String BATTERY_PERCENT = "status_bar_show_battery_percent";
    private static final String IN_OUT_ARROWS = "status_bar_show_in_out_arrows";

    private ListPreference mBatteryStyle;
    private SwitchPreference mBatteryPercent;
    private SwitchPreference mInOutArrows;
    private Context mContext;
    private ContentResolver mContentResolver;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.statusbar);
        PreferenceScreen ps = getPreferenceScreen();
        mContentResolver = getActivity().getContentResolver();

        mBatteryStyle = (ListPreference) ps.findPreference(BATTERY_STYLE);
        mBatteryStyle.setOnPreferenceChangeListener(this);
        mBatteryStyle.setValue(Integer.toString(Settings.System.getInt(mContentResolver, BATTERY_STYLE, 0)));

        mBatteryPercent = (SwitchPreference) ps.findPreference(BATTERY_PERCENT);
        mBatteryPercent.setOnPreferenceChangeListener(this);
        mBatteryPercent.setChecked(Settings.System.getInt(mContentResolver, BATTERY_PERCENT, 0) == 1 ? true : false);

        mInOutArrows = (SwitchPreference) ps.findPreference(IN_OUT_ARROWS);
        mInOutArrows.setOnPreferenceChangeListener(this);
        mInOutArrows.setChecked(Settings.System.getInt(mContentResolver, Settings.System.SHOW_STATUSBAR_IN_OUT, 0)
		 == 1 ? true : false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mBatteryStyle) {
            int batteryStyle = Integer.parseInt((String) newValue);
            Settings.System.putInt(mContentResolver, BATTERY_STYLE, batteryStyle);
            return true;
        }
        if (preference == mBatteryPercent) {
            int batteryPercent = ((Boolean) newValue) ? 1 : 0;
            Settings.System.putInt(mContentResolver, BATTERY_PERCENT, batteryPercent);
            return true;
        }
        if (preference == mInOutArrows) {
            int show = ((Boolean) newValue) ? 1 : 0;
            Settings.System.putInt(mContentResolver, Settings.System.SHOW_STATUSBAR_IN_OUT, show);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.JUSTCAF_OPTIONS;
    }
}
