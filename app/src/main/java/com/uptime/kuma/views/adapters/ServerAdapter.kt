package com.uptime.kuma.views.adapters

import android.annotation.SuppressLint
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
        @SuppressLint("RestrictedApi")
        fun bind(monitorStatusItem: MonitorStatusItem) {
            binding.apply {
                dashbordIdText.text = monitorStatusItem.monitorID.toString()
                dashbordServernameTv.visibility = View.GONE // hide username part
                dashbordTimeTv.text = monitorStatusItem.time
                dashbordMessageTv.text = monitorStatusItem.msg
                dashbordSlugTv.text = monitorStatusItem.name!!.toUpperCase().subSequence(0, 2)
                when (monitorStatusItem.status) {
                    1 -> {
                        dashbordStatusTv.text = getActivity(context)?.getText(R.string.online)
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .main_color
                            )
                        )
                        profileCard.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .background_active_item_all_server_fragment
                            )
                        )
                    }
                    0 -> {
                        dashbordStatusTv.text = getActivity(context)?.getText(R.string.offline)
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .dashbord_fragment_hors_ligne
                            )
                        )
                        profileCard.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .dashbord_fragment_hors_ligne
                            )
                        )
                    }
                    else -> {
                        dashbordStatusTv.text = getActivity(context)?.getText(R.string.pause)
                        cardViewStatus.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .attente
                            )
                        )
                        profileCard.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color
                                    .attente
                            )
                        )
                    }

                }
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