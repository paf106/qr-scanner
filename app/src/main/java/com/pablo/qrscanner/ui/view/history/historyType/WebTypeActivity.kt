package com.pablo.qrscanner.ui.view.history.historyType

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.pablo.qrscanner.ui.view.components.utils.QrScannerApplication.Companion.prefs
import com.pablo.qrscanner.databinding.ActivityWebTypeBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils
import com.pablo.qrscanner.ui.view.components.utils.WebViewActivity
import com.pablo.qrscanner.ui.viewmodel.HistoryViewModel

class WebTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebTypeBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel = ViewModelProvider(this).get()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Página web"

        val bundle = intent.extras
        val url = bundle?.getString("webUrl")!!

        binding.txtTituloWeb.text = url

        binding.btnAbrirEnlace.setOnClickListener {

            // Comprobar si está el ajuste del navegador nativo activado
            if (prefs.isNativeBrowserEnabled()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } else {
                /*val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("webUrl", binding.txtTituloWeb.text)
                startActivity(intent)*/
                Utils.goTo(this,"webUrl",url,WebViewActivity())

            }
        }

        binding.llCopy.setOnClickListener { Utils.copyToClipboard(this, url) }
        binding.llShare.setOnClickListener { Utils.shareTo(this, url) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}