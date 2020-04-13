package com.arunyadav.jphposts.view.posts.postslist

import android.util.Log
import com.arunyadav.jphposts.JPHPostsApplication
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.server.http.NetworkModule
import com.arunyadav.jphposts.server.http.PostRepositoryImpl
import io.reactivex.disposables.CompositeDisposable

class PostsFragmentPresenter(
    var view: PostsFragmentContract.View,
    var repository: PostRepositoryImpl,
    var networkModule: NetworkModule
) : PostsFragmentContract.Presenter {
    private var compositeDisposable = CompositeDisposable()

    override fun dropView() {
        compositeDisposable.dispose()
    }

    override fun fetchPosts() {
        view.showProgress()

        val disposable = repository.posts()
            .subscribeOn(networkModule.provideThreadExecutor().io())
            .observeOn(networkModule.provideThreadExecutor().ui())
            .subscribe(
                { response ->
                    response?.let {
                        if (response.isSuccessful) {
                            val posts = response.body()
                            Log.d(TAG, "response: $posts")
                            view.showPosts(posts = posts)
                        } else view.showError(JPHPostsApplication.getAppContext().getString(R.string.request_failed))
                    }
                    view.hideProgress()
                },
                { error ->
                    error.printStackTrace()
                    view.showError(JPHPostsApplication.getAppContext().getString(R.string.server_error))
                    view.hideProgress()
                })

        disposable?.let(compositeDisposable::add)
    }

    companion object {
        private const val TAG = "PostsFragmentPresenter"
    }
}