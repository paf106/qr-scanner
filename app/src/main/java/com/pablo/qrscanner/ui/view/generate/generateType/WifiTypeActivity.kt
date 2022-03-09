package com.pablo.qrscanner.ui.view.generate.generateType

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.pablo.qrscanner.R
import com.pablo.qrscanner.databinding.ActivityWifiTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.QrScannerApplication.Companion.PERMISO_DISCO
import com.pablo.qrscanner.ui.view.components.utils.Utils
import com.pablo.qrscanner.ui.view.components.utils.Utils.Companion.saveToGallery


class WifiTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWifiTypeBinding
    private lateinit var text: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWifiTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Generar Wifi"

        // Poner valor por defecto
        binding.tfEncryptionContent.setText("WEP")

        // Poner los valores al desplegable
        val items = resources.getStringArray(R.array.wifiOptions)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.tfEncryptionContent.setAdapter(adapter)

        binding.btnGenerarWifi.setOnClickListener { generateWifiCode() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun generateWifiCode() {
        if (binding.tfWifiContent.length() != 0 && binding.tfPasswordContent.length() != 0) {

            val ssid = binding.tfWifiContent.text.toString()
            var encrypt = binding.tfEncryptionContent.text.toString()
            val pass = binding.tfPasswordContent.text.toString()
            val hidden = binding.chkHidden.isChecked

            text = "WIFI:S:$ssid;T:$encrypt;P:$pass;H:$hidden;"

            if (encrypt == "Sin contraseña") {
                encrypt = "nopass"
                text = "WIFI:S:$ssid;T:$encrypt;H:$hidden;"
            }
            if (encrypt == "WPA/WPA2") {
                encrypt = "WPA"
                text = "WIFI:S:$ssid;T:$encrypt;H:$hidden;"
            }

            binding.ivWifiType.setImageBitmap(Utils.generateQrCode(text))
            binding.llDownloadShare.visibility = View.VISIBLE
            binding.cvDownload.setOnClickListener { saveToGallery(this, this, text) }
            binding.cvShare.setOnClickListener { sendTo() }
        } else {
            Toast.makeText(this, "Tienes que rellenar los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendTo() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, text)
            type = "image/png"
        }
        startActivity(Intent.createChooser(shareIntent, "Compartir por"))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISO_DISCO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveToGallery(this, this, text)
            } else {
                Utils.requestPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    this,
                    PERMISO_DISCO
                )
                // No ha aceptadao el permiso
                //Toast.makeText(this, "No has aceptado el permiso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}