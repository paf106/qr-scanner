package com.pablo.qrscanner.ui.view.history.historyType

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pablo.qrscanner.databinding.ActivityPhoneTypeHistoryBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

class PhoneTypeHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneTypeHistoryBinding
    private val PERMISO_LLAMADA = 777
    private lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneTypeHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Teléfono"

        val bundle = intent.extras
        val phone = bundle?.getString("phone")!!

        this.phone = phone

        binding.txtTituloPhone.text = phone

        binding.llCopy.setOnClickListener { Utils.copyToClipboard(this, phone) }
        binding.llShare.setOnClickListener { Utils.shareTo(this, phone) }
        binding.btnLlamar.setOnClickListener { llamar(phone) }
    }

    private fun llamar(tel: String) {
        if (!Utils.isPermissionAccepted(this, Manifest.permission.CALL_PHONE)) {

            Utils.requestPermission(Manifest.permission.CALL_PHONE, this, PERMISO_LLAMADA)
        } else {
            Utils.openPhoneIntent(this, tel)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISO_LLAMADA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utils.openPhoneIntent(this, phone)
            } else {
                // No ha aceptadao el permiso
                // Toast.makeText(this, "No has aceptado el permiso", Toast.LENGTH_SHORT).show()
            }
        }
    }
}