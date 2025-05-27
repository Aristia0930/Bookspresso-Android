package com.ssafy.bookspresso.base

import android.Manifest
import android.app.Application
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.bookspresso.data.local.SharedPreferencesUtil
import com.ssafy.bookspresso.data.local.TokenPreferencesUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TAG = "ApplicationClass_싸피"
class ApplicationClass : Application() {
    companion object{
        // 가맹점 초기 위치
        val STORE_LOCATION = LatLng(36.10830144233874, 128.41827450414362)

        // ipconfig를 통해 ip확인하기
        // 핸드폰으로 접속은 같은 인터넷으로 연결 되어있어야함 (유,무선)
//        const val SERVER_URL = "http://192.168.32.86:9987/"
        const val SERVER_URL = "https://port-0-bookcafe-m5al105w4c7a25c2.sel4.cloudtype.app/"
        const val MENU_IMGS_URL = "https://firebasestorage.googleapis.com/v0/b/newproject03-8726c.appspot.com/o/"
        const val BOOK_IMGS_URL = "https://firebasestorage.googleapis.com/v0/b/newproject03-8726c.appspot.com/o/"
        const val IMGS_URL = "${SERVER_URL}imgs/"
        const val GRADE_URL = "${SERVER_URL}imgs/grade/"

        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
        lateinit var tokenPreferencesUtil: TokenPreferencesUtil
        lateinit var retrofit: Retrofit

        // 모든 퍼미션 관련 배열
        val requiredPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT
        )

        // 주문 준비 완료 확인 시간 1분
        const val ORDER_COMPLETED_TIME = 60*1000

    }


    override fun onCreate() {
        super.onCreate()

        //shared preference 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)
        tokenPreferencesUtil=TokenPreferencesUtil(applicationContext)
        // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
        // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(AuthorizationInterceptor())  // 여기 추가
            .addInterceptor(ReceivedCookiesInterceptor()).build()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    //GSon은 엄격한 json type을 요구하는데, 느슨하게 하기 위한 설정. success, fail이 json이 아니라 단순 문자열로 리턴될 경우 처리..
    val gson : Gson = GsonBuilder()
        .setLenient()
        .create()
}