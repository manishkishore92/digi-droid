package com.manishkishore.digidroid.data.repository

import android.content.Context

class SettingsRepository(context: Context) {
    private val preferences = context.getSharedPreferences("digi_droid_settings", Context.MODE_PRIVATE)

    var maintainerMode: Boolean
        get() = preferences.getBoolean(KEY_MAINTAINER_MODE, false)
        set(value) = preferences.edit().putBoolean(KEY_MAINTAINER_MODE, value).apply()

    companion object {
        private const val KEY_MAINTAINER_MODE = "maintainer_mode"
    }
}
