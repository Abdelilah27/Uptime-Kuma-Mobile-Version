package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.databinding.ItemAllServersFragmentBinding
import com.uptime.kuma.models.serverCalcul.ServerCalcul
import com.uptime.kuma.models.serverCalcul.ServerCalcul_Items
import com.uptime.kuma.views.mainActivity.MainActivity
import java.util.*

class MonitorItemAllServersAdapter(
    val context: Context, val listener: OnClickLister
) :
    ListAdapter<ServerCalcul, MonitorItemAllServersAdapter.ViewHolder>(DifCallback()), Filterable {
    private var list = mutableListOf<ServerCalcul>()
    private var count = 0 // to check if the filtered list is empty

    fun setData(list: MutableList<ServerCalcul>?) {
        this.list = list!!
        submitList(list)
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonitorItemAllServersAdapter.ViewHolder {
        val binding =
            ItemAllServersFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MonitorItemAllServersAdapter.ViewHolder,
        position: Int
    ) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ItemAllServersFragmentBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


        fun bind(item: ServerCalcul) {
            binding.apply {
                idText.text = item.monitor_id.toString()
                val monitor = MainActivity.sharedViewModel.getMonitorById(item.monitor_id)
                val statusList =
                    MainActivity.sharedViewModel.monitorCalcul[position].monitorStatus
                titleText.text = monitor.name
                allServersSlugTv.text = monitor.name.toUpperCase().subSequence(0, 2)
                percentText.text = "100 %"
                when (statusList[0].status) {
                    0 -> {
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_no_active_item_all_server_fragment
                            )
                        )
                        profileCardServers.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_no_active_item_all_server_fragment
                            )
                        )
                    }
                    1 -> {
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_active_item_all_server_fragment
                            )
                        )
                        profileCardServers.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_active_item_all_server_fragment
                            )
                        )

                    }
                    else -> {
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .attente
                            )
                        )
                        profileCardServers.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .attente
                            )
                        )
                    }
                }
                setCallItemRecycler(
                    cardGraphRecycler,
                    statusList.take(16)
                )
            }
        }
    }


//    private fun getPercent(statusList: ArrayList<ServerCalcul_Items>): CharSequence {
//        var numberOfTrueOrFalse = 0.0
//        val status = statusList[0].status
//        statusList.forEach {
//            if (it.status == status) {
//                numberOfTrueOrFalse += 1
//            }
//        }
//        numberOfTrueOrFalse /= (statusList.size + 1)
//        numberOfTrueOrFalse *= 100
//        val rounded = numberOfTrueOrFalse.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
//        return "$rounded. %"
//    }

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

    class DifCallback : DiffUtil.ItemCallback<ServerCalcul>() {
        override fun areItemsTheSame(oldItem: ServerCalcul, newItem: ServerCalcul) =
            oldItem.monitor_id == newItem.monitor_id

        override fun areContentsTheSame(oldItem: ServerCalcul, newItem: ServerCalcul) =
            oldItem.monitor_id == newItem.monitor_id
    }

    interface OnClickLister {
        fun onItemClick(position: Int)
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<ServerCalcul>()
            if (constraint!!.isEmpty()) {
                filteredList.addAll(list)
//                Log.d("filteredList", filteredList.toString())
            } else {
                for (item in list) {
                    val monitor = MainActivity.sharedViewModel.getMonitorById(item.monitor_id)
                    if (monitor.name.toLowerCase(Locale.getDefault()).contains(constraint)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            count = (results.values as MutableList<ServerCalcul>).count()
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            if (count > 0) {
                submitList(filterResults?.values as MutableList<ServerCalcul>)
            } else {
                Collections.addAll(list)
            }
        }

    }
}