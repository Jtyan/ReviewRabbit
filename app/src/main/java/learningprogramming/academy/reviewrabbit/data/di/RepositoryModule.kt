package learningprogramming.academy.reviewrabbit.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import learningprogramming.academy.reviewrabbit.data.company.CompanyRepository
import learningprogramming.academy.reviewrabbit.data.company.CompanyRepositoryImpl
import learningprogramming.academy.reviewrabbit.data.invite.InviteRepository
import learningprogramming.academy.reviewrabbit.data.invite.InviteRepositoryImpl
import learningprogramming.academy.reviewrabbit.data.review.ReviewsRepository
import learningprogramming.academy.reviewrabbit.data.review.ReviewsRepositoryImpl
import learningprogramming.academy.reviewrabbit.data.user.UserRepository
import learningprogramming.academy.reviewrabbit.data.user.UserRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindInviteRepository(
        inviteRepositoryImpl: InviteRepositoryImpl
    ): InviteRepository
}