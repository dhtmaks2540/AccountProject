package kr.co.lee.accoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ActivityReceiptBinding
import kr.co.lee.accoutproject.viewmodels.ReceiptViewModel

@AndroidEntryPoint
class ActivityReceipt : AppCompatActivity() {
    private lateinit var binding: ActivityReceiptBinding
    private val receiptViewModel: ReceiptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_receipt)
        binding.apply {
            lifecycleOwner = this@ActivityReceipt
            viewModel = receiptViewModel
            activity = this@ActivityReceipt
        }

        getIntentData()
        setContentView(binding.root)
    }

    private fun setToolbar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getIntentData() {
        (intent.getSerializableExtra("accountAndType") as AccountAndType).let { receiptViewModel.setAccountAndType(it) }
    }

    fun onBackButton() {
        finish()
    }
}