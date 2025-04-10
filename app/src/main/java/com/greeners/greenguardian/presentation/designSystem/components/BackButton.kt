package com.greeners.greenguardian.presentation.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.R
import com.greeners.greenguardian.presentation.designSystem.components.basic.Icon
import com.greeners.greenguardian.presentation.designSystem.theme.Theme

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isIconClosable: Boolean = false,
) {
    Icon(
        painter = painterResource(
            if (isIconClosable) R.drawable.close_icon else
                R.drawable.ic_arrow_left
        ),
        contentDescription = "back",
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(Theme.color.onPrimary)
            .border(
                width = 1.dp,
                color = Theme.color.divider,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp),
    )
}