package com.github.drewstephensdesigns.astrofeed

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.github.drewstephensdesigns.astrofeed.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maxkeppeler.sheets.core.SheetStyle
import com.maxkeppeler.sheets.option.DisplayMode
import com.maxkeppeler.sheets.option.Option
import com.maxkeppeler.sheets.option.OptionSheet
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toastyConfig()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_launches,
                R.id.navigation_news,
                R.id.navigation_rockets,
                R.id.navigation_about
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
       binding.fabThemeToggle.setOnClickListener {
           initUI()
       }
        applyTheme()
    }

    //Moving back to list fragment by clicking on back arrow button
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun toastyConfig(){
        val typeface: Typeface? = ResourcesCompat.getFont(applicationContext, R.font.noto_sans)
        Toasty.Config.getInstance()
            .setTextSize(14)
            .setToastTypeface(typeface!!)
            .supportDarkTheme(true)
            .apply()
    }

    fun applyTheme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val modeNight = sharedPreferences.getInt(
            getString(R.string.pref_key_mode_night),
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

        AppCompatDelegate.setDefaultNightMode(modeNight)
    }

    private fun initUI(){
        OptionSheet().show(this) {
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
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        sharedPreferences.edit().putInt(
                            getString(R.string.pref_key_mode_night),
                            AppCompatDelegate.MODE_NIGHT_NO
                        ).apply()

                    }
                    1 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        sharedPreferences.edit().putInt(
                            getString(R.string.pref_key_mode_night),
                            AppCompatDelegate.MODE_NIGHT_YES
                        ).apply()

                    }
                    2 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        sharedPreferences.edit().putInt(
                            getString(R.string.pref_key_mode_night),
                            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        ).apply()
                    }
                }
            }
        }
    }
}