package com.arunyadav.jphposts.server.http

import com.arunyadav.jphposts.server.dto.Comment
import com.arunyadav.jphposts.server.dto.Post
import io.reactivex.Observable
import retrofit2.Response

interface PostRepository {
    // Return list of posts
    fun posts(): Observable<Response<List<Post>?>?>

    // Return list of comments associated with particular post
    fun postComments(postId: Int?): Observable<Response<List<Comment>?>?>
}