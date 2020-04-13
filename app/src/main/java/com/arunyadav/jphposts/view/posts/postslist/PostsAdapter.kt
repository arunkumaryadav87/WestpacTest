package com.arunyadav.jphposts.view.posts.postslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.server.dto.Post
import kotlinx.android.synthetic.main.post_item_layout.view.*

class PostsAdapter(var posts: List<Post>) : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    internal var clickListener: (Post) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.post_item_layout, parent, false)
        )

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(post = posts[position])

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(post: Post) {
            itemView.tvPostId.text = post.id.toString()
            itemView.tvTitle.text = post.title
            itemView.tvBody.text = post.body
            itemView.setOnClickListener { clickListener(post) }
        }
    }
}