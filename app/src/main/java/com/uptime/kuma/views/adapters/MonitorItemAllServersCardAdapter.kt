package com.uptime.kuma.views.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

class MonitorItemAllServersCardAdapter(val context: Context) :
    RecyclerView.Adapter<MonitorItemAllServersCardAdapter.ItemViewHolder>() {
    private var myList: List<MonitorStatusItem> = listOf()

    fun setData(data: List<MonitorStatusItem>) {
        myList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_graph_server, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            holder.textView.text = it.status.toString()
            when (holder.textView.text) {
                "1" -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_active_item_all_server_fragment
                        )
                    )
                }
                "0" -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_no_active_item_all_server_fragment
                        )
                    )
                }
            }
        }
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: CardView = view.findViewById(R.id.server_card_graph)
        var textView: TextView = view.findViewById(R.id.textView5)
    }

    override fun getItemCount() = myList.size
}