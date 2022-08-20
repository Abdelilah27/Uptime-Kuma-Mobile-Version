package com.uptime.kuma.views.parametre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uptime.kuma.R
import com.uptime.kuma.api.ConnexionLifecycle
import com.uptime.kuma.databinding.FragmentParametreBinding
import com.uptime.kuma.models.ParametreItem
import com.uptime.kuma.utils.SessionManagement
import com.uptime.kuma.views.adapters.ParametreRecyclerAdapter
import com.uptime.kuma.views.mainActivity.MainActivity

class ParametreFragment : Fragment(R.layout.fragment_parametre),
    ParametreRecyclerAdapter.OnItemClickListener {
    lateinit var sessionManagement: SessionManagement
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentParametreBinding.bind(view)

        val parametreRecyclerAdapter = ParametreRecyclerAdapter(requireContext(), this)

        sessionManagement = SessionManagement(requireContext())
        binding.apply {
            parametreRecycler.apply {
                adapter = parametreRecyclerAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                parametreRecyclerAdapter.submitList(getData())
            }
            buttonLogout.setOnClickListener {
                sessionManagement.logOut()
                //close connexion
                ConnexionLifecycle.closeConnexion()
                MainActivity.navController.navigate(R.id.loginFragment)
            }
        }
    }

    override fun onItemClick(position: Int) {
        when (getData()[position].paramname) {
            "About" -> MainActivity.navController.navigate(R.id.aboutFragment)
            "Apparence" -> MainActivity.navController.navigate(R.id.apparenceReglageFragment)

        }
    }

    private fun getData(): List<ParametreItem> {
        val data = arrayListOf<ParametreItem>()
        data.add(ParametreItem("Apparence"))
        data.add(ParametreItem("About"))

        return data
    }
}