package kr.co.lee.accoutproject.view.detailactivity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.ActivityDetailBinding
import kr.co.lee.accoutproject.view.detailactivity.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.detailViewModel = viewModel
        setContentView(binding.root)

        intent.getStringExtra("money")?.let { viewModel.selectMoney(it) }
        setSupportActionBar()
    }
    
    // 메뉴 초기화
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_toolbar_menu, menu)
        return true
    }

    // 메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_cancel -> {
                finish()
            }

            R.id.home -> {

            }
        }

        return true
    }

    // toolbar 설정
    private fun setSupportActionBar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setRecyclerAdapter() {
        
    }
}