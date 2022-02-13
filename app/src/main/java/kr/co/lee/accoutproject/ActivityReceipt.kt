package kr.co.lee.accoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

            toolbarBack.setOnClickListener {
                onBackButton()
            }
        }

        setToolbar()
        getIntentData()
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.receipt_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                receiptViewModel.deleteAccount()
                finish()
            }
        }
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(binding.receiptToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getIntentData() {
        (intent.getSerializableExtra("accountAndType") as AccountAndType).let {
            receiptViewModel.setAccountAndType(
                it
            )
        }
    }

    private fun onBackButton() {
        finish()
    }
}