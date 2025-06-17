package learningprogramming.academy.reviewrabbit.data.network

import learningprogramming.academy.reviewrabbit.data.model.CompanyApiResponse
import learningprogramming.academy.reviewrabbit.data.model.CompanyFilters
import learningprogramming.academy.reviewrabbit.data.model.PostCompanyApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CompanyApiService {
    @GET("companies")
    suspend fun getAllCompanies(): List<CompanyApiResponse>

    @GET("companies/{companyId}")
    suspend fun getCompanyById(@Path("companyId") companyId: Int): CompanyApiResponse

    @GET("companies/filters")
    suspend fun getCompanyFilters(): CompanyFilters

    @POST("companies")
    suspend fun postNewCompany(@Body postCompanyApi: PostCompanyApi): Response<Unit>
}