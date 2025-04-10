package com.greeners.greenguardian.presentation.feature.home.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.R
import com.greeners.greenguardian.presentation.designSystem.components.basic.Icon
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalShapes
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: Painter = painterResource(R.drawable.vector),
    text: String = stringResource(R.string.diagnose_button_text),
) {
    Button(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
        onClick = onClick,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = LocalColors.current.primary,
            contentColor = LocalColors.current.onPrimary
        ),
        shape = LocalShapes.current.mediumRoundedCorners,
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(painter = icon, contentDescription = "")
            Text(
                text = text,
                color = LocalColors.current.onPrimary,
                style = LocalTextStyle.current.bodyMedium,
            )
        }
    }
}