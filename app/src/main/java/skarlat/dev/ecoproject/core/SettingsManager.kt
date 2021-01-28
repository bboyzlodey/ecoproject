package skarlat.dev.ecoproject.core

import android.content.SharedPreferences

class SettingsManager(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val USER_NAME = "user_name"
        const val USER_PROGRESS = "user_progress"
    }

    val userName
        get() = sharedPreferences.getString(USER_NAME, "")

    val userProgress
        get() = sharedPreferences.getInt(USER_PROGRESS, 0)

    fun updateUserName(name: String) {
        sharedPreferences.edit().putString(USER_NAME, name).apply()
    }

    fun updateProgress(progress: Int) {
        sharedPreferences.edit().putInt(USER_PROGRESS, progress).apply()
    }

    fun clearSettings() {
        sharedPreferences.edit().clear().apply()
    }
}