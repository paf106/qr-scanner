package com.pablo.qrscanner.ui.view.generate.generateType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pablo.qrscanner.databinding.ActivityTextTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

class TextTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Generar texto"

        binding.btnGenerarText.setOnClickListener { generateTextCode() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun generateTextCode() {
        if (binding.tfTextContent.length() != 0) {
            binding.ivTextType.setImageBitmap(Utils.generateQrCode(binding.tfTextContent.text.toString()))
            binding.llDownloadShare.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Escribe algo", Toast.LENGTH_SHORT).show()
        }
    }
}