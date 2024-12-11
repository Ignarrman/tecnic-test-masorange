package com.masorange.tecnictest

import android.os.Bundle
import android.net.Uri
import androidx.activity.ComponentActivity

class AuthRedirectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data: Uri? = intent?.data
        if (data != null) {
            val fragment = data.fragment
            if (fragment != null) {
                val params = fragment.split("&")
                    .associate {
                        val (key, value) = it.split("=")
                        key to value
                    }

                val accessToken = params["access_token"] ?: ""
                println("Access Token: $accessToken")
            }
        }
        finish()
    }
}
