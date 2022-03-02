package kr.co.lee.accoutproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.ActivityReceiptBinding
import kr.co.lee.accoutproject.utilities.decimalFormat
import kr.co.lee.accoutproject.viewmodels.ReceiptViewModel

@AndroidEntryPoint
class ActivityReceipt : AppCompatActivity() {
    private lateinit var binding: ActivityReceiptBinding
    private val `receiptViewModel`: ReceiptViewModel by viewModels()

    private var result: String = ""
    // TextWatcher - 금액 EditText 설정
    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                val money = charSequence.toString().replace(",","").toLong()
                result = decimalFormat.format(money)
                binding.etMoney.setText(result)
                binding.etMoney.setSelection(result.length)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding 초기화
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receipt)
        binding.apply {
            lifecycleOwner = this@ActivityReceipt
            viewModel = receiptViewModel

            // ImageView Click
            ivBack.setOnClickListener {
                onBackButton()
            }

            // ImageView Click
            ivUpdate.setOnClickListener {
                updateButton(etMoney.text.toString(), etContent.text.toString())
                setResult(RESULT_OK)
                finish()
            }

            etMoney.addTextChangedListener(watcher)
        }

        setToolbar()
        getIntentData()
        setContentView(binding.root)
    }

    // Toolbar 메뉴 초기화
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.receipt_toolbar_menu, menu)
        return true
    }

    // 메뉴 클릭 리스너
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

    // Toolbar 초기화
    private fun setToolbar() {
        setSupportActionBar(binding.tbReceipt)
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

    // Account 업데이트
    private fun updateButton(money: String, content: String) {
        receiptViewModel.updateAccount(money, content)
    }
}