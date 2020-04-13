package com.arunyadav.jphposts.base

import androidx.fragment.app.Fragment
import com.arunyadav.jphposts.JPHPostsApplication
import com.arunyadav.jphposts.server.http.NetworkModule
import com.arunyadav.jphposts.server.http.PostRepositoryImpl

abstract class BaseFragment : Fragment() {
    abstract fun getScreenName(): String?

    fun getNetworkModule(): NetworkModule {
        return (activity?.application as JPHPostsApplication).getNetworkModule()
    }

    fun getRepository(): PostRepositoryImpl {
        return (activity?.application as JPHPostsApplication).getRepository()
    }
}