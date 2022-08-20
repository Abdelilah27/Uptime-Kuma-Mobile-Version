package com.uptime.kuma.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uptime.kuma.R
import com.uptime.kuma.models.dashboardCalcul.CalculDashboardItem

class DashboardRecyclerCalculItemAdapter(val context: Context) :
    RecyclerView.Adapter<DashboardRecyclerCalculItemAdapter.ItemViewHolder>() {
    private var myList: List<CalculDashboardItem> = listOf<CalculDashboardItem>()

    fun setData(data: List<CalculDashboardItem>) {
        myList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardRecyclerCalculItemAdapter.ItemViewHolder {
        val binding =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dashboard_recycler_calcul_item, parent, false)
        return ItemViewHolder(binding)
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title_dashboard_calcul)
        var number: TextView = view.findViewById(R.id.number_dashboard_calcul)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        myList[position].let {
            holder.title.text = it.title
            holder.number.text = it.number
        }
    }

    override fun getItemCount() = myList.size

}