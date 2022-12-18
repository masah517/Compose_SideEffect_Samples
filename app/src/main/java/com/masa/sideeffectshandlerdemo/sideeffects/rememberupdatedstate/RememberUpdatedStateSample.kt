package com.masa.sideeffectshandlerdemo.sideeffects.rememberupdatedstate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun RememberUpdatedStateSample() {

    var chosenOption by remember {
        mutableStateOf(Option.ROCK)
    }

    var gameStarted by remember {
        mutableStateOf(false)
    }

    Column {
        AnimatedVisibility(gameStarted) {
            GameScreen(option = chosenOption) { option ->
                chosenOption = option
            }
        }

        if (gameStarted.not()) {
            Button(onClick = { gameStarted = true }) {
                Text(text = "Start Game!")
            }
        }
    }
}

@Composable
fun GameScreen(option: Option, onOptionSelectedListener: (Option) -> Unit) {
    val currentOption by rememberUpdatedState(newValue = option)

    var result by remember {
        mutableStateOf<Boolean?>(null)
    }

    LaunchedEffect(Unit) {
        delay(5000L)
        if (currentOption === Option.PAPER) {
            result = true
        }else{
            result = false
        }
    }

    Column {
        result?.let {
            Text(text = if (it) "WIN" else "LOSE")
        }
        Row {
            Button(onClick = {
                onOptionSelectedListener(Option.ROCK)
            }) {
                Text(text = "Rock")
            }

            Button(onClick = {
                onOptionSelectedListener(Option.PAPER)
            }) {
                Text(text = "Paper")
            }

            Button(onClick = {
                onOptionSelectedListener(Option.SCISSORS)
            }) {
                Text(text = "Scissors")
            }
        }
    }
}

enum class Option {
    ROCK,
    PAPER,
    SCISSORS,
}
