package com.example.meditation

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import com.example.meditation.dialog_fragments.DialogFragmentNameSurname
import com.example.meditation.main_window.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class ThirdFragment : Fragment(), AdapterView.OnItemSelectedListener, DialogFragmentNameSurname.OnInputSelected {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View

    private lateinit var thirdFragmentParentLayout: FrameLayout

    private lateinit var userNameSurnameTextView: TextView
    private lateinit var userNameSurnameEditImageView: ImageView

    private lateinit var userMeditationNumTextView: TextView
    private lateinit var userMeditationMinutesTextView: TextView
    private lateinit var userMeditationDaysTextView: TextView

    private lateinit var switch: Switch
    private lateinit var spinner: Spinner
    private lateinit var locale: Locale

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
        rootView = inflater.inflate(R.layout.fragment_third, container, false)
        // Inflate the layout for this fragment

//        Toast.makeText(rootView.context, "third fragment", Toast.LENGTH_SHORT).show()

        init()

        return rootView
    }

    private fun init() {

        thirdFragmentParentLayout = rootView.findViewById(R.id.third_fragment_parent_layout)
        if (getUserDarkModeSharedPreferences()) {
            thirdFragmentParentLayout.setBackgroundResource(R.drawable.bg_dark)
        }
        else {
            thirdFragmentParentLayout.setBackgroundResource(R.drawable.bg_gradient)
        }

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

        switch = rootView.findViewById(R.id.third_fragment_switch)
        switch.isChecked = getUserDarkModeSharedPreferences()
        switch.setOnCheckedChangeListener { _, isChecked ->
            saveUserDarkMode(isChecked)
            activity?.recreate()
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

        activity?.recreate()
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
    

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}