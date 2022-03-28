package com.example.meditation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meditation.R
import com.example.meditation.models.MeditationVideoData

class MeditationVideoViewHolder(
    itemView: View,
    private val meditationVideoClickListener: MeditationVideoClickListener
): RecyclerView.ViewHolder(itemView) {

    private val meditationVideoImageView: ImageView = itemView.findViewById(R.id.meditation_view_item_image_view)
    private val meditationTitleTextView: TextView = itemView.findViewById(R.id.meditation_view_item_title_text_view)

    fun onBind(meditationVideoData: MeditationVideoData) {

        Glide
            .with(itemView.context)
            .load(meditationVideoData.image)
            .centerCrop()
            .into(meditationVideoImageView)

        meditationTitleTextView.text = meditationVideoData.title

        meditationVideoImageView.setOnClickListener {
            meditationVideoClickListener.onMeditationVideoClick(meditationVideoData.id)
        }
    }
}
