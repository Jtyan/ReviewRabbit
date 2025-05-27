package learningprogramming.academy.reviewrabbit.data.repository

import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.network.CompanyApiService
import javax.inject.Inject
import javax.inject.Singleton

interface CompanyRepository {
    suspend fun getAllCompanies(): List<CompanyApiResponse>
    suspend fun getCompanyById(companyId: Int): CompanyApiResponse
    suspend fun getCompanyFilters(): CompanyFilters
}

@Singleton
class CompanyRepositoryImpl @Inject constructor(private val apiService: CompanyApiService): CompanyRepository {
    override suspend fun getAllCompanies(): List<CompanyApiResponse> {
        return apiService.getAllCompanies()
    }

    override suspend fun getCompanyById(companyId: Int): CompanyApiResponse {
        return apiService.getCompanyById(companyId)
    }

    override suspend fun getCompanyFilters(): CompanyFilters {
        return apiService.getCompanyFilters()
    }
}