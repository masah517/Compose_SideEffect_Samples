package com.masa.sideeffectshandlerdemo.sideeffects.sideeffect

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SideEffectSample() {
    var compositionCount by remember { mutableStateOf(0) }

    SideEffect {
        Log.d("ComposeInfo","Get recomposed $compositionCount times")
    }

    Box {
        Column(modifier = Modifier.align(Alignment.Center),) {
            Text(text = "Get recomposed $compositionCount times")
            
            Button(onClick = { compositionCount++ }) {
                Text(text = "Click to recompose")
            }
        }
    }
}
