package skarlat.dev.ecoproject.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.User
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Const.ECO_TIPS_PREFERENCES)

class AppCache @Inject constructor(private val context: Context) {

    companion object {
        private val userKey = stringPreferencesKey("user_key")
        private val usersCount = intPreferencesKey("users_count_key")
        private val userProgressKey = intPreferencesKey("user_progress")
    }

    val userFlow: Flow<User>
        get() {
            return context.dataStore.data.map { it.user }
            /*flow {
                    context.dataStore.data.collectLatest { value ->
                        Timber.d("userFLow.emit new user")
                        emit(value.user)
                    }
                }*/
        }

    val userCountFlow: Flow<Int>
        get() {
            return context.dataStore.data.map { it.userCount }/* flow {
                emit(0)
                *//*context.dataStore.data.collectLatest { value ->
                    Timber.d("userCountFLow.emit new userCount")
                    emit(0)
                }*//*
            }*/
        }

    val userProgressFlow: Flow<Int>
        get() {
            return context.dataStore.data.map { it.userProgress }
        }

    fun setUser(user: User) {
        GlobalScope.launch {
            context.dataStore.edit {
                it[userKey] = Json.encodeToString(user)
            }
        }
    }

    fun setCount(count: Int) {
        GlobalScope.launch {
            context.dataStore.edit {
                it[usersCount] = count
            }
        }
    }

    fun clear() {
        GlobalScope.launch {
            context.dataStore.edit {
                it.clear()
            }
        }
    }

    fun setUserProgress(progress: Int) {
        GlobalScope.launch {
            context.dataStore.edit {
                it[userProgressKey] = progress
            }
        }
    }

    private val Preferences.user: User
        get() {
            return Json.decodeFromString(get(userKey) ?: return User.empty)
        }

    private val Preferences.userCount: Int
        get() = this[usersCount] ?: 0

    private val Preferences.userProgress: Int
        get() = this[userProgressKey] ?: 0
}