package com.elifnuronder.movieapp.di

import com.elifnuronder.movieapp.data.repository.FavoriteRepositoryImpl
import com.elifnuronder.movieapp.data.repository.MovieRepositoryImpl
import com.elifnuronder.movieapp.domain.repository.FavoriteRepository
import com.elifnuronder.movieapp.domain.repository.MovieRepository
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
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
    
    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository
}
