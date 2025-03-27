package com.greeners.greenguardian.presentation.designSystem.language

import androidx.compose.ui.unit.LayoutDirection

enum class LanguageCode(val languageCode:String) {
    EN("en"),
    AR("ar"),
}


object LocalizationManager {
    fun getLayoutDirection(languageCode: String): LayoutDirection {
        return when (languageCode) {
            LanguageCode.EN.languageCode -> LayoutDirection.Ltr
            else -> LayoutDirection.Rtl
        }
    }

}