package com.example.keepgoingapp

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.messaging.FirebaseMessaging
import android.view.View
import android.content.Intent
import android.net.Uri


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

        // ✨ Notification Permission хүсэх (Android 13 болон түүнээс дээш)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
        }

        // ✨ Firebase Device Token авах
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    android.util.Log.w("Firebase", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result
                android.util.Log.d("Firebase", "Device Token (fetched manually): $token")
            }
    }
    // ЭНЭ ДОТРОО дараах кодыг нэм:
    fun openWebsite(view: View) {
        val url = "https://sainaadesu.github.io/PersonalWeb/menu/programm/progamm.html" // өөрийн сайт оруул
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
