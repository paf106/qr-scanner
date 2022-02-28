package com.pablo.qrscanner.ui.view.scan

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.navigation.findNavController

import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.pablo.qrscanner.ui.view.components.utils.QrScannerApplication.Companion.prefs
import com.pablo.qrscanner.R
import com.pablo.qrscanner.data.database.HistoryDatabase
import com.pablo.qrscanner.data.database.entities.HistoryEntity
import com.pablo.qrscanner.databinding.FragmentScanBinding

import com.pablo.qrscanner.ui.view.components.utils.Utils
import com.pablo.qrscanner.ui.view.components.utils.WebViewActivity
import com.pablo.qrscanner.ui.view.history.ScanType
import com.pablo.qrscanner.ui.view.history.historyType.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!


    private var scanner = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            val database = HistoryDatabase.getInstance(requireContext())
            val result = it.contents

            // Tiene resultado
            if (result.contains("http")) {

                // Comprobar si el ajuste de abrir webs automáticamente está activo
                if (prefs.isOpenWebEnabled()) {

                    // Comprobar si está el ajuste del navegador nativo activado
                    if (prefs.isNativeBrowserEnabled()) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result)))
                        if (prefs.isClipboardEnabled()) Utils.copyToClipboard(
                            requireContext(),
                            result
                        )
                        goToPreviousFragment()
                    } else {
                        Utils.goTo(requireContext(), "webUrl", result, WebViewActivity())
                        CoroutineScope(Dispatchers.IO).launch {
                            database.getHistoryDao().insert(
                                HistoryEntity(
                                    result,
                                    Utils.getCurrentDate(),
                                    ScanType.WEBSITE
                                )
                            )
                        }
                        goToPreviousFragment()

                    }
                } else {
                    Utils.goTo(requireContext(), "webUrl", result, WebTypeActivity())
                    CoroutineScope(Dispatchers.IO).launch {
                        database.getHistoryDao()
                            .insert(HistoryEntity(result, Utils.getCurrentDate(), ScanType.WEBSITE))
                    }
                    goToPreviousFragment()
                }
            } else if (result.contains("tel:")) {
                Utils.goTo(
                    requireContext(),
                    "phone",
                    result.split(":")[1],
                    PhoneTypeHistoryActivity()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    database.getHistoryDao()
                        .insert(
                            HistoryEntity(
                                result.split(":")[1],
                                Utils.getCurrentDate(),
                                ScanType.PHONE
                            )
                        )
                }
                goToPreviousFragment()

            } else if (result.contains("MAILTO:")) {
                Utils.goTo(
                    requireContext(),
                    "email",
                    result.split(":")[1],
                    EmailTypeHistoryActivity()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    database.getHistoryDao()
                        .insert(
                            HistoryEntity(
                                result.split(":")[1],
                                Utils.getCurrentDate(),
                                ScanType.EMAIL
                            )
                        )
                }
                goToPreviousFragment()

            } else if (result.contains("wifi:")) {
                Utils.goTo(requireContext(), "wifi", result, WifiTypeHistoryActivity())
                CoroutineScope(Dispatchers.IO).launch {
                    database.getHistoryDao()
                        .insert(HistoryEntity(result, Utils.getCurrentDate(), ScanType.WIFI))

                }
                goToPreviousFragment()

            } else {
                // Tipo texto
                Utils.goTo(requireContext(), "text", result, TextTypeHistoryActivity())
                CoroutineScope(Dispatchers.IO).launch {
                    database.getHistoryDao()
                        .insert(HistoryEntity(result, Utils.getCurrentDate(), ScanType.TEXT))
                }
                goToPreviousFragment()
            }
        } else {
            // No tiene resultado
            goToPreviousFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScanner()
    }

    private fun initScanner() {

        val options = ScanOptions()
        options.setBeepEnabled(prefs.isSoundToScanEnabled())
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setCameraId(0)
        //options.setTorchEnabled(true)

        scanner.launch(options)
    }

    private fun goToPreviousFragment() {
        view?.findNavController()?.navigate(R.id.historyFragment)
    }
}