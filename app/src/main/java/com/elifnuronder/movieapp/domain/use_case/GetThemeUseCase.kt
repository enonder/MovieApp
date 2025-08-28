package com.elifnuronder.movieapp.domain.use_case

import com.elifnuronder.movieapp.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return themeRepository.isDarkTheme
    }
}
