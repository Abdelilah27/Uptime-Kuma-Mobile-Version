package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.uptime.kuma.R
import com.uptime.kuma.databinding.DashbordRecylcerItemBinding
import com.uptime.kuma.models.monitorStatus.MonitorStatusItem

class DashboardRecyclerAdapter(val context: Context, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<DashboardRecyclerAdapter.ItemViewHolder>() {

    private var myList : List<MonitorStatusItem> = listOf<MonitorStatusItem>()

    fun setData(data :List<MonitorStatusItem>){
        myList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =LayoutInflater.from(parent.context).inflate(R.layout.dashbord_recylcer_item,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            holder.nom.text = "test"
            holder.date.text = it.time
            holder.msg.text = it.msg

        }

    }


   inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nom :TextView = view.findViewById(R.id.dashbord_servername_tv)
        var date  :TextView = view.findViewById(R.id.dashbord_time_tv)
        var msg  :TextView = view.findViewById(R.id.dashbord_message_tv)
    }


//    inner class ItemViewHolder(private val binding: DashbordRecylcerItemBinding) :
//        RecyclerView.ViewHolder(binding.root),
//        View.OnClickListener {
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        //get id to pass it between the dashboard and server fragment
//        override fun onClick(p0: View?) {
//            val position: String = binding.dashbordId.text as String
//            listener.onItemClick(position)
//        }
//
////        fun bind(monitorStatusItem: MonitorStatusItem) {
////            binding.apply {
////                dashbordId.text = monitorStatusItem.monitorID.toString()
////                dashbordServernameTv.text = "Test"
////                if (monitorStatusItem.status == 1) {
////                    dashbordStatusTv.text = getActivity(context)?.getText(R.string.online)
////                    cardViewStatus.setCardBackgroundColor(
////                        ContextCompat.getColor(
////                            context,
////                            R.color
////                                .main_color
////                        )
////                    )
////                } else if (monitorStatusItem.status == 0) {
////                    dashbordStatusTv.text = getActivity(context)?.getText(R.string.offline)
////                    cardViewStatus.setCardBackgroundColor(
////                        ContextCompat.getColor(
////                            context,
////                            R.color
////                                .dashbord_fragment_hors_ligne
////                        )
////                    )
////                } else {
////                    dashbordStatusTv.text = getActivity(context)?.getText(R.string.attente)
////                    cardViewStatus.setCardBackgroundColor(
////                        ContextCompat.getColor(
////                            context,
////                            R.color
////                                .attente
////                        )
////                    )
////                }
////                dashbordTimeTv.text = monitorStatusItem.time
////                dashbordMessageTv.text = monitorStatusItem.msg
////
////            }
////
////        }
//   }


    interface OnItemClickListener {
        fun onItemClick(position: String)
    }

    override fun getItemCount(): Int = myList.size

//    class DiffCallback : DiffUtil.ItemCallback<MonitorStatusItem>() {
//        override fun areItemsTheSame(
//            oldItem: MonitorStatusItem,
//            newItem: MonitorStatusItem
//        ): Boolean {
//            return oldItem.monitorID == newItem.monitorID
//        }
//
//        override fun areContentsTheSame(oldItem: MonitorStatusItem, newItem: MonitorStatusItem) =
//            oldItem == newItem
//    }
}