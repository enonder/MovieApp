package com.elifnuronder.movieapp.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.elifnuronder.movieapp.data.local.dao.FavoriteMovieDao
import com.elifnuronder.movieapp.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    
    companion object {
        const val DATABASE_NAME = "movie_app_database"
    }
}