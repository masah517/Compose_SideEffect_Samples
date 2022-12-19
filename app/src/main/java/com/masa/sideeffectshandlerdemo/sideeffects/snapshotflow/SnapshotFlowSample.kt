package com.masa.sideeffectshandlerdemo.sideeffects.snapshotflow

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun SnapshotFlowSample(
    itemCount: Int = 50,
    targetIndex: Int = 35,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val listState = rememberLazyListState()

        LaunchedEffect(Unit) {
            snapshotFlow {
                listState.currentVisibleAreaContainsItem(targetIndex = targetIndex)
            }.distinctUntilChanged()
                .collect { isTargetWithinTheScreen ->
                    if (isTargetWithinTheScreen) {
                        Toast.makeText(context, "$targetIndex 番目のカードが画面内に入りました", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(5.dp),
                    backgroundColor = Color.Gray,
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 15.dp),
                        text = "Item: $index",
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

private fun LazyListState.currentVisibleAreaContainsItem(
    targetIndex: Int,
): Boolean {

    // ターゲットのカードが画面内に少しでも収まっているかを判定するロジック
    return layoutInfo.visibleItemsInfo.map {
        it.index
    }.contains(targetIndex)
}
