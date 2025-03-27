package com.greeners.greenguardian.presentation.feature.onBoarding

sealed class OnBoardingUiEffect {
    data object NavigateToMain : OnBoardingUiEffect()
}