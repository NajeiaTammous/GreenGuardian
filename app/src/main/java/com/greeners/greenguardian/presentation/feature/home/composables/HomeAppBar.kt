package com.greeners.greenguardian.presentation.feature.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.R
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle

@Composable
fun HomeAppBar(){
    Row (
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Image(
            modifier = Modifier.size(44.dp),
            painter = painterResource(R.drawable.ellipse),
            contentDescription = ""
        )
        Column (
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ){

            Text(
                text = stringResource(R.string.hi_plant_lover),
                color = LocalColors.current.primary,
                style = LocalTextStyle.current.bodyLarge,
            )
            Text(
                text = stringResource(R.string.good_morning),
                color = LocalColors.current.contentSecondary,
                style = LocalTextStyle.current.bodySmall,
            )
        }
    }
}