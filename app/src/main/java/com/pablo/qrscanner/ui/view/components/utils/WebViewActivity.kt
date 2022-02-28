package com.pablo.qrscanner.ui.view.components.utils

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.pablo.qrscanner.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val url = bundle?.getString("webUrl")!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = url

        binding.wbWeb.settings.javaScriptEnabled = true
        binding.wbWeb.webViewClient = WebViewClient()
        binding.wbWeb.loadUrl(url)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}