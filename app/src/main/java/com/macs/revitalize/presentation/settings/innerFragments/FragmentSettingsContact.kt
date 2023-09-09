package com.macs.revitalize.presentation.settings.innerFragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.macs.revitalize.R
import com.macs.revitalize.databinding.FragmentSettingsContactBinding

class FragmentSettingsContact : Fragment() {
    private var _binding: FragmentSettingsContactBinding?= null
    private val binding: FragmentSettingsContactBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsContactBinding.inflate(inflater, container, false)


        binding.settingsContactGoBack.setOnClickListener {
            this.findNavController().navigate(FragmentSettingsContactDirections.actionFragmentSettingsContactToSettingsFragment())
        }

        val addresses: Array<String> = arrayOf("qurram.zaheer@gmail.com")
        binding.sendEmailLink.setOnClickListener{
            Log.i("CONTACT", "CLICKED EMAIL CONTACT")
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, addresses)
                putExtra(Intent.EXTRA_SUBJECT, "")
            }

            startActivity(intent)

        }

        return binding.root
    }

}