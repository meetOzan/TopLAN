package com.gdscedirne.toplan.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.gdscedirne.toplan.R

@Composable
fun CoilImage(
    data: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {

    val painter = rememberAsyncImagePainter(data)

    Image(
        painter = painter,
        contentDescription = stringResource(R.string.coil_image),
        contentScale = contentScale,
        modifier = modifier
    )
}