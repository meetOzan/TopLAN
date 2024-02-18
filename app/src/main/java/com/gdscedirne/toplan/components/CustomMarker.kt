package com.gdscedirne.toplan.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.ui.theme.DarkWhite
import com.gdscedirne.toplan.ui.theme.MediumGrey20
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun MapMarker(
    position: LatLng,
    iconResourceId: Int,
    marker: Marker,
    markerColor: androidx.compose.ui.graphics.Color
) {

    var isClicked by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    MarkerInfoWindowContent(
        state = MarkerState(position = position),
        title = marker.title,
        icon = bitmapDescriptorFromVector(
            context,
            R.drawable.custom_marker,
            iconResourceId,
            markerColor,
            isClicked
        ),
        onClick = {
            isClicked = !isClicked
            false
        }
    ) {
        if (isClicked) {
            MarkerWindow(
                markerIcon = iconResourceId,
                title = marker.title,
                description = marker.description,
                reportedTime = marker.time,
                reportedDate = marker.date,
                type = marker.type,
                location = marker.location

            )
        } else {
            it.hideInfoWindow()
        }
    }
}

fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int,
    vectorResIdIcon: Int,
    markerColor: androidx.compose.ui.graphics.Color,
    isSelected: Boolean = false
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    val drawableIcon = ContextCompat.getDrawable(context, vectorResIdIcon) ?: return null

    val width = max(drawable.intrinsicWidth, drawableIcon.intrinsicWidth)
    val height = max(drawable.intrinsicHeight, drawableIcon.intrinsicHeight)

    val combinedBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(combinedBm)

    val colorFilter = LightingColorFilter(
        if (isSelected) markerColor.toArgb() else MediumGrey20.toArgb(),
        1.0f.roundToInt()
    )

    val colorFilterIcon = LightingColorFilter(
        DarkWhite.toArgb(),
        1.0f.roundToInt()
    )

    drawable.colorFilter = colorFilter
    drawableIcon.colorFilter = colorFilterIcon

    drawable.setBounds(0, 0, width, height)
    drawable.draw(canvas)
    drawableIcon.setBounds(
        (width - drawableIcon.intrinsicWidth) / 2,
        (height - drawableIcon.intrinsicHeight) / 2 - 20,
        (width + drawableIcon.intrinsicWidth) / 2,
        (height + drawableIcon.intrinsicHeight) / 2 - 20
    )
    drawableIcon.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(combinedBm)
}