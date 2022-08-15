package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.uptime.kuma.R
import com.uptime.kuma.databinding.DashbordRecylcerItemBinding
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

class ServerAdapter(val context: Context) :
    ListAdapter<MonitorStatusItem, ServerAdapter.ServerViewHolder>(DiffCallback()) {
    inner class ServerViewHolder(private val binding: DashbordRecylcerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(monitorStatusItem: MonitorStatusItem) {
            binding.apply {
                dashbordId.text = monitorStatusItem.monitorID.toString()
                dashbordServernameTv.visibility = View.GONE // hide username part
                if (monitorStatusItem.status == 1) {
                    dashbordStatusTv.text = getActivity(context)?.getText(R.string.online)
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .main_color
                        )
                    )
                } else if (monitorStatusItem.status == 0) {
                    dashbordStatusTv.text = getActivity(context)?.getText(R.string.offline)
                    cardViewStatus.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color
                                .dashbord_fragment_hors_ligne
                        )
                    )
                } else {
                    dashbordStatusTv.text = getActivity(context)?.getText(R.string.pause)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val binding =
            DashbordRecylcerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallback : DiffUtil.ItemCallback<MonitorStatusItem>() {
        override fun areItemsTheSame(
            oldItem: MonitorStatusItem,
            newItem: MonitorStatusItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MonitorStatusItem, newItem: MonitorStatusItem) =
            oldItem == newItem
    }
}