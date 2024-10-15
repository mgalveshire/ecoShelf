package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btnHome = findViewById<Button>(R.id.btn_home)
        val btnSearch = findViewById<Button>(R.id.btn_input)
        val btnAdd = findViewById<Button>(R.id.btn_stats)
        val btnNotifications = findViewById<Button>(R.id.btn_lifecycle)
        val btnProfile = findViewById<Button>(R.id.btn_options)

        btnHome.setOnClickListener {
            // Handle Home button click
        }
        btnSearch.setOnClickListener {
            // Handle Search button click
        }
        btnAdd.setOnClickListener {
            // Handle Add button click
        }
        btnNotifications.setOnClickListener {
            // Handle Notifications button click
        }
        btnProfile.setOnClickListener {
            // Handle Profile button click
        }

    }
}