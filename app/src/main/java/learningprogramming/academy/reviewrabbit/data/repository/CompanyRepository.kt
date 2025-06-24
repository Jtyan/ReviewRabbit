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
    suspend fun getFilteredCompaniesList(companyFilters: CompanyFilters): CompanyApiResult
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

    override suspend fun getFilteredCompaniesList(companyFilters: CompanyFilters): CompanyApiResult {
        return try {
            val response = apiService.getFilteredCompanies(companyFilters)

            if (response.isSuccessful && response.body() != null) {
                    Log.i("CompanyRepository", "Get list of filtered companies successful.")
                    CompanyApiResult.GetFilteredCompaniesSuccess(response.body()!!)
            } else {
                Log.e(
                    "CompanyRepository",
                    "Failed to get list of filtered companies. Code: ${response.code()}, Message: ${response.message()}"
                )
                CompanyApiResult.Error("Failed to retrieve companies. Please try again.")
            }
        } catch (e: IOException) {
            Log.e("CompanyRepository", "Failed to get list of filtered companies. Network error has occurred. $e")
            return CompanyApiResult.NetworkError
        } catch (e: Exception) {
            Log.e("CompanyRepository", "Failed to get list of filtered companies. Unknown error has occurred.")
            return CompanyApiResult.UnknownError(e)
        }
    }
}

sealed interface CompanyApiResult {
    data object PostCompanySuccess : CompanyApiResult
    data class GetFilteredCompaniesSuccess(val filteredCompanies: List<CompanyApiResponse>): CompanyApiResult
    data class Error(val message: String) : CompanyApiResult
    data object NetworkError : CompanyApiResult
    data class UnknownError(val exception: Throwable) : CompanyApiResult
}