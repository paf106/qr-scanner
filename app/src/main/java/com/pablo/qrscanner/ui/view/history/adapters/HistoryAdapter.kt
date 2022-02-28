package com.pablo.qrscanner.ui.view.history.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pablo.qrscanner.R
import com.pablo.qrscanner.data.database.entities.HistoryEntity
import com.pablo.qrscanner.data.model.History


class HistoryAdapter(
    private val historyList: List<HistoryEntity>,
    private val onClickListener: (HistoryEntity) -> Unit
) :
    RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(layoutInflater.inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = historyList.size
}