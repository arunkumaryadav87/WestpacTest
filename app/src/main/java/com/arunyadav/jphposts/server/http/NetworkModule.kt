package com.arunyadav.jphposts.server.http

import com.arunyadav.jphposts.JPHPostsApplication
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.server.rx.SchedulerProvider
import com.arunyadav.jphposts.server.rx.SchedulerProviderImpl
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {

    fun provideRetrofit(): Retrofit {
        val client: OkHttpClient = getOkHttpClient()
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create()) //string parser to always be on top
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(provideHTTPSBaseURL())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun provideThreadExecutor(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return try {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(ChuckInterceptor(JPHPostsApplication.getAppContext()))
                .readTimeout(
                    TIMEOUT_VALUE.toLong(),
                    TimeUnit.SECONDS
                )
            okHttpClientBuilder.build()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    private fun provideHTTPSBaseURL(): String =
        JPHPostsApplication.getAppContext().getString(R.string.base_url)

    companion object {
        private const val TAG = "NetworkModule"
        private const val TIMEOUT_VALUE = 60
    }
}