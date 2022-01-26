package kr.co.lee.accoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.lee.accoutproject.databinding.ActivityDetailBinding
import kr.co.lee.accoutproject.data.TypeEntity
import kr.co.lee.accoutproject.adapters.DetailRecyclerViewAdapter
import kr.co.lee.accoutproject.viewmodels.DetailViewModel
import kr.co.lee.accoutproject.viewmodels.DetailViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(application) }
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.detailViewModel = viewModel
        binding.detailActivity = this
        setContentView(binding.root)

        intent.getStringExtra("money")?.let { viewModel.selectMoney(it) }
        intent.getIntExtra("type", 0).let { viewModel.selectTypes(it) }
        intent.getStringExtra("date")?.let {
            viewModel.selectDate(it)
        }
        setSupportActionBar()

        viewModel.types.observe(this, {
            setRecyclerAdapter(it)
        })
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

            android.R.id.home -> {
                finish()
            }
        }

        return true
    }

    // toolbar 설정
    private fun setSupportActionBar() {
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setRecyclerAdapter(typeList: List<TypeEntity>) {
        val detailRecyclerViewAdapter = DetailRecyclerViewAdapter(typeList, this)
        binding.detailRecycler.adapter = detailRecyclerViewAdapter
    }

    // 뒤로가기 버튼
    fun backButtonClick() {
        finish()
    }
}