package com.macs.revitalize.data.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.domain.model.Habit
import com.macs.revitalize.domain.repository.Habits
import com.macs.revitalize.domain.repository.HabitsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitsRepositoryImpl(): HabitsRepository{

    private var habitsRef: CollectionReference = Firebase.firestore.collection("habits")

    override fun addNewHabit(habit: Habit, user: String): String {
        val habitsRef = habitsRef.document()
        var statusString = ""
        val dbHabit = hashMapOf(
            "name" to habit.name,
            "freq" to habit.freq,
            "desc" to habit.desc,
            "user" to user,
            "tags" to habit.tags,
            "startDate" to habit.startDate
        )

        habitsRef.set(dbHabit).addOnSuccessListener { statusString = "success" }
            .addOnFailureListener { statusString = "failure" }

        Log.i("HABIT", statusString)
        return habitsRef.id
    }

    override fun getHabitsForUser(user: String): Habits {
        TODO("Not yet implemented")
    }

}