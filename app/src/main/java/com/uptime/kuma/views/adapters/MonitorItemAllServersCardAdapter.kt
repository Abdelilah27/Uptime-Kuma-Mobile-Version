package com.uptime.kuma.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items

class MonitorItemAllServersCardAdapter(val context: Context) :
    RecyclerView.Adapter<MonitorItemAllServersCardAdapter.ItemViewHolder>() {
    private var myList: List<ServerCalcul_Items> = listOf()

    fun setData(data: List<ServerCalcul_Items>) {
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
//            Log.d("SSS", it.status.toString())
            when (it.status) {
                1 -> {

                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color
                                .background_active_item_all_server_fragment
                        )
                    )
                }
                0 -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color
                                .background_no_active_item_all_server_fragment
                        )
                    )
                }
                else -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color
                                .attente
                        )
                    )
                }
            }
        }
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var card: CardView = view.findViewById(R.id.server_card_graph)
    }

    override fun getItemCount() = myList.size
}