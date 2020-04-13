package com.arunyadav.jphposts.server.http

import com.arunyadav.jphposts.server.dto.Comment
import com.arunyadav.jphposts.server.dto.Post
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit

class PostRepositoryImpl(retrofit: Retrofit) : PostRepository {
    private var postApi: PostApi = retrofit.create<PostApi>(PostApi::class.java)

    override fun posts(): Observable<Response<List<Post>?>?> = postApi.posts()

    override fun postComments(postId: Int?): Observable<Response<List<Comment>?>?> =
        postApi.postComments(postId)
}