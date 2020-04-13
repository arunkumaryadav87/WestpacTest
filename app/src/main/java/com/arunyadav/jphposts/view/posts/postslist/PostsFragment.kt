package com.arunyadav.jphposts.view.posts.postslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.base.BaseFragment
import com.arunyadav.jphposts.server.dto.Post
import com.arunyadav.jphposts.util.ErrorDialogUtil
import com.arunyadav.jphposts.view.posts.commentslist.CommentsFragment
import kotlinx.android.synthetic.main.fragment_posts_list.*

class PostsFragment : BaseFragment(), PostsFragmentContract.View {
    private lateinit var presenter: PostsFragmentPresenter
    private lateinit var adapter: PostsAdapter
    override fun getScreenName(): String? = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = PostsFragmentPresenter(this, getRepository(), getNetworkModule())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_posts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchPosts()
    }

    override fun showError(errorMessage: String) {
        ErrorDialogUtil.show(
            activity!!,
            null,
            errorMessage,
            getString(R.string.btn_ok)) { _, _ -> }
    }

    override fun showPosts(posts: List<Post>?) {
        posts?.let {
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = PostsAdapter(it)
            adapter.clickListener = { transactionHistory -> navigateToPostDetail(transactionHistory) }
            rvPostList.adapter = adapter
            rvPostList.layoutManager = linearLayoutManager
        }
    }

    private fun navigateToPostDetail(post: Post) {
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.contentFrame, CommentsFragment.newInstance(post))?.commit()
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
    }
}