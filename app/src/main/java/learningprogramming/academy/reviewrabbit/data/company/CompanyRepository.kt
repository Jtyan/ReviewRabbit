package learningprogramming.academy.reviewrabbit.data.company

import android.util.Log
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyResponse
import learningprogramming.academy.reviewrabbit.data.company.model.CreateCompanyRequest
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface CompanyRepository {
    suspend fun getAllCompanies(): List<CompanyResponse>
    suspend fun getCompanyById(companyId: Int): CompanyResponse
    suspend fun getCompanyFilters(): CompanyFilters
    suspend fun postNewCompany(createCompanyRequest: CreateCompanyRequest): CompanyApiResult
    suspend fun getFilteredCompaniesList(companyFilters: CompanyFilters): CompanyApiResult
}

@Singleton
class CompanyRepositoryImpl @Inject constructor(private val apiService: CompanyApiService) :
    CompanyRepository {
    override suspend fun getAllCompanies(): List<CompanyResponse> {
        return apiService.getAllCompanies()
    }

    override suspend fun getCompanyById(companyId: Int): CompanyResponse {
        return apiService.getCompanyById(companyId)
    }

    override suspend fun getCompanyFilters(): CompanyFilters {
        return apiService.getCompanyFilters()
    }

    override suspend fun postNewCompany(createCompanyRequest: CreateCompanyRequest): CompanyApiResult {
        return try {
            val response = apiService.postNewCompany(createCompanyRequest)

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
    data class GetFilteredCompaniesSuccess(val filteredCompanies: List<CompanyResponse>):
        CompanyApiResult
    data class Error(val message: String) : CompanyApiResult
    data object NetworkError : CompanyApiResult
    data class UnknownError(val exception: Throwable) : CompanyApiResult
}