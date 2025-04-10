package com.greeners.greenguardian.presentation.feature.onBoarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.greeners.greenguardian.data.remote.static_data.onboardingPages
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.extension.Listen
import com.greeners.greenguardian.presentation.extension.calculateCurrentOffsetForPage
import com.greeners.greenguardian.presentation.feature.onBoarding.components.OnBoardingBackGround
import com.greeners.greenguardian.presentation.feature.onBoarding.components.OnBoardingPageWithCircles
import com.greeners.greenguardian.presentation.feature.onBoarding.components.OnBoardingSingleButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnimatedOnBoardingContent(
    state: OnBoardingUiState,
    onNext: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) { onboardingPages.size }
    val scope = rememberCoroutineScope()

    Box {
        OnBoardingBackGround(
            modifier = Modifier
                .fillMaxSize().background(LocalColors.current.background)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                pageSpacing = 16.dp
            ) { page ->
                val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
                val scale = lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                val alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )

                OnBoardingPageWithCircles(
                    pageData = onboardingPages[page],
                    pagerState = pagerState,
                    modifier = Modifier.graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                )
            }
            Spacer(modifier = Modifier.height(120.dp))
            OnBoardingSingleButton(
                currentPage = pagerState.currentPage,
                totalPages = onboardingPages.size,
                onNextOrSkip = {
                    if (pagerState.currentPage < onboardingPages.lastIndex) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onNext()
                    }
                }
            )
        }
    }
}

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = koinViewModel(),
    onNavigateTo: (OnBoardingUiEffect) -> Unit,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)
    val context = LocalContext.current

    effect.Listen { effects ->
        onEffect(
            effect = effects,
            context = context
        )
    }

    AnimatedOnBoardingContent(
        state = state,
        onNext = { onNavigateTo.invoke(OnBoardingUiEffect.NavigateToMain) }
    )
}

private fun onEffect(effect: OnBoardingUiEffect?, context: Context) {
    when (effect) {
        OnBoardingUiEffect.NavigateToMain -> {
            Toast.makeText(context, "Navigate to Home", Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }
}