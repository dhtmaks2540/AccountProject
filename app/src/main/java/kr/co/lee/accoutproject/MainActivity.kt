package kr.co.lee.accoutproject

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import kr.co.lee.accoutproject.databinding.ActivityMainBinding
import kr.co.lee.accoutproject.ui.MonthFragment
import kr.co.lee.accoutproject.ui.WeekFragment
import org.joda.time.DateTime

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var monthFragment: MonthFragment
    private lateinit var weekFragment: WeekFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        menuItemSelected()
    }

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
                }
                true
            }
            selectedItemId = R.id.action_month
        }
    }
}