package com.masa.sideeffectshandlerdemo.sideeffects.remembercoroutinescope

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RememberCoroutineScopeSample() {
    val scope = rememberCoroutineScope()
    var secretTextVisibility by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(visible = secretTextVisibility) {
            Text(text = "This is secret text")
        }

        Button(onClick = {
            // ここはComposableの外部であるため、coroutineScopeを起動するにはrememberCoroutineScopeを使用す必要がある
            scope.launch {
                secretTextVisibility = true
                // 3秒間のみ表示するため、ここで遅延する
                delay(3000L)
                secretTextVisibility = false
            }
        }) {
            Text(text = "Click to show secret text")
        }
    }
}
