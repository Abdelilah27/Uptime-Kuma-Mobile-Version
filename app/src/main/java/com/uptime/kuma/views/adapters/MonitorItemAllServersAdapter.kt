package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.models.monitor.Monitor
import com.uptime.kuma.models.serverCalcul.ServerCalcul
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items
import com.uptime.kuma.utils.RecyclerClickInterface
import com.uptime.kuma.views.mainActivity.MainActivity
import java.math.RoundingMode


class MonitorItemAllServersAdapter(
    val context: Context, val listener: RecyclerClickInterface
) :
    RecyclerView.Adapter<MonitorItemAllServersAdapter.ItemViewHolder>(), Filterable {
    private var myList: List<ServerCalcul> = listOf()
    private var originalList: List<ServerCalcul> = listOf()
    fun setData(data: List<ServerCalcul>) {
        myList = data
        originalList = myList
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
        init {
            view.setOnClickListener(this)
        }

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
                myList[position].let { monitorStatusItem ->
                    listener.onItemClick(monitorStatusItem.monitor_id)
                }
            }
        }
    }


    override fun getItemCount() = myList.size


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            holder.id.text = it.monitor_id.toString()
            val monitor = MainActivity.sharedViewModel.getMonitorById(it.monitor_id)
            val statusList =
                MainActivity.sharedViewModel.monitorCalcul[position].monitorStatus
            holder.title.text = monitor.name
            holder.slug.text = monitor.name!!.toUpperCase().subSequence(0, 2)
            holder.percent.text = getPercent(statusList)
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
                statusList.take(16)
            )
        }
    }

    private fun getPercent(statusList: List<ServerCalcul_Items>): CharSequence {
        var numberOfTrueOrFalse = 0.0
        statusList.forEach {
            if (it.status == 1) {
                numberOfTrueOrFalse += 1
            }
        }
        numberOfTrueOrFalse /= (statusList.size)
        numberOfTrueOrFalse *= 100
        val rounded = numberOfTrueOrFalse.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        return "$rounded. %"
    }

    private fun setCallItemRecycler(
        recyclerView: RecyclerView,
        list: List<ServerCalcul_Items>
    ) {
        val adapter = MonitorItemAllServersCardAdapter(context)
        recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        adapter.setData(list)
    }

    override fun getFilter(): Filter {
        var monitor: Monitor
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                if (constraint.isEmpty()) {
                    //no filter implemented we return full list
                    results.values = myList
                    results.count = myList.size
                } else {
                    val count: Int = myList.size
                    val nlist = ArrayList<ServerCalcul>(count)
                    for (item in myList) {
                        monitor = MainActivity.sharedViewModel.getMonitorById(item.monitor_id)
                        if (monitor.name!!.toUpperCase()
                                .contains(constraint.toString().toUpperCase())
                        ) nlist.add(item)
                    }
                    results.values = nlist
                    results.count = nlist.size
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                if (results.count == 0 || constraint == "") {
                    myList = originalList
                    notifyDataSetChanged()
                } else {
                    myList = (results.values as ArrayList<ServerCalcul>?)!!
                    notifyDataSetChanged()
                }
            }
        }
    }
}