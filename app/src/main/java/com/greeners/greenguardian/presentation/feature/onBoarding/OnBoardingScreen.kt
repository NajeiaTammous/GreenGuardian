package com.greeners.greenguardian.presentation.feature.onBoarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.greeners.greenguardian.presentation.extension.Listen
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel =  koinViewModel(),
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

    OnBoardingContent(
        state = state,
        onBack = navigateBack,
    )

}


private fun onEffect(effect: OnBoardingUiEffect?, context: Context) {
    when (effect) {
        OnBoardingUiEffect.NavigateToMain -> Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        else -> {}
    }
}


@Composable
private fun OnBoardingContent(
    state: OnBoardingUiState,
    onBack: () -> Unit,
) {

    Scaffold { padding ->
        Box(modifier = Modifier.background(Color.Yellow).fillMaxSize().padding(padding))
    }
}