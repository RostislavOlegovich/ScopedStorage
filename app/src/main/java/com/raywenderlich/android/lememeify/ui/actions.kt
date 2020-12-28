
package com.raywenderlich.android.lememeify.ui

import android.content.IntentSender
import com.raywenderlich.android.lememeify.model.Media

sealed class MainAction {
  data class ImagesChanged(val images: List<Media>) : MainAction()
  data class VideosChanged(val videos: List<Media>) : MainAction()
  data class ScopedPermissionRequired(
      val intentSender: IntentSender,
      val forType: ModificationType
  ) : MainAction()
  data class TrashedChanged(val trashed: List<Media>) : MainAction()
  data class FavoriteChanged(val favorites: List<Media>) : MainAction()
  object StoragePermissionsRequested : MainAction()
}

sealed class ImageDetailAction {
  object ImageDeleted : ImageDetailAction()
  object ImageSaved : ImageDetailAction()
  object ImageUpdated : ImageDetailAction()

  data class ScopedPermissionRequired(
      val intentSender: IntentSender,
      val forType: ModificationType
  ) : ImageDetailAction()
}

enum class ModificationType {
  UPDATE,
  FAVORITE,
  DELETE,
  TRASH
}

