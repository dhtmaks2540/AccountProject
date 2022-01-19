package kr.co.lee.accoutproject.view.detailactivity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.ActivityDetailBinding
import kr.co.lee.accoutproject.view.detailactivity.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var dataBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        dataBinding.lifecycleOwner = this
        dataBinding.detailViewModel = viewModel
        setContentView(dataBinding.root)

        viewModel.selectMoney(intent.getDoubleExtra("money", 0.0))
    }
}