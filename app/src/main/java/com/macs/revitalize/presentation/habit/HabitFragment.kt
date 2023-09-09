package com.macs.revitalize.presentation.habit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calenderappdemo.adapter.MyGridAdapter
import com.macs.revitalize.MainActivity
import com.macs.revitalize.R
import com.macs.revitalize.databinding.ActivityMainBinding
import com.macs.revitalize.databinding.FragmentHabitBinding
import kotlinx.android.synthetic.main.fragment_habit.*

class HabitFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentHabitBinding?= null
    private val binding: FragmentHabitBinding
        get() = _binding!!

    private lateinit var tagsAdapter: TagsAdapter

    private val viewModel: HabitViewModel by lazy {
        ViewModelProvider(this)[HabitViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHabitBinding.inflate(inflater, container, false)

        // Get logged in user email from activity
        val userEmail = (activity as MainActivity).userEmail

        // Setting up tags recycler view
        tagsAdapter = viewModel.habitTags.value?.let { TagsAdapter(it) }!!

        val tagRecyclerView = binding.tagRecyclerView
        tagRecyclerView.adapter = tagsAdapter
        tagsAdapter.setOnItemClickListener(object: TagsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel.removeHabitTag(position)
                tagsAdapter.addNewItem(viewModel.habitTags.value!!)
            }

        })

        // Setting up frequency spinner adapter
        val spinner: Spinner = binding.frequencySpinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.frequency,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
        spinner.onItemSelectedListener = this

        // Setting up tags spinner adapter
        val tagsSpinner: Spinner = binding.tagsSpinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.tags,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tagsSpinner.adapter = adapter
            }
        }
        tagsSpinner.onItemSelectedListener = this

        // Setting up add habit listener
        binding.addHabitButton.setOnClickListener {
            val habitName = binding.editTextHabitName.text.toString()
            val habitDesc = binding.editTextHabitDesc.text.toString()

            viewModel.updateHabitDesc(habitDesc)
            viewModel.updateHabitName(habitName)

            viewModel.submitAddHabit(userEmail)
            Toast.makeText(context, "Add habit succeeded", Toast.LENGTH_SHORT).show()
            this.findNavController().navigate(HabitFragmentDirections.actionHabitFragmentToCalendarFragment())
        }
        return binding.root
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            when (p0.id){
                R.id.frequencySpinner -> viewModel.updateHabitFreq(p0.getItemAtPosition(p2) as String)
                R.id.tagsSpinner -> {
                    viewModel.addHabitTag(p0.getItemAtPosition(p2) as String)
                    viewModel.habitTags.value?.let { tagsAdapter.addNewItem(it) }
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
    }

}