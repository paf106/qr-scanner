package com.pablo.qrscanner.ui.view.generate.generateType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pablo.qrscanner.databinding.ActivityEmailTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

class EmailTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailTypeBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Generar email"

        binding.btnGenerarEmail.setOnClickListener { generateTextCode() }
        binding.cvDownload.setOnClickListener {
            Utils.saveToGallery(
                this,
                this,
                binding.tfEmailContent.text.toString()
            )
        }
        binding.cvShare.setOnClickListener {
            Utils.shareTo(
                this,
                binding.tfEmailContent.text.toString()
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun generateTextCode() {
        if (binding.tfEmailContent.length() != 0) {
            binding.ivEmailType.setImageBitmap(Utils.generateQrCode("mailto:${binding.tfEmailContent.text.toString()}"))
            binding.llDownloadShare.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Escribe algo", Toast.LENGTH_SHORT).show()
        }
    }
}