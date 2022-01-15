package com.example.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.mainactivity.databinding.ActivityEtatLedBinding

class EtatLedActivity : AppCompatActivity() {
    private lateinit var ledStatus: LedStatus
    private lateinit var binding: ActivityEtatLedBinding

    companion object {
        const val PI_IDENTIFIER = "PI_IDENTIFIER"
        fun getStartIntent(context: Context, piIdentifier: String?): Intent {
            return Intent(context, EtatLedActivity::class.java).apply {
                putExtra(PI_IDENTIFIER, piIdentifier)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEtatLedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reload.setOnClickListener {
            getStatus()
        }


        binding.changeEtat.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    ledStatus = ApiService.instance.writeStatus(ledStatus.reverseStatus())
                    setVisualState()
                }
            }
        }
    }


    // Récupération de l'état depuis le serveur
    private fun getStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                ledStatus = ApiService.instance.readStatus(getIdentifier())
                setVisualState()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        getStatus()
    }

    private fun setVisualState(){
        if ( ledStatus.status){
            binding.etatLed.setImageResource(R.drawable.led_on)
        } else {
            binding.etatLed.setImageResource(R.drawable.led_off)
        }
    }

    private fun getIdentifier():String{
        return intent.getStringExtra(PI_IDENTIFIER)!!
    }
}