package gus.android.mvvmhilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import gus.android.mvvmhilt.repository.MainRepository
import gus.android.mvvmhilt.retrofit.BlogRetrofit
import gus.android.mvvmhilt.retrofit.NetworkMapper
import gus.android.mvvmhilt.room.BlogDao
import gus.android.mvvmhilt.room.CacheMapper
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}