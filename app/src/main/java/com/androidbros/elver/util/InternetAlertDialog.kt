package com.androidbros.elver.util

import android.app.AlertDialog
import android.content.Context
import com.androidbros.elver.R
import kotlin.system.exitProcess

fun internetAlertDialogShow(context: Context) {


    val alertDialog = AlertDialog.Builder(context)
    alertDialog.setTitle("İnternet Bağlantısı")
    alertDialog.setMessage("İnternete bağlanılamadı.")
    alertDialog.setPositiveButton("Çıkış Yap") { dialog, which ->
        exitProcess(-1)
    }
    alertDialog.show()


}