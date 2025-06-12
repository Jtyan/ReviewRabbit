package learningprogramming.academy.reviewrabbit.data.repository

import android.util.Log
import androidx.datastore.core.IOException
import learningprogramming.academy.reviewrabbit.data.model.GetInvitesApiResponse
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesApiModel
import learningprogramming.academy.reviewrabbit.data.model.SendInvitesApiResponse
import learningprogramming.academy.reviewrabbit.data.network.InviteApiService
import retrofit2.Response
import javax.inject.Inject

interface InviteRepository {
    suspend fun getInvites(): CompanyInviteResult
    suspend fun sendInvites(invites: SendInvitesApiModel): CompanyInviteResult
}

class InviteRepositoryImpl @Inject constructor(
    private val inviteApiService: InviteApiService
) : InviteRepository {
    override suspend fun getInvites(): CompanyInviteResult {
        return try {
            val response: Response<List<GetInvitesApiResponse>> = inviteApiService.getInvites()

            if (response.isSuccessful) {
                val getInvitesApiResponse = response.body()

                if (!getInvitesApiResponse.isNullOrEmpty()) {
                    CompanyInviteResult.GetInviteSuccess(getInvitesApiResponse)
                } else {
                    Log.w("InviteRepository", "Login successful but body was null or no invites available")
                    CompanyInviteResult.Error("Login successful but body was null or no invites available")
                }
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage = if (!errorBodyString.isNullOrBlank()) {
                    errorBodyString
                } else {
                    "GetInvite failed: HTTP: ${response.code()}"
                }
                Log.e("InviteRepository", "(GetInvites) Api Error: ${response.code()}")
                CompanyInviteResult.Error(errorMessage)
            }
        } catch (e: IOException) {
            Log.e("InviteRepository", "(GetInvites) An I/O error occurred: ${e.message}")
            CompanyInviteResult.NetworkError
        } catch (e: Exception) {
            Log.e(
                "InviteRepository",
                "(GetInvites) An unexpected error occurred: ${e.message}"
            )
            CompanyInviteResult.UnknownError(e)
        }
    }

    override suspend fun sendInvites(invites: SendInvitesApiModel): CompanyInviteResult {
        return try {
            val response = inviteApiService.sendInvites(invites)
            if(response.isSuccessful && response.body() != null) {
                CompanyInviteResult.SendInviteSuccess(response.body()!!)
            } else {
                val errorBodyString = response.errorBody()?.string()
                val errorMessage = if (!errorBodyString.isNullOrBlank()) {
                    errorBodyString
                } else {
                    "Send invites failed: HTTP: ${response.code()}"
                }
                Log.e("InviteRepository", "(SendInvites) Api Error: ${response.code()}")
                CompanyInviteResult.Error(errorMessage)
            }
        } catch (e: IOException) {
            Log.e("InviteRepository", "(SendInvites) An I/O error occurred: ${e.message}")
            CompanyInviteResult.NetworkError
        } catch (e: Exception) {
            Log.e(
                "InviteRepository",
                "(SendInvites) An unexpected error occurred: ${e.message}"
            )
            CompanyInviteResult.UnknownError(e)
        }
    }
}

sealed interface CompanyInviteResult {
    data class GetInviteSuccess(val response: List<GetInvitesApiResponse?>) : CompanyInviteResult
    data class SendInviteSuccess(val response: SendInvitesApiResponse): CompanyInviteResult
    data class Error(val message: String) : CompanyInviteResult
    data object NetworkError : CompanyInviteResult
    data class UnknownError(val exception: Throwable) : CompanyInviteResult
}