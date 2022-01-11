package kr.co.lee.accoutproject.view.main.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.view.addactivity.view.AddActivity
import kr.co.lee.accoutproject.view.dayfragment.view.DayFragment
import kr.co.lee.accoutproject.view.monthfragment.view.MonthFragment
import kr.co.lee.accoutproject.view.weekfragment.view.WeekFragment
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var monthFragment: MonthFragment
    private lateinit var weekFragment: WeekFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        binding.mainActivity = this

        toolbarSetting()
        menuItemSelected()
    }

    // Toolbar 처리
    private fun toolbarSetting() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
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

    // FloatingActionButton 리스너
    fun actionButtonClicked(view: View) {
        val addIntent = Intent(this, AddActivity::class.java)
        startActivity(addIntent)
    }
}