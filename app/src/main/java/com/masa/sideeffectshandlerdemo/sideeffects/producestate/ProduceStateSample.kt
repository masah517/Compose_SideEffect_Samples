package com.masa.sideeffectshandlerdemo.sideeffects.producestate

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import com.masa.sideeffectshandlerdemo.viewmodel.MainViewModel
import com.masa.sideeffectshandlerdemo.viewmodel.MainViewModel.Media

@Composable
fun ProduceStateSample(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val fetchImageState = fetchNetworkImage { url, type ->
        viewModel.fetchMedia(url, type)
    }

    fetchImageState.value?.let { result ->
        if (result.isSuccess) {

        } else {

        }
    }
}

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun fetchNetworkImage(
    fetchMedia: suspend (String?, String?) -> Media?,
): State<Result<Media>?> {
    return produceState(initialValue = null) {
        val image: Media? = fetchMedia("anyUrl", "anyType")

        image?.let {
            Result.success(image)
        } ?: Result.failure(Exception())
    }
}
