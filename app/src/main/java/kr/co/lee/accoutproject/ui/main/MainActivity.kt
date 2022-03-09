package kr.co.lee.accoutproject.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.ui.add.AddActivity
import kr.co.lee.accoutproject.ui.month.MonthFragment
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.base.BaseActivity
import kr.co.lee.accoutproject.ui.week.WeekFragment
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.utilities.PageType

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when (it.resultCode) {
                RESULT_OK -> {
                    mainViewModel.setAccount()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            viewModel = mainViewModel

            // Prev, Next Button
            ivPrev.setOnClickListener {
                val fragment = supportFragmentManager.fragments[0]
                if (fragment is MonthFragment) {
                    fragment.prevButtonClick()
                } else if (fragment is WeekFragment) {
                    fragment.prevButtonClick()
                }
            }
            ivNext.setOnClickListener {
                val fragment = supportFragmentManager.fragments[0]
                if (fragment is MonthFragment) {
                    fragment.nextButtonClick()
                } else if (fragment is WeekFragment) {
                    fragment.nextButtonClick()
                }
            }

            // Floating Button
            btnAdd.setOnClickListener { actionButtonClicked() }
        }

        subscribeUi()
    }

    // FloatingActionButton 이벤트 리스너
    private fun actionButtonClicked() {
        val addIntent = Intent(this, AddActivity::class.java)
        addIntent.putExtra("date", mainViewModel.date.value?.toString("yyyy/MM/dd"))
        launcher.launch(addIntent)
    }

    // LiveData observe 등록
    private fun subscribeUi() {
        mainViewModel.currentPageType.observe(this) {
            changeFragment(it)
        }
    }

    // Fragment 변경
    private fun changeFragment(pageType: PageType) {
        var targetFragment = supportFragmentManager.findFragmentByTag(pageType.tag)

        supportFragmentManager.commit {
            if (targetFragment == null) {
                targetFragment = getFragment(pageType)
                add(R.id.fcv_main, targetFragment!!, pageType.tag)
            }

            show(targetFragment!!)

            PageType.values()
                .filterNot { it == pageType }
                .forEach { type ->
                    supportFragmentManager.findFragmentByTag(type.tag)?.let {
                        hide(it)
                    }
                }
        }
    }

    // PageType에 맞는 Fragment 반환
    private fun getFragment(pageType: PageType): Fragment? {
        return when(pageType) {
            PageType.PAGE1 -> MonthFragment.newInstance()
            PageType.PAGE2 -> WeekFragment.newInstance()
        }
    }
}