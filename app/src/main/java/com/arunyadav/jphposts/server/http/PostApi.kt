package com.arunyadav.jphposts.server.http

import com.arunyadav.jphposts.server.dto.Comment
import com.arunyadav.jphposts.server.dto.Post
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    // Return list of posts
    @GET("posts")
    fun posts(): Observable<Response<List<Post>?>?>

    // Return list of comments associated with particular post
    @GET("posts/{postId}/comments")
    fun postComments(@Path("postId") postId: Int?): Observable<Response<List<Comment>?>?>
}

