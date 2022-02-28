package com.pablo.qrscanner.ui.view.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pablo.qrscanner.ui.view.components.utils.QrScannerApplication.Companion.prefs
import com.pablo.qrscanner.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Comprobar los ajustes existentes
        checkSettings()

        // Listeners para los ajustes
        binding.swSound.setOnClickListener { prefs.setSoundToScan(binding.swSound.isChecked) }
        binding.swOpenWebs.setOnClickListener { prefs.setOpenWeb(binding.swOpenWebs.isChecked) }
        binding.swBrowser.setOnClickListener { prefs.setNativeBrowser(binding.swBrowser.isChecked) }
        binding.swClipboard.setOnClickListener { prefs.setClipboard(binding.swClipboard.isChecked) }

    }

    private fun checkSettings() {
        if (prefs.isClipboardEnabled()) binding.swClipboard.isChecked = true
        if (prefs.isNativeBrowserEnabled()) binding.swBrowser.isChecked = true
        if (prefs.isOpenWebEnabled()) binding.swOpenWebs.isChecked = true
        if (prefs.isSoundToScanEnabled()) binding.swSound.isChecked = true
    }

}