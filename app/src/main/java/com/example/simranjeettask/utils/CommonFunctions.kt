package com.example.simranjeettask.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.simranjeettask.R

object CommonFunctions {

    fun replaceFragments(activity: FragmentActivity, fragment: Fragment) {
        activity.supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack("hi")
            .commit()
    }

    fun showToast(context: Context, msg:String){
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }


}