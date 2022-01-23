package kr.co.lee.accoutproject.view.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.view.addactivity.view.AddActivity
import kr.co.lee.accoutproject.view.dayfragment.view.DayFragment
import kr.co.lee.accoutproject.view.main.viewmodel.MainViewModel
import kr.co.lee.accoutproject.view.main.viewmodel.MainViewModelFactory
import kr.co.lee.accoutproject.view.monthfragment.view.MonthFragment
import kr.co.lee.accoutproject.view.weekfragment.view.WeekFragment
import org.joda.time.DateTime

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    // ViewModel 생성
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding 레이아웃 초기화 및 데이터 셋팅
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainActivity = this
        binding.mainViewModel = viewModel

        setContentView(binding.root)

        toolbarSetting()
        menuItemSelected()
    }

    // Toolbar 메뉴 생성
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
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
        binding.mainBottomMenu.run {
            setOnItemSelectedListener {
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
            selectedItemId = R.id.action_month
        }
    }

    // FloatingActionButton 이벤트 리스너
    fun actionButtonClicked(dateTime: DateTime) {
        val addIntent = Intent(this, AddActivity::class.java)
        addIntent.putExtra("date", dateTime.toString("yyyy/MM/dd"))
        startActivity(addIntent)
    }
}