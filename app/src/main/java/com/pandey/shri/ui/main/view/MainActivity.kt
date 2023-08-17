package com.pandey.shri.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.pandey.shri.R
import com.pandey.shri.databinding.ActivityMainBinding
import com.pandey.shri.utils.AppUpdateUtil
import com.pandey.shri.utils.InAppUpdate

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var binding: ActivityMainBinding
    private lateinit var inAppUpdate: InAppUpdate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
       // AppUpdateUtil().checkForUpdates(this)
        inAppUpdate = InAppUpdate(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        inAppUpdate.onActivityResult(requestCode,resultCode, data)
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