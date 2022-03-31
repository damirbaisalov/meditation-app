package com.example.meditation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.meditation.main_window.MY_APP_USER_ACTIVITY
import com.example.meditation.main_window.USER_DARK_MODE
import com.example.meditation.viewmodel.SecondViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View

    private lateinit var secondFragmentParentLayout: FrameLayout

    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resumeButton: Button
    private lateinit var resetButton: Button

    private lateinit var timeTextView: TextView
    private lateinit var timeBriefTextView: TextView
    private lateinit var greetingsLinearLayout: LinearLayout

    private lateinit var vm: SecondViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_second, container, false)
        // Inflate the layout for this fragment

        init()

        vm = ViewModelProvider(requireActivity()).get(SecondViewModel::class.java)

        vm.getResultTimeLive().observe(requireActivity(), {
            timeTextView.text = it
        })

        vm.getResultTimeBriefLive().observe(requireActivity(), {
            timeBriefTextView.text = it
        })

        vm.getResultStartButtonStateLive().observe(requireActivity(), {
            startButton.isVisible = it
        })

        vm.getResultStopButtonStateLive().observe(requireActivity(), {
            stopButton.isVisible = it
        })

        vm.getResultResumeButtonStateLive().observe(requireActivity(), {
            resumeButton.isVisible = it
        })

        vm.getResultResetButtonStateLive().observe(requireActivity(), {
            resetButton.isVisible = it
        })

        vm.getResultGreetingStateLive().observe(requireActivity(), {
            greetingsLinearLayout.isVisible = it
        })

        return rootView
    }

    private fun init() {

        secondFragmentParentLayout = rootView.findViewById(R.id.second_fragment_parent_layout)
        if (getUserDarkModeSharedPreferences())
            secondFragmentParentLayout.setBackgroundResource(R.drawable.bg_dark)
        else
            secondFragmentParentLayout.setBackgroundResource(R.drawable.bg_gradient)

        timeTextView = rootView.findViewById(R.id.fragment_second_timer_text_view)
        timeBriefTextView = rootView.findViewById(R.id.fragment_second_timer_brief_text_view)
        greetingsLinearLayout = rootView.findViewById(R.id.fragment_second_greetings_linear_layout)

        startButton = rootView.findViewById(R.id.fragment_second_start_button)
        stopButton = rootView.findViewById(R.id.fragment_second_pause_button)
        resumeButton = rootView.findViewById(R.id.fragment_second_resume_button)
        resetButton = rootView.findViewById(R.id.fragment_second_reset_button)

        startButton.setOnClickListener {
            startButtonClicked()
            vm.startStopTimer()
        }

        stopButton.setOnClickListener {
            stopButtonClicked()
            vm.startStopTimer()
        }

        resumeButton.setOnClickListener {
            resumeButtonClicked()
            vm.startStopTimer()
        }

        resetButton.setOnClickListener {
            resetButtonClicked()
            vm.resetTimer()
        }

    }

    private fun startButtonClicked() {
        vm.resetResultStartButtonStateLive()
    }

    private fun stopButtonClicked() {
        vm.resetResultStopButtonStateLive()
    }

    private fun resumeButtonClicked() {
        vm.resetResultResumeButtonStateLive()
    }

    private fun resetButtonClicked() {
        vm.resetResultResetButtonStateLive()
    }

    private fun getUserDarkModeSharedPreferences(): Boolean {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getBoolean(USER_DARK_MODE, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}