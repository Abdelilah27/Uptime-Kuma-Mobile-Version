package com.uptime.kuma.views.parametre

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

        Log.d("Parametre Name",getData()[position].paramname)
        when (getData()[position].paramname) {
            "About" -> findNavController().navigate(R.id.action_parametreFragment_to_aboutFragment)
            "Notifications" -> findNavController().navigate(R.id.action_parametreFragment_to_notificationsFragment)
            "Apparence" -> findNavController().navigate(R.id.action_parametreFragment_to_apparenceReglageFragment)

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