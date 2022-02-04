package kr.co.lee.accoutproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import kr.co.lee.accoutproject.viewmodels.MainViewModelFactory
import org.joda.time.DateTime

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    // ViewModel 생성
    private val mainViewModel: MainViewModel by viewModels { MainViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding 레이아웃 초기화 및 데이터 셋팅
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            viewModel = mainViewModel

            // Floating Button
            addButton.setOnClickListener { actionButtonClicked() }

            // Bottom NavigationView
            mainBottomMenu.run {
                menuItemSelected()
                selectedItemId = R.id.action_month
            }
        }
        toolbarSetting()

        setContentView(binding.root)
    }

    // Toolbar 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> {
                println("Account !!! : ${mainViewModel.accounts.value}")
            }
        }

        return true
    }

    // Toolbar 처리
    private fun toolbarSetting() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // BottomNavigationView 선택 리스너
    private fun menuItemSelected() {
        binding.mainBottomMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_month -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, MonthFragment())
                    }
                }
                R.id.action_week -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, WeekFragment())
                    }
                }
                R.id.action_day -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, DayFragment())
                    }
                }
            }
            true
        }
    }

    // FloatingActionButton 이벤트 리스너
    private fun actionButtonClicked() {
        val addIntent = Intent(this, AddActivity::class.java)
        addIntent.putExtra("date", mainViewModel.selectedDate.value?.toString("yyyy/MM/dd"))
        startActivity(addIntent)
    }

//    fun actionButtonClicked(dateTime: DateTime) {
//        val addIntent = Intent(this, AddActivity::class.java)
//        addIntent.putExtra("date", dateTime.toString("yyyy/MM/dd"))
//        startActivity(addIntent)
//    }
}