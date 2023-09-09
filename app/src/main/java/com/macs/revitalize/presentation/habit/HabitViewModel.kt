package com.macs.revitalize.presentation.habit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.macs.revitalize.data.repository.HabitsRepositoryImpl
import com.macs.revitalize.data.repository.UsersRepositoryImpl
import com.macs.revitalize.domain.model.Habit
import java.util.*

class HabitViewModel: ViewModel() {

    private var _habitName = MutableLiveData<String>()
    val habitName: LiveData<String>
        get() = _habitName

    private var _habitDesc = MutableLiveData<String>()
    val habitDesc: LiveData<String>
        get() = _habitDesc

    private var _habitFreq = MutableLiveData<String>()
    val habitFreq: LiveData<String>
        get() = _habitFreq

    private var _habitTags = MutableLiveData<MutableList<String>>()
    val habitTags: LiveData<MutableList<String>>
        get() = _habitTags

    fun updateHabitName(name: String){
        _habitName.value = name
    }

    fun updateHabitDesc(desc: String){
        _habitDesc.value = desc
    }

    fun updateHabitFreq(freq: String){
        Log.i("HABIT", freq)
        _habitFreq.value = freq
    }

    fun addHabitTag(tag: String){
        _habitTags.value = if (_habitTags.value?.contains(tag) == false) _habitTags.value?.toMutableList()?.apply { add(tag) } else _habitTags.value?.toMutableList()
    }

    fun removeHabitTag(position: Int) {
        _habitTags.value = _habitTags.value?.toMutableList()?.apply { removeAt(position) }
    }
    init {
        Log.i("HABIT", "INIT")
        _habitTags.value = mutableListOf()
    }

    fun submitAddHabit(email: String) {
        val createdHabit = Habit(habitName.value!!, habitDesc.value!!, habitFreq.value!!, habitTags.value!!, Calendar.getInstance().time)
        Log.i("HABIT", "Adding habit ${habitName.value}")

        val addedHabitKey = HabitsRepositoryImpl().addNewHabit(createdHabit, email)
        UsersRepositoryImpl().linkHabitToUser(addedHabitKey, email)
    }



}