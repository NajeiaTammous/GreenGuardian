package com.greeners.greenguardian.presentation.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun <E> E.Listen(onEffect: suspend (currentEffect: E) -> Unit) {
    println("effect: $this")
    LaunchedEffect(key1 = this) {
        onEffect(this@Listen)
    }
}
