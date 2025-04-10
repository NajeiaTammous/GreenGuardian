package com.greeners.greenguardian.presentation.extension

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlin.math.absoluteValue

@Composable
fun <E> E.Listen(onEffect: suspend (currentEffect: E) -> Unit) {
    println("effect: $this")
    LaunchedEffect(key1 = this) {
        onEffect(this@Listen)
    }
}
