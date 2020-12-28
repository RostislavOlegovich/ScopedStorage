

package com.raywenderlich.android.lememeify.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(val id: Long,
                 val uri: Uri,
                 val path: String,
                 val name: String,
                 val size: String,
                 val mimeType: String,
                 val width: String?,
                 val height: String?,
                 val date: String,
                 val favorite: Boolean = false,
                 val trashed: Boolean = false) :
        Parcelable