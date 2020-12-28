
package com.raywenderlich.android.lememeify.ui.images

import androidx.recyclerview.selection.ItemKeyProvider

class ImageKeyProvider(private val adapter: MediaAdapter) : ItemKeyProvider<String>(SCOPE_CACHED) {

  override fun getKey(position: Int): String? =
      "${adapter.currentList[position].id}"

  override fun getPosition(key: String): Int =
      adapter.currentList.indexOfFirst { "${it.id}" == key }
}