package com.arunyadav.jphposts.server.http

import com.arunyadav.jphposts.JPHPostsApplication
import com.arunyadav.jphposts.R
import com.arunyadav.jphposts.server.rx.SchedulerProvider
import com.arunyadav.jphposts.server.rx.SchedulerProviderImpl
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.*
import okhttp3.CacheControl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {
    fun provideRetrofit(): Retrofit {
        val client = getOkHttpClient()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(provideHTTPSBaseURL())
            .client(client)
            .build()
    }

    fun provideThreadExecutor(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val cacheSize = (1 * 1024 * 1024).toLong()
        val appCache = Cache(JPHPostsApplication.getAppContext().cacheDir, cacheSize)
        return try {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)
                .cache(appCache)
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

    private val cacheInterceptor = Interceptor { chain: Interceptor.Chain ->
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.MINUTES) // online caching
            .maxStale(5, TimeUnit.MINUTES)  // Offline caching
            .build()
        response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }

    private fun provideHTTPSBaseURL(): String =
        JPHPostsApplication.getAppContext().getString(R.string.base_url)

    companion object {
        private const val TAG = "NetworkModule"
        private const val HEADER_PRAGMA = "pragma"
        private const val HEADER_CACHE_CONTROL = "cache-control"
        private const val TIMEOUT_VALUE = 60
    }
}