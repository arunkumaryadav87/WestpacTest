package com.arunyadav.jphposts.view.posts.postslist

import com.arunyadav.jphposts.base.BaseView
import com.arunyadav.jphposts.server.dto.Post

interface PostsFragmentContract {
    interface View : BaseView {
        fun showError(errorMessage: String)
        fun showPosts(posts: List<Post>?)
    }

    interface Presenter {
        fun dropView()
        fun fetchPosts()
    }
}