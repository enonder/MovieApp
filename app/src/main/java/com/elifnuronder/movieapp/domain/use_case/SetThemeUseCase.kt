package com.elifnuronder.movieapp.domain.use_case

import com.elifnuronder.movieapp.domain.repository.ThemeRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository
) {
    suspend operator fun invoke(isDark: Boolean) {
        themeRepository.setDarkTheme(isDark)
    }
}
