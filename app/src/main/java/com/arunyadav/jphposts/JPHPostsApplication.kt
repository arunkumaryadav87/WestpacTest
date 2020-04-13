package com.arunyadav.jphposts

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.arunyadav.jphposts.server.http.NetworkModule
import com.arunyadav.jphposts.server.http.PostRepositoryImpl
import retrofit2.Retrofit

class JPHPostsApplication : Application() {
    private lateinit var module: NetworkModule

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        module = NetworkModule()
    }

    fun getNetworkModule() : NetworkModule = module

    fun getRepository() : PostRepositoryImpl {
        return PostRepositoryImpl(module.provideRetrofit())
    }

    companion object {
        private lateinit var appContext: Context
        fun getAppContext(): Context = appContext
        fun isInternetConnected(): Boolean {
            val connectivityManager =
                appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capability =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
    }
}