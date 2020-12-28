

package com.raywenderlich.android.lememeify

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.os.BuildCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.raywenderlich.android.lememeify.model.Media
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val EXTRA_IMAGE = "extra.image"
const val MIME_TYPE_IMAGE_REGEX = "image/[-\\w.]+\$"

private const val DATA_PATTERN = "MMM d, yyyy  •  HH:mm"
private const val MEGABYTE = 1000.0

private const val IMAGE_EXTENSION_JPG = "jpg"
private const val IMAGE_EXTENSION_PNG = "png"

fun hasSdkHigherThan(sdk: Int): Boolean {
    //Early previous of R will return Build.VERSION.SDK_INT as 29
    if (Build.VERSION_CODES.R == sdk) {
        return BuildCompat.isAtLeastR()
    }
    return Build.VERSION.SDK_INT > sdk
}

fun hasStoragePermission(context: Context): Boolean {
    return hasPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
}

fun requestStoragePermission(fragment: Fragment, requestCode: Int) {
    fragment.requestPermissions(
            arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
            requestCode)
}

fun hasMediaLocationPermission(context: Context): Boolean {
    return hasPermission(context, Manifest.permission.ACCESS_MEDIA_LOCATION)
}

fun requestMediaLocationPermission(activity: Activity, requestCode: Int) {
    requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_MEDIA_LOCATION), requestCode)
}

private fun hasPermission(context: Context, permission: String): Boolean {
    return ActivityCompat.checkSelfPermission(context,
            permission) == PackageManager.PERMISSION_GRANTED
}

private fun requestPermissions(activity: Activity, list: Array<String>, code: Int) {
    ActivityCompat.requestPermissions(activity, list, code)
}

fun navigateToMain(view: View) {
    view.findNavController().navigateUp()
}

fun navigateToDetails(view: View, image: Media) {
    val bundle = bundleOf(EXTRA_IMAGE to image)
    view.findNavController().navigate(R.id.actionDetails, bundle)
}

fun getFormattedDateFromMillis(millis: Long): String {
    val date = Date()
    date.time = millis * 1000L
    return SimpleDateFormat(DATA_PATTERN, Locale.getDefault()).format(date)
}

fun getFormattedKbFromBytes(context: Context, bytes: Long): String {
    return context.getString(R.string.image_kb, (bytes / MEGABYTE).roundToInt())
}

fun getImageFormat(type: String): Bitmap.CompressFormat {
    return when (type) {
      Bitmap.CompressFormat.PNG.name -> {
        Bitmap.CompressFormat.PNG
      }
      Bitmap.CompressFormat.JPEG.name -> {
        Bitmap.CompressFormat.JPEG
      }
      Bitmap.CompressFormat.WEBP.name -> {
        Bitmap.CompressFormat.WEBP
      }
        else -> {
            Bitmap.CompressFormat.JPEG
        }
    }
}

fun getImageExtension(format: Bitmap.CompressFormat): String {
    return when (format) {
      Bitmap.CompressFormat.PNG -> IMAGE_EXTENSION_PNG
      Bitmap.CompressFormat.JPEG -> IMAGE_EXTENSION_JPG
        else -> IMAGE_EXTENSION_JPG
    }
}