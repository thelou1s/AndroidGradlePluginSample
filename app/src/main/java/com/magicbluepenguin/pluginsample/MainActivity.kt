package com.magicbluepenguin.pluginsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(
            "Log",
            "ANDROID_ID:" + Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        );
    }
}
