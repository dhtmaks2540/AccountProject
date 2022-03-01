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

        // DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receipt)
        binding.apply {
            lifecycleOwner = this@ActivityReceipt
            viewModel = receiptViewModel
            activity = this@ActivityReceipt

            toolbarBack.setOnClickListener {
                onBackButton()
            }

            ivUpdate.setOnClickListener {
                updateButton(moneyView.text.toString(), contentView.text.toString())
                setResult(RESULT_OK)
                finish()
            }
        }

        setToolbar()
        getIntentData()
        setContentView(binding.root)
    }

    // Option Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.receipt_toolbar_menu, menu)
        return true
    }

    // Menu 클릭 리스너
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                receiptViewModel.deleteAccount()
                setResult(RESULT_OK)
                finish()
            }
        }
        return true
    }

    // Toolbar init
    private fun setToolbar() {
        setSupportActionBar(binding.receiptToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // Intent를 통해 넘어온 데이터
    private fun getIntentData() {
        (intent.getSerializableExtra("accountAndType") as AccountAndType).let {
            receiptViewModel.setAccountAndType(
                it
            )
        }
    }

    // BackButton
    private fun onBackButton() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun updateButton(money: String, content: String) {
        receiptViewModel.updateAccount(money, content)
    }
}