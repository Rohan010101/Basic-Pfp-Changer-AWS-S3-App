package com.example.aws_profile_picture_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aws_profile_picture_app.ui.theme.AWSProfilePictureAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AWSProfilePictureAppTheme {
                Log.d("MainActivity", "Starting Pfp")
                Pfp()
            }
        }
    }
}