package com.macs.revitalize.presentation.reports

import android.content.ClipData
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.MainActivity
import com.macs.revitalize.R


class Achievements : Fragment() {

    public lateinit var adapter : RecyclerAdapter
    public lateinit var recyclerView: RecyclerView
    private val itemsList = ArrayList<HabitAchievement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_achievements2, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)

        val db = Firebase.firestore

        val habitsRef = db.collection("habits")

        lateinit var description:String
        val userEmail = (activity as MainActivity).userEmail
        habitsRef.addSnapshotListener { habitDocuments, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {

                Log.e("FIRESTORE", "Cards listener error.", firebaseFirestoreException)
                return@addSnapshotListener
            }
                val items = mutableListOf<ClipData.Item>()
            habitDocuments!!.documents.forEach{
                if(userEmail==it.get("user") as String){
                    val myAchievement6: HabitAchievement = HabitAchievement(it.get("desc") as String,
                        it.get("name") as String, 0)
                    itemsList.add(myAchievement6)
                    Log.d(ContentValues.TAG, "ITEM LENGTH : ${it.get("desc") as String} ")
                    Log.d(ContentValues.TAG, "ITEM LENGTH : ${it.get("name") as String} ")
                    Log.d(ContentValues.TAG, "ITEM LENGTH : ${itemsList.size} ")

                }



            }
            val layoutManager = LinearLayoutManager(context)
            val recyclerAdapter = RecyclerAdapter(itemsList)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = recyclerAdapter

        }






        val myAchievement:HabitAchievement= HabitAchievement("Finish Cycling","Successfully Completed",50)

        val myAchievement2:HabitAchievement= HabitAchievement("Drink Water","Not Completed",100)



        itemsList.add(myAchievement)
        itemsList.add(myAchievement2)




        return view

    }

}

