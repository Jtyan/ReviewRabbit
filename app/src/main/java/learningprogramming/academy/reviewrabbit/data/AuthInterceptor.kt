package learningprogramming.academy.reviewrabbit.data

import kotlinx.coroutines.runBlocking
import learningprogramming.academy.reviewrabbit.data.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = runBlocking { sessionManager.getCurrentToken() }
        request.addHeader("Authorization", "Bearer $token")
        request.addHeader("Accept", "application/json")
        return chain.proceed(request.build())
    }
}
