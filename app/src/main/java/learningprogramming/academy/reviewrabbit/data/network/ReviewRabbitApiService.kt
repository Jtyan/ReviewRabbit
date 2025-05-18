package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.CompanyFilters
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewRabbitApiService {
    @GET("companies")
    suspend fun getAllCompanies(): List<CompanyApiResponse>

    @GET("companies/{companyId}")
    suspend fun getCompanyById(@Path("companyId") companyId: String): CompanyApiResponse

    @GET("companies/filters")
    suspend fun getCompanyFilters(): CompanyFilters
}