package learningprogramming.academy.reviewrabbit.data.repository

import android.util.Log
import androidx.datastore.core.IOException
import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.model.PostCompanyApi
import learningprogramming.academy.reviewrabbit.data.network.CompanyApiService
import javax.inject.Inject
import javax.inject.Singleton

interface CompanyRepository {
    suspend fun getAllCompanies(): List<CompanyApiResponse>
    suspend fun getCompanyById(companyId: Int): CompanyApiResponse
    suspend fun getCompanyFilters(): CompanyFilters
    suspend fun postNewCompany(postCompanyApi: PostCompanyApi): CompanyApiResult
}

@Singleton
class CompanyRepositoryImpl @Inject constructor(private val apiService: CompanyApiService) :
    CompanyRepository {
    override suspend fun getAllCompanies(): List<CompanyApiResponse> {
        return apiService.getAllCompanies()
    }

    override suspend fun getCompanyById(companyId: Int): CompanyApiResponse {
        return apiService.getCompanyById(companyId)
    }

    override suspend fun getCompanyFilters(): CompanyFilters {
        return apiService.getCompanyFilters()
    }

    override suspend fun postNewCompany(postCompanyApi: PostCompanyApi): CompanyApiResult {
        return try {
            val response = apiService.postNewCompany(postCompanyApi)

            if (response.isSuccessful) {
                Log.i("CompanyRepository", "Company added successfully.")
                CompanyApiResult.PostCompanySuccess
            } else {
                Log.e(
                    "CompanyRepository",
                    "Failed to add new company. ${response.code()}: ${response.message()}"
                )
                CompanyApiResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: IOException) {
            Log.e("CompanyRepository", "Failed to add new company. Network error has occurred. $e")
            return CompanyApiResult.NetworkError
        } catch (e: Exception) {
            Log.e("CompanyRepository", "Failed to add new company. Unknown error has occurred.")
            return CompanyApiResult.UnknownError(e)
        }
    }
}

sealed interface CompanyApiResult {
    data object PostCompanySuccess : CompanyApiResult
    data class Error(val message: String) : CompanyApiResult
    data object NetworkError : CompanyApiResult
    data class UnknownError(val exception: Throwable) : CompanyApiResult
}