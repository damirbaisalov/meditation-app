package com.example.meditation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation.main_window.MY_APP_USER_ACTIVITY
import com.example.meditation.main_window.USER_DARK_MODE
import com.example.meditation.models.Database
import com.example.meditation.models.MeditationVideoData
import com.example.meditation.view.MeditationVideoAdapter
import com.example.meditation.view.MeditationVideoClickListener

class FirstFragment : Fragment() {

    private lateinit var rootView: View

    private lateinit var firstFragmentParentLayout: FrameLayout

    private lateinit var recyclerView: RecyclerView
    private val meditationVideoAdapter = MeditationVideoAdapter(getMeditationVideoClickListener())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_first, container, false)
        // Inflate the layout for this fragment

        init()

        return rootView
    }

    private fun init() {

        firstFragmentParentLayout = rootView.findViewById(R.id.first_fragment_parent_layout)
        if (getUserDarkModeSharedPreferences())
            firstFragmentParentLayout.setBackgroundResource(R.drawable.bg_dark)
        else
            firstFragmentParentLayout.setBackgroundResource(R.drawable.bg_gradient)

        recyclerView = rootView.findViewById(R.id.first_fragment_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(
            rootView.context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = meditationVideoAdapter
        meditationVideoAdapter.setList(Database.meditationVideoDatabase)
    }

    private fun getMeditationVideoClickListener(): MeditationVideoClickListener {
        return object: MeditationVideoClickListener {
            override fun onMeditationVideoClick(id: String) {
                openVideoYoutube(id)
            }
        }
    }

    private fun openVideoYoutube(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$id"))

        try {
            startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            startActivity(webIntent)
        }

    }

    private fun getUserDarkModeSharedPreferences(): Boolean {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getBoolean(USER_DARK_MODE, false)
    }
}