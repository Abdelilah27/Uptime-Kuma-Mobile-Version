package com.uptime.kuma.views.apparence

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.uptime.kuma.R
import com.uptime.kuma.databinding.FragmentApparenceReglageBinding
import com.uptime.kuma.views.mainActivity.MainActivity


class ApparenceReglageFragment : Fragment(R.layout.fragment_apparence_reglage) {

    private lateinit var binding: FragmentApparenceReglageBinding
    private var checked: Boolean = false
    private var switchListener = 0 //to undo switch after cancelling dialog alert
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApparenceReglageBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.aapparenceback.setOnClickListener {
            MainActivity.navController.navigateUp()
        }

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
                    var toast = Toast.makeText(
                        requireContext(),
                        "language has been changed successfully",
                        Toast.LENGTH_LONG
                    )
                    toast.show()

                }
                "French" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "fr")
                    MainActivity.languageSettings.language = "fr"
                    var toast = Toast.makeText(
                        requireContext(),
                        "la langue à été changé avec succés ",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
                "Anglais" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "en")
                    MainActivity.languageSettings.language = "en"
                    var toast = Toast.makeText(
                        requireContext(),
                        "language has been changed successfully",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }
                "Francais" -> {
                    MainActivity.mainActivityViewModel.setAppLocale(requireContext(), "fr")
                    MainActivity.languageSettings.language = "fr"
                    var toast = Toast.makeText(
                        requireContext(),
                        "la langue à été changé avec succés ",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                }

            }
        })
        return root
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
            MainActivity.instance.restartApplication()
        }
        view.findViewById<View>(R.id.buttonNo).setOnClickListener {
            binding.switchDarkMode.isChecked = !checked
            Log.d("bool22", binding.switchDarkMode.hasOnClickListeners().toString())
            alertDialog.dismiss()
        }
        if (alertDialog.window != null) {
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.switchDarkMode.setOnCheckedChangeListener(null)
    }

}