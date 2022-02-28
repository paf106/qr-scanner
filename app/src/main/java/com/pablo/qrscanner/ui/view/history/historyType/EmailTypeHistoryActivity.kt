package com.pablo.qrscanner.ui.view.history.historyType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pablo.qrscanner.databinding.ActivityEmailTypeHistoryBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

class EmailTypeHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailTypeHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailTypeHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Email"

        val bundle = intent.extras
        val email = bundle?.getString("email")!!

        binding.txtTituloEmail.text = email

        binding.llCopy.setOnClickListener { Utils.copyToClipboard(this, email) }
        binding.llShare.setOnClickListener { Utils.shareTo(this, email) }
        binding.btnEnviarEmail.setOnClickListener { Utils.openEmailIntent(this, email) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}