package com.greeners.greenguardian.presentation.feature.home.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.components.basic.Icon
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalShapes
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle

@Composable
fun CustomSearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    iconPainter: Painter,
    text: String,
    isEnabled: Boolean = true,
    oneLineOnly: Boolean = false
) {
    TextField(
        singleLine = oneLineOnly,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = text,
        textStyle = LocalTextStyle.current.bodyMedium,
        onValueChange = onValueChange,
        label = {
            Text(
                text = hint,
                color = LocalColors.current.contentTertiary,
                style = LocalTextStyle.current.bodySmall,
            )
        },
        shape = LocalShapes.current.mediumRoundedCorners,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedTextColor = LocalColors.current.contentPrimary,
            unfocusedTextColor = LocalColors.current.contentSecondary,
            focusedContainerColor = LocalColors.current.onPrimary,
            unfocusedContainerColor = LocalColors.current.onPrimary,
            focusedIndicatorColor = LocalColors.current.primary,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = LocalColors.current.onPrimary,
            disabledIndicatorColor = Color.Transparent,
        ),
        leadingIcon = {
            Icon(
                painter = iconPainter,
                contentDescription = "TextFieldIcon",
                tint = LocalColors.current.contentTertiary
            )
        },
        enabled = isEnabled
    )
}