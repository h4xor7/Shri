package com.pandey.shri.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.pandey.shri.R
import com.pandey.shri.databinding.ActivityMainBinding
import com.pandey.shri.utils.AppUpdateUtil
import com.pandey.shri.utils.InAppUpdate

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var binding: ActivityMainBinding
    private lateinit var inAppUpdate: InAppUpdate
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.page_home, R.id.page_detail))
        binding.mainToolBar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.page_home, R.id.page_detail -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }

                else -> {
                    binding.bottomNavigation.visibility = View.GONE

                }

            }


        }


        //NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
        // AppUpdateUtil().checkForUpdates(this)
        inAppUpdate = InAppUpdate(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        inAppUpdate.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdate.onDestroy()
    }


}