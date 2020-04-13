package com.arunyadav.jphposts.view.posts.commentslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.base.BaseFragment
import com.arunyadav.jphposts.server.dto.Comment
import com.arunyadav.jphposts.server.dto.Post
import com.arunyadav.jphposts.util.ErrorDialogUtil
import kotlinx.android.synthetic.main.fragment_post_details.*

class CommentsFragment : BaseFragment(), CommentsContract.View {
    private lateinit var presenter: CommentsPresenter
    private lateinit var adapter: CommentsAdapter
    private lateinit var selectedPost: Post;
    override fun getScreenName(): String? = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedPost = it.getParcelable<Post>(POST_OBJECT) as Post
        }

        presenter = CommentsPresenter(this, getRepository(), getNetworkModule())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    fun initializeViews() {
        tvPostTitle.text = selectedPost.title
        tvPostBody.text = selectedPost.body
        presenter.fetchComments(selectedPost.id)
    }

    override fun showError(errorMessage: String) {
        ErrorDialogUtil.show(
            activity!!,
            null,
            errorMessage,
            getString(R.string.btn_ok)) { _, _ -> }
    }

    override fun showComments(comments: List<Comment>?) {
        comments?.let {
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = CommentsAdapter(it)
            rvCommentList.adapter = adapter
            rvCommentList.layoutManager = linearLayoutManager
        }
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    companion object {
        private const val TAG = "PostsFragment"
        const val POST_OBJECT = "POST_OBJECT"

        fun newInstance(post: Post) : CommentsFragment {
            val fragment = CommentsFragment()
            val bundle = Bundle()
            bundle.putParcelable(POST_OBJECT, post)
            fragment.arguments = bundle
            return fragment
        }
    }
}