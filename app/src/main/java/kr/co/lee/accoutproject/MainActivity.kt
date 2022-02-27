package kr.co.lee.accoutproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel 생성
    private val mainViewModel: MainViewModel by viewModels()

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
                    (fragment as WeekFragment)
                }
            }

            ivNext.setOnClickListener {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fragment?.tag == "Month") {
                    (fragment as MonthFragment).nextButtonClick()
                } else if (fragment?.tag == "Week") {
                    (fragment as WeekFragment)
                }
            }

            // Floating Button
            addButton.setOnClickListener { actionButtonClicked() }

            // Bottom NavigationView
            mainBottomMenu.run {
                menuItemSelected()
                selectedItemId = R.id.action_month
            }
        }

//        setToolbar()
        setContentView(binding.root)
    }

    // Toolbar 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    // Menu Selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
//                println("Account !!! : ${mainViewModel.accounts.value}")
            }
        }

        return true
    }

    // Toolbar 처리
    private fun setToolbar() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // BottomNavigationView 선택 리스너
    private fun menuItemSelected() {
        binding.mainBottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_month -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, MonthFragment(), "Month")
                    }
                }
                R.id.action_week -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, WeekFragment(), "Week")
                    }
                }
            }
            true
        }
    }

    // FloatingActionButton 이벤트 리스너
    private fun actionButtonClicked() {
        val addIntent = Intent(this, AddActivity::class.java)
        addIntent.putExtra("date", mainViewModel.date.value?.toString("yyyy/MM/dd"))
        startActivity(addIntent)
    }
}