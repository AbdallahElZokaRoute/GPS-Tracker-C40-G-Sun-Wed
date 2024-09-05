package com.route.gps_trackerc40gsunwed.Kotlin_TIPS

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import com.route.gps_trackerc40gsunwed.Settings


lateinit var button: Button
lateinit var onSettingsItemClickListener: OnSettingsItemClickListener
fun setOnClick() {
    button.setOnClickListener { }
    onSettingsItemClickListener = object : OnSettingsItemClickListener {
        override fun onSettingsItemClick(settings: Settings, position: Int) {
            TODO("Not yet implemented")
        }

        override fun onSettingsDoubleCLick(settings: Settings, position: Int) {
            TODO("Not yet implemented")
        }

    }

}

interface OnSettingsItemClickListener {
    fun onSettingsItemClick(settings: Settings, position: Int)
    fun onSettingsDoubleCLick(settings: Settings, position: Int)
}

val settingsList = mutableListOf(
    Settings(),
    Settings(),
    Settings()
)

fun getItemCount(): Int = settingsList.size

