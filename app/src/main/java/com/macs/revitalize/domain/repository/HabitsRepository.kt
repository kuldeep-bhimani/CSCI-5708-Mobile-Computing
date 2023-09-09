package com.macs.revitalize.domain.repository

import com.macs.revitalize.domain.model.Habit

typealias Habits = List<Habit>

interface HabitsRepository {

    fun addNewHabit(habit: Habit, user: String): String

    fun getHabitsForUser(user: String): Habits
}