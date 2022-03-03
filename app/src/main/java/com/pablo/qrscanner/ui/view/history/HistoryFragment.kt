package com.pablo.qrscanner.ui.view.history


import android.app.AlertDialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.pablo.qrscanner.R
import com.pablo.qrscanner.data.database.HistoryDatabase
import com.pablo.qrscanner.data.database.entities.HistoryEntity
import com.pablo.qrscanner.ui.view.history.adapters.HistoryAdapter
import com.pablo.qrscanner.databinding.FragmentHistoryBinding
import com.pablo.qrscanner.ui.view.components.utils.Utils
import com.pablo.qrscanner.ui.view.history.historyType.*

import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var database :HistoryDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);
        database = HistoryDatabase.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // val database = HistoryDatabase.getInstance(view.context)
        database.getHistoryDao().getAll().observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuHistoryDelete -> createAlert()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onItemSelected(history: HistoryEntity) {
        when (history.scanType) {
            ScanType.TEXT -> Utils.goTo(
                requireContext(),
                "text",
                history.text,
                TextTypeHistoryActivity()
            )
            ScanType.PHONE -> Utils.goTo(
                requireContext(),
                "phone",
                history.text,
                PhoneTypeHistoryActivity()
            )
            ScanType.EMAIL -> Utils.goTo(
                requireContext(),
                "email",
                history.text,
                EmailTypeHistoryActivity()
            )
            ScanType.WEBSITE -> Utils.goTo(
                requireContext(),
                "webUrl",
                history.text,
                WebTypeActivity()
            )
            ScanType.WIFI -> Utils.goTo(
                requireContext(),
                "wifi",
                history.text,
                WifiTypeHistoryActivity()
            )
        }
    }

    fun initRecyclerView(listaInscripciones: List<HistoryEntity>) {
        binding.recyclerHistory.layoutManager = LinearLayoutManager(context)
        binding.recyclerHistory.adapter = HistoryAdapter(listaInscripciones) { onItemSelected(it) }
    }

    fun createAlert() {
        //val database = HistoryDatabase.getInstance(requireContext())
        val builder = AlertDialog.Builder(context)
        builder.setMessage("¿Seguro que quieres eliminar todo el historial?")
        builder.setTitle("Eliminar historial")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener { dialog, id ->
                    lifecycleScope.launch { database.getHistoryDao().deleteAll() }
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->
                })
        builder.create()
        builder.show()
    }
}