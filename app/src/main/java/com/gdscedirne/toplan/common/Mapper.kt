package com.gdscedirne.toplan.common

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.gdscedirne.toplan.data.model.Feed
import com.gdscedirne.toplan.data.model.Marker
import java.io.InputStream

fun Uri.uriToBitmap(contentResolver: ContentResolver): Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(this)
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}

fun Marker.toFeed() : Feed {
    return Feed(
        id = id,
        title = title,
        imageUrl = imageUrl,
        description = description
    )
}