package com.greeners.greenguardian.presentation.feature.onBoarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.data.remote.static_data.OnBoardingPage
import com.greeners.greenguardian.data.remote.static_data.onboardingPages
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.GreenGuardianTypography
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors

@Composable
fun OnBoardingPageWithCircles(
    modifier: Modifier = Modifier,
    pageData: OnBoardingPage,
    pagerState: PagerState,
) {
    val colors = LocalColors.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = pageData.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(320.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            PageIndicator(
                pageCount = onboardingPages.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(bottom = 24.dp),
                activeDotWidth = 32.dp,
                animationDuration = 500
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = pageData.title,
                color = colors.contentPrimary,
                style = GreenGuardianTypography().headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pageData.description,
                color = colors.contentSecondary,
                style = GreenGuardianTypography().bodySmall,
                textAlign = TextAlign.Center
            )
        }
}