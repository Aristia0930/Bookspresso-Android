package com.ssafy.bookspresso.ui


import android.os.Bundle
import androidx.activity.viewModels
import com.ssafy.bookspresso.R
import com.ssafy.bookspresso.base.BaseActivity
import com.ssafy.bookspresso.databinding.ActivityManagerBinding
import com.ssafy.bookspresso.ui.manager.ManagerFragment
import com.ssafy.bookspresso.ui.manager.ManagerViewModel

class ManagerActivity : BaseActivity<ActivityManagerBinding>(ActivityManagerBinding::inflate) {

    private val mainActivityViewModel: ManagerViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_manager, ManagerFragment())
            .commit()
    }



}