

package com.raywenderlich.android.lememeify.ui.images

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.raywenderlich.android.lememeify.R
import com.raywenderlich.android.lememeify.model.Media
import kotlinx.android.synthetic.main.item_image.view.*

class MediaAdapter(val clickAction: (Media) -> Unit) :
    ListAdapter<Media, MediaAdapter.MainViewHolder>(DiffCallback()) {

  var tracker: SelectionTracker<String>? = null

  init {
    setHasStableIds(true)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return MainViewHolder(inflater.inflate(R.layout.item_image, parent, false))
  }

  override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    val imageToBind = getItem(position)

    tracker?.let {
      Glide.with(holder.image.context)
          .load(imageToBind.uri)
          .signature(ObjectKey(imageToBind.date))
          .into(holder.image)

      if(it.isSelected("${imageToBind.id}")) {
        holder.image.setColorFilter(
            ContextCompat.getColor(holder.image.context, R.color.color65TransparentPrimary),
            PorterDuff.Mode.SRC_OVER)
      } else {
        holder.image.clearColorFilter()
      }

      holder.image.setOnClickListener {
        clickAction(imageToBind)
      }
    }
  }

  override fun getItemId(position: Int): Long {
    return currentList[position].id
  }

  private class DiffCallback : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
      return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
      return oldItem == newItem
    }
  }

  inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image = itemView.iv_image!!

    fun getImageDetails(): ItemDetailsLookup.ItemDetails<String> =

        object : ItemDetailsLookup.ItemDetails<String>() {

          override fun getPosition(): Int = adapterPosition

          override fun getSelectionKey(): String? = "${getItem(adapterPosition).id}"
        }
  }
}