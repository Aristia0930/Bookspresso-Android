package com.ssafy.bookspresso.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
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
import com.ssafy.bookspresso.ui.home.HomeFragment
import com.ssafy.bookspresso.ui.my.MyPageFragment
import com.ssafy.bookspresso.ui.my.OrderDetailFragment
import com.ssafy.bookspresso.ui.order.MapFragment
import com.ssafy.bookspresso.ui.order.MenuDetailFragment
import com.ssafy.bookspresso.ui.order.OrderFragment
import com.ssafy.bookspresso.ui.order.ShoppingListFragment
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "MainActivity_싸피"

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var nAdapter: NfcAdapter
    private lateinit var filters: Array<IntentFilter>
    private lateinit var pIntent: PendingIntent

    private lateinit var beaconManager: BeaconManager
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

        setBeacon()

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
                        .replace(R.id.frame_layout_main, OrderFragment())
                        .commit()
                    true
                }

                R.id.navigation_page_3 -> {
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

        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

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

    private fun setBeacon() {

    }

    override fun onStart() {
        super.onStart()
        /* permission check */
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{
                //퍼미션 획득 성공일때
                startScan()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions)
        } else { //이미 전체 권한이 있는 경우
            startScan()
        }
        /* permission check */
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
    //비콘연결

    private val region = Region(
        "estimote",
        listOf(
            Identifier.parse(BEACON_UUID),
            Identifier.parse(BEACON_MAJOR),
            Identifier.parse(BEACON_MINOR)),
        BLUETOOTH_ADDRESS
    )
    private val requestBLEActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ){
        // 사용자의 블루투스 사용이 가능한지 확인
        if (bluetoothAdapter.isEnabled) {
            startScan()
        }
    }
    private fun startScan() {
        if ( !bluetoothAdapter.isEnabled ) {
            val callBLEEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBLEActivity.launch(callBLEEnableIntent)
        }
        Log.d(TAG, "startScan: ")

        // 리전에 비컨이 있는지 없는지..정보를 받는 클래스 지정
        beaconManager.addMonitorNotifier(monitorNotifier)
        beaconManager.startMonitoring(region)

        //detacting되는 해당 region의 beacon정보를 받는 클래스 지정.
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.startRangingBeacons(region)
    }

    var monitorNotifier: MonitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region) {
            Log.d(TAG, "didEnterRegion: 비콘 접근완료")

            val today = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Date())
            val lastShownDate = ApplicationClass.sharedPreferencesUtil.getLastShownDate(this@MainActivity, "last_beacon_alert_date")
            if (lastShownDate == today) return
            if (hasShownBeaconDialog) return
            hasShownBeaconDialog = true

            ApplicationClass.sharedPreferencesUtil.saveLastShownDate(this@MainActivity, "last_beacon_alert_date", today)
            runOnUiThread {
                val dialogView = layoutInflater.inflate(R.layout.dialog_store_event, null)

                // 이미지 바꾸기 (Glide 사용 시)
                val imageView = dialogView.findViewById<ImageView>(R.id.iv_dialog_store_event_img)
                Glide.with(this@MainActivity)
                    .load(R.drawable.logo)  // 필요한 경우 URL로 교체 가능
                    .into(imageView)

                AlertDialog.Builder(this@MainActivity)
                    .setView(dialogView)
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }

        }

        override fun didExitRegion(region: Region) { //발견 못함.
            Log.d(TAG, "I no longer see an beacon")
        }

        override fun didDetermineStateForRegion(state: Int, region: Region) { //상태변경
            Log.d(TAG, "I have just switched from seeing/not seeing beacons: $state")
        }
    }

    //매초마다 해당 리전의 beacon 정보들을 collection으로 제공받아 처리한다.
    var rangeNotifier: RangeNotifier = object : RangeNotifier {
        override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
            beacons?.run{
                if (isNotEmpty()) {
                    forEach { beacon ->
                        val msg = "distance: " + beacon.distance
                        // 사정거리 내에 있을 경우 이벤트 표시 다이얼로그 팝업
                        if (beacon.distance <= BEACON_DISTANCE) {
                            Log.d(TAG, "didRangeBeaconsInRegion: distance 이내.")
                            val textView = TextView(this@MainActivity).apply{
                                text = "$msg"
                            }


                        } else {
//                            Log.d(TAG, "didRangeBeaconsInRegion: distance 이외.")
                        }
//                        Log.d( TAG,"distance: " + beacon.distance + " id:" + beacon.id1 + "/" + beacon.id2 + "/" + beacon.id3)

                    }
                }
                if (isEmpty()) {
//                    Log.d(TAG, "didRangeBeaconsInRegion: 비컨을 찾을 수 없습니다.")
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: stopScan")
        beaconManager.stopMonitoring(region)
        beaconManager.stopRangingBeacons(region)
    }


}