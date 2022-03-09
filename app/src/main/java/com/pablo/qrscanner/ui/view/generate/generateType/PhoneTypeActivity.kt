package com.pablo.qrscanner.ui.view.generate.generateType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pablo.qrscanner.databinding.ActivityPhoneTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

class PhoneTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneTypeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Generar teléfono"

        binding.btnGenerarPhone.setOnClickListener { generateTextCode() }
        binding.cvDownload.setOnClickListener {
            Utils.saveToGallery(
                this,
                this,
                binding.tfPhoneContent.text.toString()
            )
        }
        binding.cvShare.setOnClickListener {
            Utils.shareTo(
                this,
                binding.tfPhoneContent.text.toString()
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun generateTextCode() {
        if (binding.tfPhoneContent.length() != 0) {
            binding.ivPhoneType.setImageBitmap(Utils.generateQrCode("tel:${binding.tfPhoneContent.text.toString()}"))
            binding.llDownloadShare.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "Escribe algo", Toast.LENGTH_SHORT).show()
        }
    }
}