package learningprogramming.academy.reviewrabbit.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl
    ): CompanyRepository

    @Binds
    @Singleton
    abstract fun bindReviewsRepository(
        reviewsRepositoryImpl: ReviewsRepositoryImpl
    ): ReviewsRepository
}