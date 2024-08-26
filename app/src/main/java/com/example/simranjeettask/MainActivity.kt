package com.example.simranjeettask

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.simranjeettask.ui.DashboardFragment
import com.example.simranjeettask.ui.LoginFragment
import com.example.simranjeettask.ui.SignUpFragment
import com.example.simranjeettask.utils.CommonFunctions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        CommonFunctions.replaceFragments(this,LoginFragment())
        backpress()
    }


    private fun backpress() {
        val dispatcher = onBackPressedDispatcher
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                    val count = supportFragmentManager.backStackEntryCount
                    if (count == 0) {
                        finishAffinity()
                    } else {
                        supportFragmentManager.popBackStack();
                    }
                }
            }

        dispatcher.addCallback(this, callback)
    }
}
