package com.pablo.qrscanner.ui.view.generate

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pablo.qrscanner.databinding.FragmentGenerateBinding
import com.pablo.qrscanner.ui.view.generate.generateType.EmailTypeActivity
import com.pablo.qrscanner.ui.view.generate.generateType.PhoneTypeActivity
import com.pablo.qrscanner.ui.view.generate.generateType.TextTypeActivity
import com.pablo.qrscanner.ui.view.generate.generateType.WifiTypeActivity


class GenerateFragment : Fragment() {

    private var _binding: FragmentGenerateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGenerateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvTexto.setOnClickListener {
            startActivity(Intent(context, TextTypeActivity::class.java))
        }
        binding.cvWifi.setOnClickListener{
            startActivity(Intent(context,WifiTypeActivity::class.java))
        }

        binding.cvTelefono.setOnClickListener{
            startActivity(Intent(context, PhoneTypeActivity::class.java))
        }

        /*binding.cvEvento.setOnClickListener{
            startActivity(Intent(context, PhoneTypeActivity::class.java))
        }
        binding.cvContacto.setOnClickListener{
            startActivity(Intent(context, PhoneTypeActivity::class.java))
        }*/
        binding.cvEmail.setOnClickListener{
            startActivity(Intent(context, EmailTypeActivity::class.java))
        }


    }

}