package com.macs.revitalize.presentation.feed


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.macs.revitalize.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//


        // Inflate the layout for this fragment
//        val feedViewModel =
//            ViewModelProvider(this).get(FeedViewModel::class.java)

        val view: View = inflater.inflate(R.layout.fragment_feed, container, false)

//                    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//                    recyclerView.layoutManager = LinearLayoutManager(activity)
//
//                    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
//                    recyclerView.layoutManager = layoutManager
//                    val data = ArrayList<FeedViewModel>()
//                    val adapter = CustomAdapter(data)
//                    recyclerView.adapter = adapter
//
//
//
//                    for (i in 1..3) {
//
//                        data.add(FeedViewModel(R.drawable.ic_social_feed, "Item " + i))
//                        println(data)
//                    }
        val spinnerOption = view.findViewById<Spinner>(R.id.spinner)
        spinnerOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                print("onItemSelected position = $position id = $id")
                var option = spinnerOption.selectedItem.toString()
                if (option == "Your posts") {
                    val bundle = Bundle()
                    println("your posts")


//                    val view2: View = inflater.inflate(R.layout.fragment_feed, container, false)
//
//                    val recyclerView2: RecyclerView = view2.findViewById(R.id.recyclerView)
//                    recyclerView2.layoutManager = LinearLayoutManager(activity)
//
//                    val layoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(context)
//                    recyclerView2.layoutManager = layoutManager2
//
//                    val data2 = ArrayList<FeedViewModel>()
//                    val adapter2 = CustomUserPostAdapter(data2)
//                    recyclerView2.adapter = adapter2
//
//
//
//                    for (i in 1..4) {
//
//                        data2.add(FeedViewModel(R.drawable.ic_social_feed, "Item " + i))
//                        println(data2)
//                    }
//                    println("userpost item "+adapter2.itemCount)
                    val fragment = UserPostFragment()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.linearLayoutRecyler, fragment)
                    transaction.addToBackStack(null)
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    transaction.commit()
//                    this.supportFragmentManager.beginTransaction().replace(R.id.linearLayoutRecyler, CreatePostFragment()).addToBackStack(null).setTransition(
//                        FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
                } else {
                    println("following")
                    val fragment = FollowersPostFragment()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.linearLayoutRecyler, fragment)
                    transaction.addToBackStack(null)
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    transaction.commit()



//
//                    val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//                    recyclerView.layoutManager = LinearLayoutManager(activity)
//
//                    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
//                    recyclerView.layoutManager = layoutManager
////        recyclerView.setHasFixedSize(true)
//                    val data = ArrayList<FeedViewModel>()
//                    val adapter = CustomAdapter(data)
//                    recyclerView.adapter = adapter
//
//
//
//                    for (i in 1..3) {
//
//                        data.add(FeedViewModel(R.drawable.ic_social_feed, "Item " + i))
//                        println(data)
//                    }
//                    println("follower item "+adapter.itemCount)
//                    val fragment = FeedFragment()
//                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
//                    transaction.replace(R.id.linearLayoutRecyler, fragment)
//                    transaction.addToBackStack(null)
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                    transaction.commit()

//
//

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }
        val createPostButton: FloatingActionButton= view.findViewById(R.id.createPost)
        createPostButton.setOnClickListener{
//            Fragment frag = CreatePostFragment()
            println("bb")
          this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.linearLayoutRecyler, CreatePostFragment()).addToBackStack(null).setTransition(
              FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
//            (view.context as FragmentActivity).fragmentManager.beginTransaction()
//                .replace(com.macs.revitalize.R.id.feedFragment, frag)
//                .commit()
//            startActivity(Intent(context,CreatePostFragment::class.java))
        }



        return view
    }
}