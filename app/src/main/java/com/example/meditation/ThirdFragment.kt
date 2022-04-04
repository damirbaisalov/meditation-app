package com.example.meditation

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.meditation.dialog_fragments.DialogFragmentNameSurname
import com.example.meditation.main_window.*
import kotlinx.android.synthetic.main.activity_main_window.*
import java.util.*

class ThirdFragment : Fragment(), AdapterView.OnItemSelectedListener, DialogFragmentNameSurname.OnInputSelected {

    private lateinit var rootView: View

    private lateinit var thirdFragmentParentLayout: FrameLayout
    private lateinit var thirdFragmentStatisticsLayout: LinearLayout
    private lateinit var thirdFragmentBottomLayout: LinearLayout
    private lateinit var thirdFragmentNumTitleTextView: TextView
    private lateinit var thirdFragmentMinTitleTextView: TextView
    private lateinit var thirdFragmentDayTitleTextView: TextView
    private lateinit var thirdFragmentDarkModeTextView: TextView
    private lateinit var thirdFragmentLanguageTextView: TextView



    private lateinit var userNameSurnameTextView: TextView
    private lateinit var userNameSurnameEditImageView: ImageView

    private lateinit var userMeditationNumTextView: TextView
    private lateinit var userMeditationMinutesTextView: TextView
    private lateinit var userMeditationDaysTextView: TextView

    private lateinit var switch: Switch
    private lateinit var spinner: Spinner
    private lateinit var locale: Locale

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_third, container, false)
        // Inflate the layout for this fragment

//        Toast.makeText(rootView.context, "third fragment", Toast.LENGTH_SHORT).show()

        init()

        return rootView
    }

    private fun init() {

        thirdFragmentParentLayout = rootView.findViewById(R.id.third_fragment_parent_layout)
        thirdFragmentStatisticsLayout = rootView.findViewById(R.id.fragment_third_statistics_layout)
        thirdFragmentBottomLayout = rootView.findViewById(R.id.fragment_third_bottom_layout)
        thirdFragmentNumTitleTextView = rootView.findViewById(R.id.fragment_third_num_title_text)
        thirdFragmentMinTitleTextView = rootView.findViewById(R.id.fragment_third_minutes_title_text)
        thirdFragmentDayTitleTextView = rootView.findViewById(R.id.fragment_third_day_title_text)
        thirdFragmentDarkModeTextView = rootView.findViewById(R.id.fragment_third_dark_theme_title)
        thirdFragmentLanguageTextView = rootView.findViewById(R.id.fragment_third_language_title)

        userNameSurnameTextView = rootView.findViewById(R.id.third_fragment_name_surname)
        if (getUserNameSurnameSharedPreferences()=="default") {
            userNameSurnameTextView.text = resources.getString(R.string.third_fragment_name_surname_text)
        } else {
            userNameSurnameTextView.text = getUserNameSurnameSharedPreferences()
        }

        userNameSurnameEditImageView = rootView.findViewById(R.id.third_fragment_edit_image_view)
        userNameSurnameEditImageView.setOnClickListener {
            val dialog = DialogFragmentNameSurname()
            dialog.show(childFragmentManager, "dialog_fragment_name_surname")
        }

        userMeditationNumTextView = rootView.findViewById(R.id.user_meditation_num_text_view)
        userMeditationNumTextView.text = getUserMeditationNumSharedPreferences()
        userMeditationMinutesTextView = rootView.findViewById(R.id.user_meditation_minutes_text_view)
        userMeditationMinutesTextView.text = getUserMeditationMinutesSharedPreferences()
        userMeditationDaysTextView = rootView.findViewById(R.id.user_meditation_day_text_view)
        userMeditationDaysTextView.text = getUserMeditationDaysSharedPreferences()

        handleDarkModeViews(getUserDarkModeSharedPreferences())

        switch = rootView.findViewById(R.id.third_fragment_switch)
        switch.isChecked = getUserDarkModeSharedPreferences()
        switch.setOnCheckedChangeListener { _, isChecked ->
            saveUserDarkMode(isChecked)
            handleDarkModeViews(isChecked)
        }

        spinner = rootView.findViewById(R.id.third_fragment_spinner)
        ArrayAdapter.createFromResource(
            rootView.context,
            R.array.Languages,
            R.layout.spinner_item_selected
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.my_drop_down_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.setSelection(getLanguageSharedPreferences(), true)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        saveUserLanguage(position)
        when(position) {
            0 -> setLocale("en-us")
            1 -> setLocale("kk")
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun sendInputNameSurname(input: String) {
        userNameSurnameTextView.text = input
        saveUserNameSurname(input)
    }

    private fun setLocale(languageName: String) {
        locale = Locale(languageName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)

        if (getUserNameSurnameSharedPreferences()=="default")
            userNameSurnameTextView.text = res.getString(R.string.third_fragment_name_surname_text)

        thirdFragmentNumTitleTextView.text= res.getString(R.string.third_fragment_meditation_num_text)
        thirdFragmentMinTitleTextView.text= res.getString(R.string.third_fragment_meditation_minutes_text)
        thirdFragmentDayTitleTextView.text= res.getString(R.string.third_fragment_meditation_days_text)
        thirdFragmentDarkModeTextView.text= res.getString(R.string.third_fragment_dark_mode_text)
        thirdFragmentLanguageTextView.text= res.getString(R.string.third_fragment_language_change_text)
        requireActivity().bottomNavigationView.menu.getItem(0).title = res.getString(R.string.menu_learning_text)
        requireActivity().bottomNavigationView.menu.getItem(1).title = res.getString(R.string.menu_start_text)
        requireActivity().bottomNavigationView.menu.getItem(2).title = res.getString(R.string.menu_profile_text)

//        startActivity(requireActivity().intent)
//        requireActivity().finish()
//        requireActivity().overridePendingTransition(0, 0)
    }

    private fun handleDarkModeViews(isChecked: Boolean) {
        if (isChecked) {
            thirdFragmentParentLayout.setBackgroundResource(R.drawable.bg_dark)
            changeStatusBarMode(android.R.color.background_dark)
            requireActivity().bottomNavigationView.setBackgroundResource(R.color.bottom_nav_view_dark)
            thirdFragmentStatisticsLayout.setBackgroundResource(R.drawable.bg_dark_corner_radius)
            thirdFragmentBottomLayout.setBackgroundResource(R.drawable.bg_dark_corner_radius)
            thirdFragmentNumTitleTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            thirdFragmentMinTitleTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            thirdFragmentDayTitleTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        }
        else {
            thirdFragmentParentLayout.setBackgroundResource(R.drawable.bg_gradient)
            changeStatusBarMode(R.color.start_gradient_color)
            requireActivity().bottomNavigationView.setBackgroundResource(R.color.bottom_nav_color)
            thirdFragmentStatisticsLayout.setBackgroundResource(R.drawable.bg_white_corner_radius)
            thirdFragmentBottomLayout.setBackgroundResource(R.drawable.bg_gradient_corner_radius)
            thirdFragmentNumTitleTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            thirdFragmentMinTitleTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
            thirdFragmentDayTitleTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.black))
        }
    }

    private fun changeStatusBarMode(id: Int) {
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(requireContext(), id)
    }

    private fun saveUserDarkMode(input: Boolean) {
        val sharedPref = rootView.context.getSharedPreferences(MY_APP_USER_ACTIVITY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(USER_DARK_MODE, input)
        editor.apply()
    }

    private fun getUserDarkModeSharedPreferences(): Boolean {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getBoolean(USER_DARK_MODE, false)
    }

    private fun saveUserLanguage(language: Int) {
        val sharedPref = rootView.context.getSharedPreferences(MY_APP_USER_ACTIVITY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(USER_LANGUAGE, language)
        editor.apply()
    }

    private fun saveUserNameSurname(input: String?) {
        val sharedPref = rootView.context.getSharedPreferences(MY_APP_USER_ACTIVITY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_NAME_SURNAME, input)
        editor.apply()
    }

    private fun getLanguageSharedPreferences(): Int {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getInt(USER_LANGUAGE, 0)
    }

    private fun getUserNameSurnameSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_NAME_SURNAME, "default") ?:"default"
    }

    private fun getUserMeditationNumSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_MEDITATION_NUM, "0") ?:"0"
    }

    private fun getUserMeditationMinutesSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_MEDITATION_MINUTES, "0") ?:"0"
    }

    private fun getUserMeditationDaysSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = rootView.context.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_MEDITATION_DAYS, "0") ?:"0"
    }
}