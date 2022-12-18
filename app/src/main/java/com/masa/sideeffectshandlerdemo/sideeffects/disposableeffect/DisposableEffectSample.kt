package com.masa.sideeffectshandlerdemo.sideeffects.disposableeffect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun DisposableEffectSample(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onLifecycleResume: () -> Unit,
    onLifecycleStop: () -> Unit,
) {

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    onLifecycleResume()
                }

                Lifecycle.Event.ON_STOP -> {
                    onLifecycleStop()
                }

                else -> {}
            }
        }

        // ライフサイクルのObserverを登録する
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            // メモリーリークなどを避けるため, Observerを削除する
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }
}
