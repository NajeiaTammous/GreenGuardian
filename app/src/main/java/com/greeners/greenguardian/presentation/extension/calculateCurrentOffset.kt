package com.greeners.greenguardian.presentation.extension

import androidx.compose.foundation.pager.PagerState
import kotlin.math.absoluteValue


fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return ((page - currentPage) + currentPageOffsetFraction).absoluteValue
}