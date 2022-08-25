package com.uptime.kuma.views.apparence

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentApparenceReglageBinding
import com.uptime.kuma.utils.RestartApp
import com.uptime.kuma.views.mainActivity.MainActivity


class ApparenceReglageFragment : Fragment(R.layout.fragment_apparence_reglage), RestartApp {

    private lateinit var binding: FragmentApparenceReglageBinding
    private var checked: Boolean = false
    private var switchListener = 0 //to undo switch after cancelling dialog alert
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApparenceReglageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.apply {
            switchDarkMode.isChecked = MainActivity.saveData.lightMode == "true"
            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                switchListener += 1
                if (switchListener % 2 != 0) {
                    showWarningDialog() //show dialog alert
                    checked = isChecked
                }

            }

        }

        val list = resources.getStringArray(R.array.list)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, list)
        // Inflate the layout for this fragment
        binding.auto.setAdapter(arrayAdapter)
        binding.auto.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            when (binding.auto.text.toString()) {
                "English" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "en")
                    MainActivity.languageSettings.language = "en"
                }
                "French" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "fr")
                    MainActivity.languageSettings.language = "fr"
                }
                "Anglais" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "en")
                    MainActivity.languageSettings.language = "en"
                }
                "Francais" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "fr")
                    MainActivity.languageSettings.language = "fr"
                }

            }
        })

        return root
    }

    override fun restartApplication() {
        val intent = requireActivity().baseContext.packageManager.getLaunchIntentForPackage(
            requireActivity().baseContext.packageName
        )
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    private fun showWarningDialog() {
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
        val view: View =
            layoutInflater.inflate(com.uptime.kuma.R.layout.layout_warning_dialog, null)
        builder.setView(view)
        (view.findViewById<View>(R.id.textTitle) as TextView).text =
            resources.getString(R.string.warning_title)
        (view.findViewById<View>(R.id.textMessage) as TextView).text =
            resources.getString(R.string.dummy_text)
        (view.findViewById<View>(R.id.buttonYes) as Button).text =
            resources.getString(R.string.yes)
        (view.findViewById<View>(R.id.buttonNo) as Button).text = resources.getString(R.string.no)
        (view.findViewById<View>(R.id.imageIcon) as ImageView).setImageResource(R.drawable.ic_baseline_warning_amber_24)
        val alertDialog = builder.create()
        view.findViewById<View>(R.id.buttonYes).setOnClickListener {
            if (checked) {
                MainActivity.saveData.lightMode = "true"
            } else {
                MainActivity.saveData.lightMode = "false"
            }
            restartApplication()
        }
        view.findViewById<View>(R.id.buttonNo).setOnClickListener {
            binding.switchDarkMode.isChecked = !checked
            alertDialog.dismiss()
        }
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }

}