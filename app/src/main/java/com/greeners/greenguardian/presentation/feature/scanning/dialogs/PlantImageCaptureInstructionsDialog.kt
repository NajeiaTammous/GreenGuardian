package com.greeners.greenguardian.presentation.feature.scanning.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.Theme

@Composable
fun PlantImageCaptureInstructionsDialog(onComplete: () -> Unit) {

    val steps = listOf(
        InstructionModel(
            instructions = "Make sure your plant is in foucas and well- lit. Avoid using LED grow lights."
        ),
        InstructionModel(
            instructions = "Take A picture of one plant at a time "
        ),
        InstructionModel(
            instructions = "if your plant has flowers, focus on them"
        ),
        InstructionModel(
            instructions = "Keep Containers and other objects out of the frame"
        ),
    )

    var selectedStepIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .background(
                color = Color.Black.copy(alpha = .7f)
            )
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(
                    all = 20.dp
                ),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                steps[selectedStepIndex].images.forEach { imageResource ->
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .weight(1f),
                        painter = painterResource(imageResource),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
            Text(
                text = steps[selectedStepIndex].instructions,
                textAlign = TextAlign.Center,
                style = Theme.typography.headlineMedium,
                color = Theme.color.contentPrimary,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                steps.forEachIndexed { index, step ->

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                color = if (index == selectedStepIndex) Theme.color.primary else Theme.color.contentTertiary,
                                shape = CircleShape
                            )
                    )
                }
            }

            Button(
                onClick = {
                    if (selectedStepIndex < steps.size - 1) {
                        selectedStepIndex++
                    } else {
                        onComplete()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Theme.color.primary,
                ),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = if (selectedStepIndex < steps.size - 1) "Next" else "Take a Photo",
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantImageCaptureInstructionsDialog() {
    PlantImageCaptureInstructionsDialog({})
}