package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.models.serverCalcul.ServerCalcul
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items
import com.uptime.kuma.views.mainActivity.MainActivity

class MonitorItemAllServersAdapter(
    val context: Context, val listener: OnClickLister, val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<MonitorItemAllServersAdapter.ItemViewHolder>() {
    private var myList: List<ServerCalcul> = listOf()

    fun setData(data: List<ServerCalcul>) {
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
            holder.id.text = it.monitor_id.toString()
            val monitor = MainActivity.sharedViewModel.getMonitorById(it.monitor_id)
            val statusList =
                MainActivity.sharedViewModel.monitorCalcul[position].monitorStatus.take(16)
            holder.title.text = monitor.name
            holder.slug.text = monitor.name.toUpperCase().subSequence(0, 2)
            holder.percent.text = "100%"
            when (statusList[0].status) {
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
            setCallItemRecycler(
                holder.secondRecycler,
                statusList
            )
        }
    }

//    private fun getPercent(statusList: List<ServerCalcul_Items>): String {
//        var percent: String
//        var numberOfTrue = 1
//        statusList.forEach {
//            if (it.status == 1) {
//                numberOfTrue + 1
//                Log.d("numberOfTrue", numberOfTrue.toString())
//            }
//        }
//        percent = (numberOfTrue / (statusList.size + 1)).toString()
//        return "$percent %"
//    }

    private fun setCallItemRecycler(recyclerView: RecyclerView, list: List<ServerCalcul_Items>) {
        val adapter = MonitorItemAllServersCardAdapter(context)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.setData(list)
    }

    interface OnClickLister {
        fun onItemClick(position: Int)
    }
}