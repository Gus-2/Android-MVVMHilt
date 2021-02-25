package gus.android.mvvmhilt.repository

import gus.android.mvvmhilt.model.Blog
import gus.android.mvvmhilt.retrofit.BlogRetrofit
import gus.android.mvvmhilt.retrofit.NetworkMapper
import gus.android.mvvmhilt.room.BlogDao
import gus.android.mvvmhilt.room.CacheMapper
import gus.android.mvvmhilt.utlil.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
){
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try{
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for(blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        } catch(e:Exception) {

        }
    }
}