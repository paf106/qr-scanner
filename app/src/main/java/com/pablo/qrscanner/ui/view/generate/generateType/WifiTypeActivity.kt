package com.pablo.qrscanner.ui.view.generate.generateType

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.pablo.qrscanner.R
import com.pablo.qrscanner.databinding.ActivityWifiTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class WifiTypeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWifiTypeBinding
    private val PERMISO_DISCO = 8
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
            binding.cvDownload.setOnClickListener { saveToGallery() }
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

    private fun saveToGallery() {
        if (Utils.isPermissionAccepted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val bitmap = Utils.generateQrCode(text)
            lateinit var outputStream: FileOutputStream
            val file = Environment.getExternalStorageDirectory()
            val dir = File(file.absolutePath + "/QrScanner")
            dir.mkdirs()

            val filename = "$text.png"
            val outFile = File(dir, filename)
            try {
                outputStream = FileOutputStream(outFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()

            } catch (e: Exception) {

            } finally {
                outputStream.close()
            }
            Toast.makeText(this, "Hecho", Toast.LENGTH_SHORT).show()
        } else {
            Utils.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, this, 77)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISO_DISCO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveToGallery()
            } else {
                // No ha aceptadao el permiso
                 Toast.makeText(this, "No has aceptado el permiso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}