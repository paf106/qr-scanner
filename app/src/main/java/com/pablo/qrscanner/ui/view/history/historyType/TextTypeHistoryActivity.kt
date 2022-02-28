package com.pablo.qrscanner.ui.view.history.historyType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pablo.qrscanner.databinding.ActivityQrTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

class TextTypeHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Texto"

        val bundle = intent.extras
        val text = bundle?.getString("text")!!

        binding.txtTituloTexto.text = text

        binding.llCopy.setOnClickListener { Utils.copyToClipboard(this, text) }
        binding.llShare.setOnClickListener { Utils.shareTo(this, text) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}