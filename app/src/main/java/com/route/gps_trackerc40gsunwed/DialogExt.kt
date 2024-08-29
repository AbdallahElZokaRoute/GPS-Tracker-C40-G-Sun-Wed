package com.route.gps_trackerc40gsunwed

import android.app.Activity
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AlertDialog

fun Activity.showDialog(
    message: String,
    positiveButtonText: String,
    onPositiveClickListener: OnClickListener,
    negativeButtonText: String?,
    onNegativeClickListener: OnClickListener?
) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
    builder.setPositiveButton(positiveButtonText, onPositiveClickListener)
    builder.setNegativeButton(negativeButtonText, onNegativeClickListener)
    builder.show()
}