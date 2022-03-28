package com.example.meditation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation.R
import com.example.meditation.models.MeditationVideoData

class MeditationVideoAdapter(
    private val meditationVideoClickListener: MeditationVideoClickListener
): RecyclerView.Adapter<MeditationVideoViewHolder>() {

    private var dataList: MutableList<MeditationVideoData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeditationVideoViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.meditation_video_item, parent, false)

        return MeditationVideoViewHolder(rootView, meditationVideoClickListener)
    }

    override fun onBindViewHolder(holder: MeditationVideoViewHolder, position: Int) {
        holder.onBind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun setList(meditationVideoData: List<MeditationVideoData>) {
        dataList.clear()
        dataList.addAll(meditationVideoData)
        notifyDataSetChanged()
    }

    fun clearAll() {
        (dataList as? ArrayList<MeditationVideoData>)?.clear()
        notifyDataSetChanged()
    }
}