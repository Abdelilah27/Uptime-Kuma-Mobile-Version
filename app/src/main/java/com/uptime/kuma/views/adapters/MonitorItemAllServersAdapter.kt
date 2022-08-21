package com.uptime.kuma.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.models.ServerCard
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

class MonitorItemAllServersAdapter(
    val context: Context, val listener: OnClickLister
) :
    RecyclerView.Adapter<MonitorItemAllServersAdapter.ItemViewHolder>() {
    private var myList: List<ServerCard> = listOf()

    fun setData(data: List<ServerCard>) {
        myList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonitorItemAllServersAdapter.ItemViewHolder {
        val binding =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_all_servers_fragment, parent, false)
        return ItemViewHolder(binding)
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var id: TextView = view.findViewById(R.id.dashbord_id)
        var title: TextView = view.findViewById(R.id.title_text)
        var slug: TextView = view.findViewById(R.id.all_servers_slug_tv)
        var percent: TextView = view.findViewById(R.id.percent_text)
        var card: CardView = view.findViewById(R.id.card_view_status)
        var profileCardServers: CardView = view.findViewById(R.id.profile_card_servers)
        var secondRecycler: RecyclerView = view.findViewById(R.id.card_graph_recycler)
        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }


    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            holder.id.text = it.monitor.id.toString()
            holder.title.text = it.monitor.name
            holder.slug.text = it.monitor.name.toUpperCase().subSequence(0, 2)
            holder.percent.text = "99.99%"
            when (it.monitor.active) {
                0 -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_no_active_item_all_server_fragment
                        )
                    )
                    holder.profileCardServers.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_no_active_item_all_server_fragment
                        )
                    )
                }
                1 -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_active_item_all_server_fragment
                        )
                    )
                    holder.profileCardServers.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .background_active_item_all_server_fragment
                        )
                    )

                }
                else -> {
                    holder.card.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .attente
                        )
                    )
                    holder.profileCardServers.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .attente
                        )
                    )
                }
            }
            setCallItemRecycler(holder.secondRecycler, myList[position].listOfStatus)
        }

    }

    private fun setCallItemRecycler(recyclerView: RecyclerView, list: List<MonitorStatusItem>) {
        val adapter = MonitorItemAllServersCardAdapter(context)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.setData(list)
    }

    interface OnClickLister {
        fun onItemClick(position: Int)
    }
}