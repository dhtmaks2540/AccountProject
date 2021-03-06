package kr.co.lee.accoutproject.ui.receipt

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.base.BaseActivity
import kr.co.lee.accoutproject.databinding.ActivityReceiptBinding
import kr.co.lee.accoutproject.model.AccountAndType
import kr.co.lee.accoutproject.utilities.decimalFormat

@AndroidEntryPoint
class ReceiptActivity : BaseActivity<ActivityReceiptBinding>(R.layout.activity_receipt) {
    private val receiptViewModel: ReceiptViewModel by viewModels()

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

        binding.apply {
            viewModel = receiptViewModel

            // 이전 버튼 클릭
            ivBack.setOnClickListener {
                onBackButton()
            }

            // 수정 버튼 클릭
            ivUpdate.setOnClickListener {
                updateButton(etMoney.text.toString(), etContent.text.toString())
                setResult(RESULT_OK)
                finish()
            }

            // 삭제 버튼 클릭
            ivDelete.setOnClickListener {
                deleteClick()
            }

            // TextWatcher 등록
            etMoney.addTextChangedListener(watcher)
        }

        setToolbar()
        getIntentData()
        setContentView(binding.root)
    }

    // Account 삭제
    private fun deleteClick() {
        receiptViewModel.deleteAccount()
        setResult(RESULT_OK)
        finish()
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