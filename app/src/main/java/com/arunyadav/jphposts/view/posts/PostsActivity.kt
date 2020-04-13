package com.arunyadav.jphposts.view.posts

import android.os.Bundle
import android.util.Log
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.base.BaseActivity
import com.arunyadav.jphposts.view.posts.postslist.PostsFragment

class PostsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        navigateToPostsFragment()
    }

    override fun getScreenName(): String = TAG

    private fun navigateToPostsFragment() {
        Log.d(TAG, "navigateToPostsFragment")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contentFrame, PostsFragment())
            .commit()
    }

    companion object {
        const val TAG = "PostsActivity"
    }
}