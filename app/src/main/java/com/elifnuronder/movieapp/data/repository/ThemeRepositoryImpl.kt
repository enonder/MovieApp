package com.elifnuronder.movieapp.data.repository

import com.elifnuronder.movieapp.data.preferences.ThemePreferences
import com.elifnuronder.movieapp.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    private val themePreferences: ThemePreferences
) : ThemeRepository {
    
    override val isDarkTheme: Flow<Boolean> = themePreferences.isDarkTheme
    
    override suspend fun setDarkTheme(isDark: Boolean) {
        themePreferences.setDarkTheme(isDark)
    }
}
