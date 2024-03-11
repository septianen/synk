package com.sen.synk.ui.album

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sen.synk.R
import com.sen.synk.data.model.Album
import com.sen.synk.databinding.ItemAlbumBinding

class AlbumAdapter() : PagingDataAdapter<Album, AlbumAdapter.ViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        album?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
        fun bind(item: Album?) {
            if (item == null)
                return

            binding.tvTitle.text = "ID:${item.id} - ${item.title}"

            binding.tvUrl.text = item.url
            Linkify.addLinks(binding.tvUrl, Linkify.WEB_URLS)
            binding.tvUrl.movementMethod = LinkMovementMethod.getInstance()
            binding.tvUrl.setLinkTextColor(itemView.context.resources.getColor(com.septianen.iris.R.color.primary))

            Glide.with(itemView.context)
                .load(item.thumbnailUrl)
                .error(
                    itemView.context.getDrawable(com.septianen.iris.R.drawable.broken_image)
                )
                .circleCrop()
                .into(binding.ivThumbnail)

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAlbumBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
                oldItem == newItem
        }
    }
}