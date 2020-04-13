package com.arunyadav.jphposts.view.posts.commentslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.server.dto.Comment
import kotlinx.android.synthetic.main.comment_item_layout.view.*

class CommentsAdapter(var comments: List<Comment>) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(comment: Comment) {
            itemView.tvBody.text = comment.body
            itemView.tvEmail.text = comment.email
        }
    }
}