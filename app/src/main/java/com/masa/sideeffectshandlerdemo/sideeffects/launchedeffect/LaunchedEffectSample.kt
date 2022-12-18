package com.masa.sideeffectshandlerdemo.sideeffects.launchedeffect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.masa.sideeffectshandlerdemo.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun LaunchedEffectTimerSample() {
    var timer by remember {
        mutableStateOf(0L)
    }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(1000L)
            timer++
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Time count : $timer")
    }
}

@Composable
fun LaunchedEffectSnackBarSample(
    viewModel: MainViewModel = hiltViewModel()
) {
    var snackBarVisible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.sharedFlow.collect { event ->
            when (event) {
                is MainViewModel.MainEvents.ShowSnackBar -> {
                    snackBarVisible = true
                    delay(3000L)
                    snackBarVisible = false
                }
            }
        }
    }

    Column {
        Button(onClick = { viewModel.emitEvent(MainViewModel.MainEvents.ShowSnackBar("test")) }) {

        }
        if (snackBarVisible) {
            Snackbar {
                Text(text = "Hello")
            }
        }
    }
}
