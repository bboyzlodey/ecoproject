package skarlat.dev.ecoproject.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import skarlat.dev.ecoproject.Const
import skarlat.dev.ecoproject.User
import timber.log.Timber
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Const.ECO_TIPS_PREFERENCES)

class AppCache @Inject constructor(private val context: Context) {

    companion object {
        private val userKey = stringPreferencesKey("user_key")
        private val usersCount = intPreferencesKey("users_count_key")
    }

    val userFlow: Flow<User>
        get() {
            return flow {
                context.dataStore.data.collectLatest { value ->
                    Timber.d("userFLow.emit new user")
                    emit(value.user)
                }
            }
        }

    val userCountFlow: Flow<Int>
        get() {
            return flow {
                context.dataStore.data.collectLatest { value ->
                    Timber.d("userCountFLow.emit new userCount")
                    emit(value.userCount)
                }
            }
        }

    private val Preferences.user: User
        get() {
            return Json.decodeFromString(get(userKey) ?: return User.empty)
        }

    private val Preferences.userCount: Int
        get() = this[usersCount] ?: 0
}