package learningprogramming.academy.reviewrabbit.data.session

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import learningprogramming.academy.reviewrabbit.data.user.model.LoginUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    val userTokenFlow: Flow<String?> = dataStore.data
        .catchIOException()
        .map { preferences ->
            preferences[SessionKeys.USER_TOKEN]
        }

    val tokenExpirationFlow: Flow<Long?> = dataStore.data
        .catchIOException()
        .map { preferences ->
            preferences[SessionKeys.TOKEN_EXPIRATION]
        }

    val userIdFlow: Flow<Long?> = dataStore.data
        .catchIOException()
        .map { preferences ->
            preferences[SessionKeys.USER_ID]
        }

    val userEmailFlow: Flow<String?> = dataStore.data
        .catchIOException()
        .map { preferences ->
            preferences[SessionKeys.USER_EMAIL]
        }

    val isLoggedInFlow: Flow<Boolean> = dataStore.data
        .catchIOException()
        .map { preferences ->
            val tokenIsPresent = !preferences[SessionKeys.USER_TOKEN].isNullOrBlank()
            val expiration = preferences[SessionKeys.TOKEN_EXPIRATION] ?: 0L
            val notExpired = System.currentTimeMillis() / 1000 < expiration

            tokenIsPresent && notExpired && preferences[SessionKeys.IS_LOGGED_IN] == true
        }

    suspend fun getUserId(): Long? = userIdFlow.firstOrNull()

    suspend fun getCurrentToken(): String? = userTokenFlow.firstOrNull()

    suspend fun getEmail(): String? = userEmailFlow.firstOrNull()

    suspend fun isSessionValid(): Boolean {
        return isLoggedInFlow.firstOrNull() ?: false
    }

    suspend fun saveSession(loginResponse: LoginUserResponse) {
        dataStore.edit { preferences ->
            preferences[SessionKeys.USER_TOKEN] = loginResponse.token
            preferences[SessionKeys.TOKEN_EXPIRATION] = loginResponse.expiration
            preferences[SessionKeys.USER_ID] = loginResponse.id
            preferences[SessionKeys.USER_EMAIL] = loginResponse.email
            preferences[SessionKeys.IS_LOGGED_IN] = true
        }
        Log.d("SessionManager", "Session saved for user: ${loginResponse.email}")
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(SessionKeys.USER_TOKEN)
            preferences.remove(SessionKeys.TOKEN_EXPIRATION)
            preferences.remove(SessionKeys.USER_ID)
            preferences.remove(SessionKeys.USER_EMAIL)
            preferences[SessionKeys.IS_LOGGED_IN] = false
        }
        Log.d("SessionManager", "Session cleared.")
    }

    private fun Flow<Preferences>.catchIOException(): Flow<Preferences> = this.catch { exception ->
        if (exception is IOException) {
            Log.e("SessionManager", "Error reading preferences.", exception)
            emit(emptyPreferences())
        }else {
            throw exception
        }
    }
}