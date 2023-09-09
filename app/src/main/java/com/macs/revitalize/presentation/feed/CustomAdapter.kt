package com.macs.revitalize.presentation.feed

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.macs.revitalize.R


internal class CustomAdapter(

    private val mList: List<FeedViewModel>)

//    private var itemsList1: List<String>, private var itemsList2: List<String>)
:
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageViewPost: ImageView = view.findViewById(R.id.imageView)
        var imageViewLikeButton: ImageView = view.findViewById(R.id.imageView3)
        var imageViewCommentButton: ImageView = view.findViewById(R.id.imageView4)
        var linearLayoutHorizontal2 = view.findViewById<LinearLayout>(R.id.linearLayoutHorizontal2)
        var button: Button = view.findViewById(R.id.buttonPostComment)
        var commentText: TextView = view.findViewById(R.id.textViewComment1)
        var newCommentText: EditText = view.findViewById(R.id.editTextComment)
//        var commentRecycler = view.findViewById<RecyclerView>(R.id.recyclerViewComments)
//


//        var itemTextView2: TextView = view.findViewById(R.id.editTextTextMultiLine)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_followers_post, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val viewPool = RecycledViewPool()
        var isCommentsVisible = false



                    var p: FeedViewModel = mList[position]
                    val item1 = mList[position]
                    holder.imageViewPost.setImageResource(item1.image)

                    holder.button.setOnClickListener {
                        Toast.makeText(holder.button.context, "comment posted", Toast.LENGTH_SHORT)
                            .show()
                        holder.linearLayoutHorizontal2.visibility = View.INVISIBLE
                        holder.commentText.text = holder.newCommentText.text


                        isCommentsVisible = false
                    }

                    var isLiked = false
                    holder.imageViewLikeButton.setOnClickListener {
                        if (!isLiked) {
                            holder.imageViewLikeButton.setColorFilter(Color.parseColor("#EE116F"))
                            isLiked = true
                        } else {
                            holder.imageViewLikeButton.setColorFilter(Color.parseColor("#808080"))
                            isLiked = false
                        }

                    }



                    holder.imageViewCommentButton.setOnClickListener {
                        if (!isCommentsVisible) {
                            holder.linearLayoutHorizontal2.visibility = View.VISIBLE
                        }

                    }
                }
            





//         return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

        // onClick Listener for view
        fun onClick(v: View) {

            Toast.makeText(
                v.context,
                "comment posted ",
                Toast.LENGTH_SHORT
            ).show()


        }

    }



