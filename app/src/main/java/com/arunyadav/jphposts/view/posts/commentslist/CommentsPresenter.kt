package com.arunyadav.jphposts.view.posts.commentslist

import android.util.Log
import com.arunyadav.jphposts.JPHPostsApplication
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.server.http.NetworkModule
import com.arunyadav.jphposts.server.http.PostRepositoryImpl
import io.reactivex.disposables.CompositeDisposable

class CommentsPresenter(
    var view: CommentsContract.View,
    var repository: PostRepositoryImpl,
    var networkModule: NetworkModule
) : CommentsContract.Presenter {
    private var compositeDisposable = CompositeDisposable()

    override fun dropView() {
        compositeDisposable.dispose()
    }

    override fun fetchComments(postId: Int) {
        view.showProgress()

        val disposable = repository.postComments(postId)
            .subscribeOn(networkModule.provideThreadExecutor().io())
            .observeOn(networkModule.provideThreadExecutor().ui())
            .subscribe(
                { response ->
                    response?.let {
                        if (response.isSuccessful) {
                            val comments = response.body()
                            Log.d(TAG, "response: $comments")
                            view.showComments(comments = comments)
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