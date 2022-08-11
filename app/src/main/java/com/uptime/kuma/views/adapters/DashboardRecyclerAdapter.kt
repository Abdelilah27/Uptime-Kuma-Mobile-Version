package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.databinding.DashbordRecylcerItemBinding
import com.uptime.kuma.models.DashbordItems
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

class DashboardRecyclerAdapter(val context: Context, private val listener: OnItemClickListener) :
    ListAdapter<MonitorStatusItem, DashboardRecyclerAdapter.ItemViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            DashbordRecylcerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class ItemViewHolder(private val binding: DashbordRecylcerItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        fun bind(monitorStatusItem: MonitorStatusItem) {
            binding.apply {
                dashbordServernameTv.text = "Test"
                if (monitorStatusItem.status==1) {
                    dashbordStatusTv.text = "En ligne"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .main_color
                        )
                    )
                } else if (monitorStatusItem.status==0) {
                    dashbordStatusTv.text = "Hors ligne"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .dashbord_fragment_hors_ligne
                        )
                    )
                }
                else  {
                    dashbordStatusTv.text = "En pause"
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .dashbord_fragment_hors_ligne
                        )
                    )
                }

                dashbordTimeTv.text = monitorStatusItem.time
                dashbordMessageTv.text = monitorStatusItem.msg
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class DiffCallback : DiffUtil.ItemCallback<MonitorStatusItem>() {
        override fun areItemsTheSame(oldItem: MonitorStatusItem, newItem: MonitorStatusItem) : Boolean {
            return oldItem.monitorID==newItem.monitorID
        }

        override fun areContentsTheSame(oldItem: MonitorStatusItem, newItem: MonitorStatusItem) =
            oldItem == newItem
    }
}