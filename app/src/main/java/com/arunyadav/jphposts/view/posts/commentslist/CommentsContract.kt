package com.arunyadav.jphposts.view.posts.commentslist

import com.arunyadav.jphposts.base.BaseView
import com.arunyadav.jphposts.server.dto.Comment

interface CommentsContract {
    interface View : BaseView {
        fun showError(errorMessage: String)
        fun showComments(comments: List<Comment>?)
    }

    interface Presenter {
        fun dropView()
        fun fetchComments(postId: Int)
    }
}