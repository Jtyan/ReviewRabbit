package learningprogramming.academy.reviewrabbit.data.company

import learningprogramming.academy.reviewrabbit.data.company.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.company.model.CompanyResponse
import learningprogramming.academy.reviewrabbit.data.company.model.CreateCompanyRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CompanyApiService {
    @GET("companies")
    suspend fun getAllCompanies(): List<CompanyResponse>

    @GET("companies/{companyId}")
    suspend fun getCompanyById(@Path("companyId") companyId: Int): CompanyResponse

    @GET("companies/filters")
    suspend fun getCompanyFilters(): CompanyFilters

    @POST("companies")
    suspend fun postNewCompany(@Body createCompanyRequest: CreateCompanyRequest): Response<Unit>

    @POST("companies/search")
    suspend fun getFilteredCompanies(@Body companyFilters: CompanyFilters): Response<List<CompanyResponse>>
}