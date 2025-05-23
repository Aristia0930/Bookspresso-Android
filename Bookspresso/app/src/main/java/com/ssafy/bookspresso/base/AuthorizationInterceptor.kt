package com.ssafy.bookspresso.base

import android.util.Log
import com.ssafy.bookspresso.base.ApplicationClass
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

private const val TAG = "AuthorizationIntercepto_싸피"
class AuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = ApplicationClass.sharedPreferencesUtil.getString("Authorization")

        Log.d(TAG, "intercept: ${jwtToken}")
        if (!jwtToken.isNullOrEmpty()) {
            builder.addHeader("Authorization", "$jwtToken")
        }

        return chain.proceed(builder.build())
    }
}