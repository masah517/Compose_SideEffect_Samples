package com.masa.sideeffectshandlerdemo.sideeffects.rememberupdatedstate

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun RememberUpdatedStateSample() {

    var chosenOption by remember {
        mutableStateOf(Option.ROCK)
    }

    var gameStarted by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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

    var timer by remember {
        mutableStateOf<Int>(5)
    }

    LaunchedEffect(Unit) {
        // この部分はcurrentOptionが更新されても再起動されない
        repeat(5) {
            delay(1000L)
            timer--
        }
        // パーに勝つ手を選んだ場合のみ勝利にする
        result = (currentOption === Option.PAPER)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        result?.let {
            Text(text = if (it) "WIN" else "LOSE")
        }

        Text(text = if (timer > 0) "残り時間 $timer" else "TIME UP!", textAlign = TextAlign.Center)
        Text(text = "あなたは今 ${option.name} を選択しています")

        Row {
            Button(
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = if (option === Option.ROCK) Color.Yellow else Color.Gray,
                    contentColor = Color.Black,
                ),
                onClick = {
                    onOptionSelectedListener(Option.ROCK)
                }) {
                Text(text = "Rock")
            }

            Button(
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = if (option === Option.PAPER) Color.Yellow else Color.Gray,
                    contentColor = Color.Black,
                ),
                onClick = {
                    onOptionSelectedListener(Option.PAPER)
                }) {
                Text(text = "Paper")
            }

            Button(
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = if (option === Option.SCISSORS) Color.Yellow else Color.Gray,
                    contentColor = Color.Black,
                ),
                onClick = {
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
