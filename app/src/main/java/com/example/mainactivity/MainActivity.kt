package com.example.mainactivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mainactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // <-- Référence à notre ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --> Indique que l'on utilise le ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanner.setOnClickListener {
            startActivity(ScannerPeriphActivity.getStartIntent(this))
        }

        binding.commander.setOnClickListener {
            if (LocalPreferences.getInstance(this).lastConnectedDeviceName() == null) {
                Toast.makeText(this, "Connection à un périphérique", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(EtatLedActivity.getStartIntent(this, LocalPreferences.getInstance(this).lastConnectedDeviceName()))
            }
        }
    }

}
