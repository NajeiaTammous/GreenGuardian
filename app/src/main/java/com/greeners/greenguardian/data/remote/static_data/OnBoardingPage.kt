package com.greeners.greenguardian.data.remote.static_data

import androidx.annotation.DrawableRes
import com.greeners.greenguardian.R

data class OnBoardingPage(
    val title: String,
    val description: String,
    @DrawableRes val imageRes: Int
)

val onboardingPages = listOf(
    OnBoardingPage(
        title = "Instant Disease Diagnosis",
        description = "Snap a photo to identify your plant's issues and receive expert care solutions.",
        imageRes = R.drawable.on_boarding_2
    ),
    OnBoardingPage(
        title = "Discover Palestine's Plants",
        description = "Explore the rich biodiversity of Palestine, from desert flora to vibrant wildflowers.",
        imageRes = R.drawable.onboarding_1
    ),
    OnBoardingPage(
        title = "Plant Care & Disease Prevention",
        description = "Learn how to care for your plants, prevent diseases, and keep them healthy with easy tips and tricks.",
        imageRes = R.drawable.onboarding_12
    )
)