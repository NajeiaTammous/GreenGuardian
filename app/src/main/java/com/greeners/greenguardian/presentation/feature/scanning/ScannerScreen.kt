package com.greeners.greenguardian.presentation.feature.scanning

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.greeners.greenguardian.R
import com.greeners.greenguardian.presentation.designSystem.components.BackButton
import com.greeners.greenguardian.presentation.designSystem.components.basic.Text
import com.greeners.greenguardian.presentation.designSystem.theme.Theme
import com.greeners.greenguardian.presentation.feature.scanning.dialogs.PlantImageCaptureInstructionsDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScannerScreen(
    imageUri: String?,
    viewModel: ScannerViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onDiseaseReadMoreClick: (
        accessToken: String
    ) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        imageUri?.let {
            viewModel.updateImageUri(
                it.toUri()
            )
        }
    }
    ScannerScreenContent(
        state = state,
        listener = viewModel,
        onDiseaseReadMoreClick = onDiseaseReadMoreClick,
        onBackPressed = onBackPressed,
        galleryImageUri = imageUri
    )
}

@Composable
private fun ScannerScreenContent(
    state: ScannerUiState = ScannerUiState(),
    listener: ScannerListener,
    onDiseaseReadMoreClick: (
        accessToken: String
    ) -> Unit,
    onBackPressed: () -> Unit,
    galleryImageUri: String?,
) {
    val isInstructionsVisible =
        remember { mutableStateOf(galleryImageUri == null && state.imageUri == null) }
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            listener.onReceive(ScannerUiEffect.OnImageSavedWith(context))
        } else {
            listener.onReceive(ScannerUiEffect.OnImageSavingCanceled)
        }
    }
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                listener.onReceive(ScannerUiEffect.OnPermissionGrantedWith(context))
            } else {
                listener.onReceive(ScannerUiEffect.OnPermissionDenied)
            }
        }

    fun handleCloseClick() {
        if (state.imageUri == null) {
            onBackPressed.invoke()
        } else {
            if (galleryImageUri == null) {
                listener.onReceive(ScannerUiEffect.OnCloseButtonClicked)
            } else {
                onBackPressed.invoke()
            }
        }
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            snackBarHostState.showSnackbar(
                message = state.error,
                actionLabel = "Dismiss",
                duration = SnackbarDuration.Short
            )
            listener.onReceive(ScannerUiEffect.OnErrorShown)
            handleCloseClick()
        }
    }

    LaunchedEffect(key1 = state.tempCacheFileUrl) {
        state.tempCacheFileUrl?.let {
            takePictureLauncher.launch(it)
        }
    }

    Scaffold(
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {

                BackButton(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .align(Alignment.CenterStart),
                    onClick = { handleCloseClick() },
                    isIconClosable = state.imageUri != null
                )

//                Box(
//                    modifier = Modifier
//                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
//                        .border(
//                            width = 1.dp,
//                            color = Theme.color.divider,
//                            shape = RoundedCornerShape(8.dp)
//                        )
//                        .align(Alignment.CenterStart)
//                        .clip(shape = RoundedCornerShape(8.dp))
//                        .clickable {
//                            handleCloseClick()
//                        },
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Image(
//                        modifier = Modifier.padding(8.dp),
//                        imageVector = if (state.imageUri == null) Icons.AutoMirrored.Filled.ArrowBack else Icons.Filled.Close,
//                        contentDescription = null,
//                    )
//                }

                Text(
                    text = "Scan Your Plant",
                    style = Theme.typography.headlineMedium,
                    color = Theme.color.contentPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.isLoading.not()) {
                if (state.imageUri == null) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding())
                            .align(Alignment.Center)
                            .scale(1.25f)
                            .offset((-20).dp),
                        painter = painterResource(R.drawable.img),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                    Image(
                        painter = painterResource(R.drawable.scan_icon),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(state.imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding()),
                        contentScale = ContentScale.Crop,
                    )
                }

                state.imageUri.let { uri ->
                    OutlinedButton(
                        onClick = {
                            if (uri == null) {
                                val cameraPermission = checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                )
                                if (cameraPermission != PERMISSION_GRANTED) {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                } else {
                                    listener.onReceive(
                                        ScannerUiEffect.OnPermissionGrantedWith(
                                            context
                                        )
                                    )
                                }
                            } else {
                                listener.onReceive(ScannerUiEffect.ProcessPlantImage(context, uri))
                            }
                        },
                        border = BorderStroke(1.dp, Theme.color.contentTertiary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(paddingValues)
                            .align(Alignment.BottomCenter),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Text(
                            text = if (uri == null) "Capture the photo" else "Continue",
                            style = Theme.typography.titleSmall,
                            color = Theme.color.contentPrimary
                        )
                        if (uri != null) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Continue",
                                modifier = Modifier.padding(start = 8.dp),
                                tint = Theme.color.primary
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    state.disease != null && state.disease.name.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(paddingValues)
                        .align(Alignment.BottomCenter)
                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        ),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = state.disease?.name ?: "",
                                style = Theme.typography.headlineMedium,
                                color = Theme.color.contentPrimary,
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = Theme.typography.bodyMedium.toSpanStyle()
                                            .copy(color = Theme.color.contentPrimary)
                                    ) {
                                        append("Cause: ")
                                    }
                                    withStyle(
                                        style = Theme.typography.bodyMedium.toSpanStyle()
                                            .copy(color = Theme.color.contentSecondary)
                                    ) {
                                        append(state.disease?.name)
                                    }
                                }.text,
                            )
                            Row(
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        onDiseaseReadMoreClick(
                                            state.disease!!.accessToken
                                        )
                                    },
                            ) {
                                Text(
                                    text = "Read More",
                                    style = Theme.typography.titleSmall,
                                    color = Theme.color.contentPrimary,
                                    fontWeight = FontWeight.Medium,
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Theme.color.primary,
                                )
                            }
                        }

                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    color = Theme.color.primary,
                    strokeWidth = 4.dp
                )
            }
        }
        if (isInstructionsVisible.value) {
            PlantImageCaptureInstructionsDialog {
                isInstructionsVisible.value = false
            }
        }

    }
}

@Preview
@Composable
private fun ScannerScreenPreview() {
    ScannerScreenContent(
        listener = object : ScannerListener {

            override fun updateCapturedPhoto(uri: Uri) {
                TODO("Not yet implemented")
            }

            override fun onReceive(effect: ScannerUiEffect) {
                TODO("Not yet implemented")
            }
        },
        state = TODO(),
        onDiseaseReadMoreClick = TODO(),
        onBackPressed = TODO(),
        galleryImageUri = TODO()
    )
}