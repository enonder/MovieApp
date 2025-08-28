package com.elifnuronder.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elifnuronder.movieapp.domain.use_case.GetThemeUseCase
import com.elifnuronder.movieapp.domain.use_case.SetThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : ViewModel() {
    
    val isDarkTheme: StateFlow<Boolean> = getThemeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false // Default to light theme
        )
    
    fun toggleTheme() {
        viewModelScope.launch {
            setThemeUseCase(!isDarkTheme.value)
        }
    }
    
    fun setTheme(isDark: Boolean) {
        viewModelScope.launch {
            setThemeUseCase(isDark)
        }
    }
}
