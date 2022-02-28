package com.pablo.qrscanner.ui.view.history.historyType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pablo.qrscanner.databinding.ActivityWifiTypeBinding
import com.pablo.qrscanner.databinding.ActivityWifiTypeHistoryBinding

class WifiTypeHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWifiTypeHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiTypeHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Wifi"

        val bundle = intent.extras
        val wifi = bundle?.getString("wifi")!!
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}