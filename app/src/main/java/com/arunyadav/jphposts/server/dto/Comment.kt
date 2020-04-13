package com.arunyadav.jphposts.server.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    var postId: Int,
    var id: Int,
    var name: String,
    var email: String,
    var body: String
) : Parcelable