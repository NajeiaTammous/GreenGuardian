package com.greeners.greenguardian.presentation.feature.home

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import com.greeners.greenguardian.R
import com.greeners.greenguardian.presentation.designSystem.components.basic.Icon
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.LocalColors
import com.greeners.greenguardian.presentation.designSystem.theme.LocalTextStyle
import com.greeners.greenguardian.presentation.designSystem.theme.Theme
import com.greeners.greenguardian.presentation.feature.home.composables.CustomButton
import com.greeners.greenguardian.presentation.feature.home.composables.CustomSearchBar
import com.greeners.greenguardian.presentation.feature.home.composables.HomeAppBar
import com.greeners.greenguardian.presentation.feature.home.composables.PlantItem
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun HomeScreen(
    onSearchBarClicked: () -> Unit,
    onImageSelected: (imageUri: String) -> Unit,
    onCameraScanClicked: () -> Unit,
    onPlantClicked: (plantId: String) -> Unit,
    onSeeAllClicked: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            File(context.cacheDir, "temp_image.jpg")
        )
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            it?.let { it1 -> viewModel.onImageSelected(it1) }
        }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it) {
                viewModel.onImageSelected(photoUri)
            }
        }
    )




    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeUiEffect.OnClickPlantCard -> {
                    onPlantClicked(effect.plantId)
                }

                is HomeUiEffect.OnClickSearch -> {
                    onSearchBarClicked()
                }

                is HomeUiEffect.OnSelectedImage -> {
                    effect.imageUri?.let { onImageSelected(it.toString()) }
                }

                is HomeUiEffect.OnSelectedImageResource -> {
                    when (effect.imageType) {
                        ImageType.CAMERA -> {
                            onCameraScanClicked()
                        }

                        ImageType.GALLERY -> {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    }
                }

                is HomeUiEffect.OnClickSeeAll -> {
                    onSeeAllClicked()
                }
            }
        }

    }
    HomeContent(state = state, listener = viewModel)
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    listener: HomeInteractionListener
) {

    if (state.isScanDialogVisible) {
        Box(modifier = Modifier.fillMaxSize()) {
            ScanOptionDialog(
                onCameraSelected = {
                    listener.onClickToSelectImage(ImageType.CAMERA)
                },
                onGallerySelected = {
                    listener.onClickToSelectImage(ImageType.GALLERY)
                },
                onDismiss = listener::onScanDialogDismissed
            )

        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .background(LocalColors.current.background)
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HomeAppBar()
        CustomSearchBar(
            modifier = Modifier.clickable(
                onClick = listener::onClickSearch,
                interactionSource = interactionSource,
                indication = null
            ),
            hint = stringResource(R.string.search_hint),
            isEnabled = false,
            text = "",
            iconPainter = painterResource(R.drawable.search_normal)
        )
        Image(
            modifier = Modifier
                .aspectRatio(1.3f)
                .padding(horizontal = 16.dp),
            painter = painterResource(R.drawable.home_plant),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.welcome_message),
            color = LocalColors.current.contentSecondary,
            style = LocalTextStyle.current.bodySmall,
        )
        CustomButton(onClick = listener::onClickScan)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.common_plants_title),
                style = LocalTextStyle.current.titleSmall,
                color = LocalColors.current.contentPrimary,
            )
            Text(
                text = stringResource(R.string.see_all),
                color = LocalColors.current.primary,
                style = LocalTextStyle.current.bodyMedium,
                modifier = Modifier.clickable(
                    onClick = { listener.onClickSeeAll() },
                    interactionSource = interactionSource,
                    indication = null
                )
            )
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            items(state.plants, key = { it.id }) { plant ->
                PlantItem(
                    name = plant.name,
                    onClickItem = { listener.onClickPlantCard(plant.id) },
                    itemId = plant.id,
                    image = plant.imageResId
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScanOptionDialog(
    modifier: Modifier = Modifier,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        content = {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Choose what you re sutable",
                        style = Theme.typography.titleSmall,
                        color = Theme.color.contentPrimary,
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
                    )
                    Button(
                        onClick = onCameraSelected,
                        colors = ButtonDefaults.buttonColors(containerColor = Theme.color.primary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_camera),
                            contentDescription = "Camera",
                            tint = Color.White
                        )
                        Spacer(Modifier.width(14.dp))
                        Text(
                            "Camera Scan",
                            color = Color.White,
                            style = Theme.typography.bodyMedium
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onGallerySelected,
                        colors = ButtonDefaults.buttonColors(containerColor = Theme.color.divider),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_gallery),
                            contentDescription = "gallery",
                            tint = Theme.color.primary
                        )
                        Spacer(Modifier.width(14.dp))
                        Text(
                            "Gallery Scan ",
                            color = Theme.color.contentSecondary,
                            style = Theme.typography.bodyMedium
                        )
                    }
                }
            }
        })
}

private fun handleImageSelection(
    uri: Uri?,
    context: Context,
    onImageSelected: (ByteArray) -> Unit
) {
    val imageByteArrays =
        uri?.let {
            context.contentResolver.openInputStream(it)?.use { inputStream ->
                inputStream.readBytes()
            }
        }
    imageByteArrays?.let { onImageSelected(it) }
}
