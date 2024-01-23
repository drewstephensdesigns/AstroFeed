package com.github.drewstephensdesigns.astrofeed.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.drewstephensdesigns.astrofeed.R
import com.github.drewstephensdesigns.astrofeed.databinding.FragmentAboutBinding
import com.google.android.material.transition.MaterialSharedAxis
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.option.DisplayMode
import com.maxkeppeler.sheets.option.Option
import com.maxkeppeler.sheets.option.OptionSheet

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    // Shared Prefs
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI(){
        with(binding){
            textAboutCompany.text = getString(R.string.title_company)
            textAboutDeveloper.text = getString(R.string.title_about)
            textChangeTheme.text = getString(R.string.action_change_theme)

            textAboutCompany.setOnClickListener {
                findNavController().navigate(R.id.navigation_company)
            }

            textAboutDeveloper.setOnClickListener {
                findNavController().navigate(R.id.navigation_about_dev)
            }

            textChangeTheme.setOnClickListener {
                OptionSheet().show(requireActivity()) {
                    style(SheetStyle.DIALOG)
                    displayToolbar(true)
                    title("Set Theme")
                    displayMode(DisplayMode.GRID_HORIZONTAL)
                    with(
                        Option(R.drawable.ic_light_mode, "Light"),
                        Option(R.drawable.ic_dark_mode, "Dark"),
                        Option(R.drawable.ic_follow_system, "Follow System")
                    )
                    onPositive { index: Int, _: Option ->
                        when(index){
                            0 -> {
                                sharedPreferences.edit().putInt(
                                    getString(R.string.pref_key_mode_night),
                                    MODE_NIGHT_NO
                                ).apply()
                                setDefaultNightMode(MODE_NIGHT_NO)
                            }
                            1 -> {
                                sharedPreferences.edit().putInt(
                                    getString(R.string.pref_key_mode_night),
                                    MODE_NIGHT_YES
                                ).apply()
                                setDefaultNightMode(MODE_NIGHT_YES)
                            }
                            2 -> {
                                sharedPreferences.edit().putInt(
                                    getString(R.string.pref_key_mode_night),
                                    MODE_NIGHT_FOLLOW_SYSTEM
                                ).apply()
                                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}