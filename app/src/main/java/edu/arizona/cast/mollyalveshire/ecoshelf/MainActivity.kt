package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnHome = findViewById<Button>(R.id.btn_home)
        val btnInput = findViewById<Button>(R.id.btn_input)
        val btnStats = findViewById<Button>(R.id.btn_stats)
        val btnLifecycle = findViewById<Button>(R.id.btn_lifecycle)
        val btnOptions = findViewById<Button>(R.id.btn_options)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        btnHome.setOnClickListener {
            loadFragment(HomeFragment())
        }
        btnInput.setOnClickListener {
            loadFragment(InputFragment())
        }
        btnStats.setOnClickListener {
            loadFragment(StatsFragment())

        }
        btnLifecycle.setOnClickListener {
            loadFragment(LifecycleFragment())

        }
        btnOptions.setOnClickListener {
            loadFragment(OptionsFragment())

        }
    }
    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, fragment)
        transaction.commit()
    }
}