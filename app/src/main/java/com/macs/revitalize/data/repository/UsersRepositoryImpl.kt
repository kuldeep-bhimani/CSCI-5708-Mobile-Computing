package com.macs.revitalize.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.domain.repository.UsersRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl (): UsersRepository{
    private val usersRef: CollectionReference = Firebase.firestore.collection("users")
    override fun linkHabitToUser(habitKey: String, user: String) {
        usersRef.document(user).update("habits", FieldValue.arrayUnion(habitKey))
    }

    override fun addAuthUser(email: String): String {
        var statusString = ""
        val dbUser = hashMapOf(
            "email" to email,
            "subscribedTo" to ArrayList<String>(),
            "habits" to ArrayList<String>(),
            "achievements" to ArrayList<String>()
        )

        usersRef.document(email)
            .set(dbUser)
            .addOnSuccessListener { statusString = "success" }
            .addOnFailureListener { statusString = "failure" }

        return statusString
    }
}