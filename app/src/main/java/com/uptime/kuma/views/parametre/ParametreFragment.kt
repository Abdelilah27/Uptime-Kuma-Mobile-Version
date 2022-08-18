package com.uptime.kuma.views.parametre

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.views.mainActivity.MainActivity
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentParametreBinding
import com.uptime.kuma.models.ParametreItem
import com.uptime.kuma.views.adapters.ParametreRecyclerAdapter

class ParametreFragment : Fragment(R.layout.fragment_parametre),ParametreRecyclerAdapter.OnItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=FragmentParametreBinding.bind(view)

        val parametreRecyclerAdapter=ParametreRecyclerAdapter(requireContext(),this)



        binding.apply {
        parametreRecycler.apply {
            adapter=parametreRecyclerAdapter
            layoutManager=LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            parametreRecyclerAdapter.submitList(getData())
        }
        }
    }
    override fun onItemClick(position: Int) {
        when (getData()[position].paramname) {
            "About" -> MainActivity.navController.navigate(R.id.aboutFragment)
            "Notifications" -> MainActivity.navController.navigate(R.id.notificationsFragment)
            "Apparence" -> MainActivity.navController.navigate(R.id.apparenceReglageFragment)

        }
    }

    private fun getData():List<ParametreItem>{
        val data= arrayListOf<ParametreItem>()
        data.add(ParametreItem("Notifications"))
        data.add(ParametreItem("Apparence"))
        data.add(ParametreItem("About"))

        return data
    }
}