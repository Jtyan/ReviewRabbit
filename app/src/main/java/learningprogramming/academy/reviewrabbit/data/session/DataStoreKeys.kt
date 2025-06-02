package learningprogramming.academy.reviewrabbit.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val APP_SESSION_PREFS = "app_session_prefs"

val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(
    name = APP_SESSION_PREFS
)

object SessionKeys {
    val USER_TOKEN = stringPreferencesKey("user_token")
    val TOKEN_EXPIRATION = longPreferencesKey("token_expiration_seconds")
    val USER_ID = longPreferencesKey("user_id")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}