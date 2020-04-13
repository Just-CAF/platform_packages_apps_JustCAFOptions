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

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

public class Lockscreen extends SettingsPreferenceFragment {

    private ContentResolver mContentResolver;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.lockscreen);
        PreferenceScreen ps = getPreferenceScreen();
        mContentResolver = getActivity().getContentResolver();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.JUSTCAF_OPTIONS;
    }
}
