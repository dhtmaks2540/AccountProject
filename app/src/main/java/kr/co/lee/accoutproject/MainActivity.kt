package kr.co.lee.accoutproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.utilities.PageType
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel 생성
    private val mainViewModel: MainViewModel by viewModels()
    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when(it.resultCode) {
                RESULT_OK -> {
                    mainViewModel.setAccount()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding 레이아웃 초기화 및 데이터 셋팅
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            viewModel = mainViewModel

            // Prev, Next Button
            ivPrev.setOnClickListener {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fragment?.tag == "Month") {
                    (fragment as MonthFragment).prevButtonClick()
                } else if (fragment?.tag == "Week") {
                    (fragment as WeekFragment).prevButtonClick()
                }
            }

            ivNext.setOnClickListener {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fragment?.tag == "Month") {
                    (fragment as MonthFragment).nextButtonClick()
                } else if (fragment?.tag == "Week") {
                    (fragment as WeekFragment).nextButtonClick()
                }
            }

            // Floating Button
            addButton.setOnClickListener { actionButtonClicked() }

            // Bottom NavigationView
//            mainBottomMenu.run {
//                menuItemSelected()
//                selectedItemId = R.id.action_month
//            }
        }

        subscribeUi()

        setContentView(binding.root)
    }

    // BottomNavigationView 선택 리스너
//    private fun menuItemSelected() {
//        binding.mainBottomMenu.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.action_month -> {
//                    supportFragmentManager.commit {
//                        replace(R.id.fragment_container, MonthFragment(), "Month")
//                    }
//                }
//                R.id.action_week -> {
//                    supportFragmentManager.commit {
//                        replace(R.id.fragment_container, WeekFragment(), "Week")
//                    }
//                }
//            }
//            true
//        }
//    }

    // FloatingActionButton 이벤트 리스너
    private fun actionButtonClicked() {
        val addIntent = Intent(this, AddActivity::class.java)
        addIntent.putExtra("date", mainViewModel.date.value?.toString("yyyy/MM/dd"))
        launcher.launch(addIntent)
    }

    private fun subscribeUi() {
        mainViewModel.currentPageType.observe(this) {
            changeFragment(it)
        }
    }

    private fun changeFragment(pageType: PageType) {
        val transaction = supportFragmentManager.beginTransaction()
        var targetFragment = supportFragmentManager.findFragmentByTag(pageType.tag)

        println("$pageType, ${targetFragment.toString()}")

        if(targetFragment == null) {
            targetFragment = getFragment(pageType)
            transaction.add(R.id.fragment_container, targetFragment!!, pageType.tag)
        }

        transaction.show(targetFragment)

        PageType.values()
            .filterNot { it == pageType }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.tag)?.let {
                    transaction.hide(it)
                }
            }

        transaction.commitAllowingStateLoss()

//        supportFragmentManager.commit {
//            if(targetFragment == null) {
//                targetFragment = getFragment(pageType)
//                add(R.id.fragment_container, targetFragment!!, pageType.tag)
//            }
//
//            show(targetFragment!!)
//
//            PageType.values()
//                .filterNot { it == pageType }
//                .forEach { type ->
//                    supportFragmentManager.findFragmentByTag(type.tag)?.let {
//                        hide(it)
//                    }
//                }
//        }
    }

    private fun getFragment(pageType: PageType): Fragment? {
        return when(pageType.tag) {
            "month" -> {
                MonthFragment.newInstance(pageType.title)
            }
            "week" -> {
                WeekFragment.newInstance(pageType.title)
            }
            else -> null
        }
    }
}