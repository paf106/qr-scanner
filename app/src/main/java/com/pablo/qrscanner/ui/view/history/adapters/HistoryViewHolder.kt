package com.pablo.qrscanner.ui.view.history.adapters


import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.pablo.qrscanner.R
import com.pablo.qrscanner.data.database.HistoryDatabase
import com.pablo.qrscanner.data.database.entities.HistoryEntity
import com.pablo.qrscanner.databinding.ItemHistoryBinding
import com.pablo.qrscanner.ui.view.history.ScanType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemHistoryBinding.bind(view)
    val database = HistoryDatabase.getInstance(view.context)

    fun render(history: HistoryEntity, onClickListener: (HistoryEntity) -> Unit) {
        binding.txtEscaneo.text = history.text
        binding.txtTimestamp.text = history.timestamp
        when (history.scanType) {
            ScanType.EMAIL -> binding.ivScanType.setImageResource(R.drawable.ic_email)
            ScanType.CONTACT -> binding.ivScanType.setImageResource(R.drawable.ic_contacts)
            ScanType.PHONE -> binding.ivScanType.setImageResource(R.drawable.ic_phone)
            ScanType.EVENT -> binding.ivScanType.setImageResource(R.drawable.ic_calendar_month)
            ScanType.WIFI -> binding.ivScanType.setImageResource(R.drawable.ic_wifi)
            ScanType.WEBSITE -> binding.ivScanType.setImageResource(R.drawable.ic_search)
            ScanType.TEXT -> binding.ivScanType.setImageResource(R.drawable.ic_text)
        }
        binding.ivOptions.setOnClickListener { showPopupMenu(it, history) }
        itemView.setOnClickListener { onClickListener(history) }
    }

    private fun showPopupMenu(v: View, history: HistoryEntity) {
        val popupMenu = PopupMenu(itemView.context, v)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.mnuDelete -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        database.getHistoryDao().delete(history)
                    }
                    true
                }
                else -> true
            }
        }
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
        popupMenu.show()
    }
}