package com.macs.revitalize.presentation.feed

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.R

internal class CustomUserPostAdapter (private val mList: List<FeedViewModel>)
    :
    RecyclerView.Adapter<CustomUserPostAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageViewPost: ImageView = view.findViewById(R.id.imageView5)
        var imageViewLikeButton: ImageView = view.findViewById(R.id.imageView6)
        var imageViewCommentButton: ImageView = view.findViewById(R.id.imageView7)
        var linearLayoutHorizontal2 = view.findViewById<LinearLayout>(R.id.linearLayout2Horizontal2)
        var button: Button = view.findViewById(R.id.buttonPostComment2)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user_post, parent, false)

        return MyViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val viewPool = RecyclerView.RecycledViewPool()
        var isCommentsVisible = false
        println("post2")


        val item1 = mList[position]
        holder.imageViewPost.setImageResource(item1.image)

        holder.button.setOnClickListener {
            Toast.makeText(holder.button.context, "comment posted", Toast.LENGTH_SHORT)
                .show()
            holder.linearLayoutHorizontal2.visibility = View.INVISIBLE



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


    override fun getItemCount(): Int {

        return mList.size
    }

}