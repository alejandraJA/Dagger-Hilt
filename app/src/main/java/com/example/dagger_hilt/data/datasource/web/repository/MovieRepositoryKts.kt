package com.example.dagger_hilt.data.datasource.web.repository

import androidx.lifecycle.LiveData
import com.example.dagger_hilt.data.datasource.database.dao.MovieDaoKts
import com.example.dagger_hilt.data.datasource.database.entities.MovieEntityKts
import com.example.dagger_hilt.data.datasource.web.api.MovieServiceKts
import com.example.dagger_hilt.data.datasource.web.models.MoviesResponseKts
import com.example.dagger_hilt.data.datasource.web.models.response.ApiResponseKts
import com.example.dagger_hilt.domain.IMovieRepositoryKts
import com.example.dagger_hilt.domain.NetworkBoundResourceKts
import com.example.dagger_hilt.sys.util.AppExecutorsKts
import com.example.dagger_hilt.sys.util.ConstantsKts
import com.example.dagger_hilt.sys.util.ResourceKts
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryKts @Inject constructor(
    private val dao: MovieDaoKts,
    private val service: MovieServiceKts,
    private val appExecutor: AppExecutorsKts
) : IMovieRepositoryKts {
    override fun loadMovies(): LiveData<ResourceKts<List<MovieEntityKts>>> =
        object : NetworkBoundResourceKts<List<MovieEntityKts>, MoviesResponseKts>(appExecutor) {
            override fun saveCallResult(response: MoviesResponseKts) =
                response.listMovies.forEach { movie ->
                    dao.setMovie(
                        MovieEntityKts(
                            movie.id,
                            movie.title,
                            movie.originalTitle,
                            movie.overview,
                            movie.posterPath,
                            like = false
                        )
                    )
                }

            override fun shouldFetch(data: List<MovieEntityKts>?): Boolean = data.isNullOrEmpty()

            override fun loadFromDb(): LiveData<List<MovieEntityKts>> = dao.getMovies()

            override fun createCall(): LiveData<ApiResponseKts<MoviesResponseKts>> =
                service.loadMovies(ConstantsKts.API_KEY)
        }.asLiveData()

    override fun updateMovie(id: Int, check: Boolean) = dao.updateMovie(id, check)

}