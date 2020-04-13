package com.arunyadav.jphposts.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.arunyadav.jphposts.JPHPostsApplication
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.base.BaseActivity
import com.arunyadav.jphposts.view.posts.PostsActivity

class SplashActivity : BaseActivity() {
    override fun getScreenName(): String = TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (JPHPostsApplication.isInternetConnected()) {
            proceedToLaunchApp()
        } else {
            Toast.makeText(
                applicationContext
                , getString(R.string.no_internet_connection)
                , Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun proceedToLaunchApp() {
        Handler().postDelayed({
            val nextScreen = Intent(applicationContext, PostsActivity::class.java)
            startActivity(nextScreen)
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
            finish()
        }, 3000L)
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}