package com.masa.sideeffectshandlerdemo.sideeffects.derivedstateof

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun DerivedStateOfSample(
    itemCount: Int = 50,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(itemCount) { index ->
                Text(text = "Item: $index")
            }
        }

        val hasReachedListEnd by remember(listState.firstVisibleItemIndex) {
            mutableStateOf(
                listState.firstVisibleItemIndex > 0
            )
        }

        Column {
            AnimatedVisibility(hasReachedListEnd) {
                Button(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(70.dp),
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }) {
                    Text("Back to top")
                }
            }
        }
    }
}
