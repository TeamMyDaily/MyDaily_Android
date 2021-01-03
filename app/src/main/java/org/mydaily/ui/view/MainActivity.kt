package org.mydaily.ui.view

import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mydaily.R
import org.mydaily.databinding.ActivityMainBinding
import org.mydaily.util.extension.replace
import org.mydaily.ui.base.BaseActivity
import org.mydaily.ui.view.daily.DailyFragment
import org.mydaily.ui.view.mypage.MyPageFragment
import org.mydaily.ui.view.remind.RemindFragment
import org.mydaily.ui.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    private val dailyFragment: DailyFragment by lazy { DailyFragment() }
    private val remindFragment: RemindFragment by lazy { RemindFragment() }
    private val myFragment: MyPageFragment by lazy { MyPageFragment() }

    override fun initView() {
        initBottomNavigation()
    }

    override fun initBeforeBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun initBottomNavigation() {
        setSupportActionBar(binding.tbMain)
        replaceDailyFragment()

        binding.bnvMain.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_daily -> {
                    replaceDailyFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_remind -> {
                    replaceRemindFragment()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_my -> {
                    replaceMyPageFragment()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun replaceDailyFragment() {
        replace(R.id.container_main, dailyFragment)
        supportActionBar?.title = getString(R.string.menu_daily)
    }

    private fun replaceRemindFragment() {
        replace(R.id.container_main, remindFragment)
        supportActionBar?.title = getString(R.string.menu_remind)
    }

    private fun replaceMyPageFragment() {
        replace(R.id.container_main, myFragment)
        supportActionBar?.title = getString(R.string.menu_my)
    }

}