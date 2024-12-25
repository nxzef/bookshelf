package com.example.bookshelf.di

import android.content.Context
import com.example.bookshelf.BuildConfig
import com.example.bookshelf.data.network.BookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL: String = "https://www.googleapis.com/books/v1/"
    private const val API_KEY: String = BuildConfig.API_KEY


    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val originalUrl = original.url

            val newUrl = originalUrl.newBuilder().addQueryParameter("key", API_KEY).build()

            val packageName = context.packageName
            val sha1Fingerprint = getSha1Fingerprint(context)

            val requestBuilder =
                original.newBuilder().url(newUrl)
                    .addHeader("X-Android-Package", packageName)
                    .addHeader("X-Android-Cert", sha1Fingerprint ?: "")
            chain.proceed(requestBuilder.build())
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideBookApiService(retrofit: Retrofit): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }

    private fun getSha1Fingerprint(context: Context): String? {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(
                context.packageName,
                android.content.pm.PackageManager.GET_SIGNING_CERTIFICATES
            )

            val signatures = packageInfo.signingInfo?.apkContentsSigners
            if (signatures.isNullOrEmpty()) {
                return null // No signatures found
            }

            val messageDigest = MessageDigest.getInstance("SHA-1")
            messageDigest.update(signatures[0].toByteArray())

            // Generate SHA-1 without colons and in uppercase
            messageDigest.digest().joinToString("") { "%02X".format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }



}