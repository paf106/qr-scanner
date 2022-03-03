package com.pablo.qrscanner.ui.view.history.historyType

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pablo.qrscanner.databinding.ActivityWifiTypeHistoryBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils

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

        val wifiText = wifi.split(";") as ArrayList<String>

        val inicio = wifiText[0]
        wifiText.removeAt(0)
        wifiText.add(inicio.substring(inicio.indexOf(":") + 1))

        lateinit var ssid: String
        lateinit var password: String
        var isHidden = ""
        lateinit var cifrado: String

        for (s: String in wifiText) {
            if (s.startsWith("S:")) {
                ssid = s.split(":")[1]
            }
            if (s.startsWith("P:")) {
                password = s.split(":")[1]
            }
            if (s.startsWith("H:")) {
                isHidden = s.split(":")[1]
            }
            if (s.startsWith("T:") || s.startsWith("AT:")) {
                cifrado = s.split(":")[1]
            }
        }

        if (isHidden == "") {
            isHidden = "false"
        }

        binding.txtNombreWifi.text = ssid
        binding.txtPasswordWifi.text = password
        if (isHidden == "true") binding.txtOcultaWifi.text = "Si" else binding.txtOcultaWifi.text =
            "No"
        binding.txtCifradoWifi.text = cifrado

        binding.llCopy.setOnClickListener { Utils.copyToClipboard(this, wifi) }
        binding.llShare.setOnClickListener { Utils.shareTo(this, wifi) }
        binding.btnConectar.setOnClickListener { conectarAWifi() }
    }


    private fun conectarAWifi() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}