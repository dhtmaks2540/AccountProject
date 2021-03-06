package kr.co.lee.accoutproject.ui.add

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.base.BaseActivity
import kr.co.lee.accoutproject.databinding.ActivityAddBinding
import kr.co.lee.accoutproject.ui.detail.DetailActivity
import kr.co.lee.accoutproject.utilities.decimalFormat
import java.util.*

@AndroidEntryPoint
class AddActivity : BaseActivity<ActivityAddBinding>(R.layout.activity_add) {
    private val addViewModel: AddViewModel by viewModels()

    private var result: String = ""
    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when(it.resultCode) {
                RESULT_OK -> {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }

    // 원화 설정
    private val krwText: String = Currency.getInstance(Locale.KOREA).symbol

    // TextWatcher - 금액 EditText 설정
    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                val money = charSequence.toString().replace(",","").toLong()
                addViewModel.setDoubleItem(money)
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
            viewModel = addViewModel

            // 입금, 출금 버튼
            btnIncome.setOnClickListener { buttonClick(addViewModel.moneyItem.value, 1, addViewModel.date.value, addViewModel.doubleMoneyItem.value) }
            btnDeposit.setOnClickListener { buttonClick(addViewModel.moneyItem.value, 0, addViewModel.date.value, addViewModel.doubleMoneyItem.value) }
            ivCancel.setOnClickListener { cancelClick() }

            // EditText Text Watcher 등록
            etMoney.addTextChangedListener(watcher)
            labelMoney.text = krwText
        }

        setSupportActionBar()
        getIntentData()
    }

    // 취소 버튼
    private fun cancelClick() {
        finish()
    }

    // Intent Data 설정
    private fun getIntentData() {
        intent.getStringExtra("date")?.let { addViewModel.setDateItem(it) }
    }

    // toolbar 설정
    private fun setSupportActionBar() {
        setSupportActionBar(binding.tbAdd)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    // Start intent button
    private fun buttonClick(moneyItem: String?, typeForm: Int, date: String?, doubleMoney: Long?) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("money", moneyItem)
        detailIntent.putExtra("doubleMoney", doubleMoney)
        detailIntent.putExtra("type", typeForm)
        detailIntent.putExtra("date", date)
        launcher.launch(detailIntent)
    }
}