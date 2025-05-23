package com.ssafy.bookspresso.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Region
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.ApplicationClass
import com.ssafy.bookspresso.base.BaseActivity
import com.ssafy.bookspresso.databinding.ActivityMainBinding
import com.ssafy.bookspresso.ui.cafe.CafeFragment
import com.ssafy.bookspresso.ui.home.HomeFragment
import com.ssafy.bookspresso.ui.my.MyPageFragment
import com.ssafy.bookspresso.ui.my.OrderDetailFragment
import com.ssafy.bookspresso.ui.order.MapFragment
import com.ssafy.bookspresso.ui.order.MenuDetailFragment
import com.ssafy.bookspresso.ui.order.OrderFragment
import com.ssafy.bookspresso.ui.order.ShoppingListFragment
import org.intellij.lang.annotations.Identifier
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "MainActivity_싸피"

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var nAdapter: NfcAdapter
    private lateinit var filters: Array<IntentFilter>
    private lateinit var pIntent: PendingIntent

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var hasShownBeaconDialog = false

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: 호출")
        val fragment =
            supportFragmentManager.findFragmentById(R.id.frame_layout_main)

        if (fragment is ShoppingListFragment) {
            intent?.let {
                if (it.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {

                    val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                    val msgs = rawMsgs?.map { it as NdefMessage } ?: listOf()
                    for (msg in msgs) {
                        for (record in msg.records) {
                            val payload = record.payload
                            val data = String(payload, Charsets.UTF_8).substring(3) // 첫 3바이트는 언어코드

                            Log.d(TAG, "onNewIntent: $data")
                            fragment.onNfcScanned(data)
                        }
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout_main, OrderFragment())
                            .commit()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nAdapter.enableForegroundDispatch(this, pIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nAdapter.disableForegroundDispatch(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nAdapter = NfcAdapter.getDefaultAdapter(this)
        val nfcIntent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pIntent = PendingIntent.getActivity(this, 0, nfcIntent, PendingIntent.FLAG_MUTABLE)
        val ndefFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        ndefFilter.addDataType("text/plain")  // 이런 데이터 타입이 들어오면 내가 할거다.
        filters = arrayOf(ndefFilter)

        // 가장 첫 화면은 홈 화면의 Fragment로 지정

        setNdef()

        createNotificationChannel("ssafy_channel", "ssafy")

        checkPermissions()


        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_main, HomeFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_page_1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, HomeFragment())
                        .commit()
                    true
                }

                R.id.navigation_page_2 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, CafeFragment())
                        .commit()
                    true
                }

                R.id.navigation_page_3 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, OrderFragment())
                        .commit()
                    true
                }

                R.id.navigation_page_4 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_main, MyPageFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            // 재선택시 다시 랜더링 하지 않기 위해 수정
            if (binding.bottomNavigation.selectedItemId != item.itemId) {
                binding.bottomNavigation.selectedItemId = item.itemId
            }
        }


        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    fun openFragment(index: Int, key: String, value: Int) {
        moveFragment(index, key, value)
    }

    fun openFragment(index: Int) {
        moveFragment(index, "", 0)
    }

    private fun moveFragment(index: Int, key: String, value: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (index) {
            //장바구니
            1 -> {
                // 재주문
                if (key.isNotBlank() && value != 0) {
                    transaction.replace(
                        R.id.frame_layout_main,
                        ShoppingListFragment.newInstance(value)
                    )
                        .addToBackStack(null)
                } else {
                    transaction.replace(
                        R.id.frame_layout_main,
                        ShoppingListFragment()
                    )
                        .addToBackStack(null)
                }
            }
            //주문 상세 보기
            2 -> transaction.replace(R.id.frame_layout_main, OrderDetailFragment())
                .addToBackStack(null)
            //메뉴 상세 보기
            3 -> transaction.replace(R.id.frame_layout_main, MenuDetailFragment())
                .addToBackStack(null)
            //map으로 가기
            4 -> transaction.replace(R.id.frame_layout_main, MapFragment())
                .addToBackStack(null)
            //logout
            5 -> {
                logout()
            }
        }
        transaction.commit()
    }

    private fun logout() {
        // 로그 아웃.
        ApplicationClass.sharedPreferencesUtil.deleteUser()
        ApplicationClass.sharedPreferencesUtil.deleteUserCookie()

        // 화면이동.
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)

    }

    fun hideBottomNav(state: Boolean) {
        if (state) binding.bottomNavigation.visibility = View.GONE
        else binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun setNdef() {
        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            // app 실행
        }

    }

    // NotificationChannel 설정
    private fun createNotificationChannel(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)

            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    companion object {
        private const val BEACON_UUID = "e2c56db5-dffb-48d2-b060-d0f5a71096e0" // 4반 모두 동일값
        private const val BEACON_MAJOR = "40011" // 4반 모두 동일값
        private const val BEACON_MINOR = "43419" // 4반 모두 동일값
        private const val BLUETOOTH_ADDRESS = "C3:00:00:1C:5E:7A"

//        private const val BEACON_UUID = "fda50693-a4e2-4fb1-afcf-c6eb07647825" // 5반 모두 동일값
//        private const val BEACON_MAJOR = "10004" // 5반 모두 동일값
//        private const val BEACON_MINOR = "54480" // 5반 모두 동일값
//        private const val BLUETOOTH_ADDRESS = "00:81:F9:44:39:58"

        private const val BEACON_DISTANCE = 5.0 // 거리
    }

    private fun checkPermissions() {

    }

    private val checker = com.ssafy.bookspresso.util.PermissionChecker(this)
    private val runtimePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

}