package kr.co.lee.accoutproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.databinding.ActivityAddBinding
import kr.co.lee.accoutproject.viewmodels.AddViewModel
import java.text.DecimalFormat
import java.util.*

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private val decimalFormat = DecimalFormat("#,###")
    private var result: String = ""
    private val addViewModel: AddViewModel by viewModels()

    // TextWatcher - 금액 EditText 설정
    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                val money = charSequence.toString().replace(",","").toLong()
                addViewModel.setDoubleItem(money)
                result = decimalFormat.format(money)
                binding.krwEditView.setText(result)
                binding.krwEditView.setSelection(result.length)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    private val krwText: String = Currency.getInstance(Locale.KOREA).symbol;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        binding.apply {
            lifecycleOwner = this@AddActivity
            viewModel = addViewModel
            activity = this@AddActivity

            // 입금버튼
            depositButton.setOnClickListener { buttonClick(addViewModel.moneyItem.value, 1, addViewModel.date.value, addViewModel.doubleMoneyItem.value) }
            expenseButton.setOnClickListener { buttonClick(addViewModel.moneyItem.value, 0, addViewModel.date.value, addViewModel.doubleMoneyItem.value) }

            // EditText
            krwEditView.addTextChangedListener(watcher)
            krwLabel.text = krwText
        }
        setSupportActionBar()

        intent.getStringExtra("date")?.let { addViewModel.setDateItem(it) }

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_toolbar_menu, menu)
        return true
    }

    // Toolbar 메뉴 선택
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_cancel -> {
                finish()
            }
        }
        return true
    }

    // toolbar 설정
    private fun setSupportActionBar() {
        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    // button
    private fun buttonClick(moneyItem: String?, typeForm: Int, date: String?, doubleMoney: Long?) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("money", moneyItem)
        detailIntent.putExtra("doubleMoney", doubleMoney)
        detailIntent.putExtra("type", typeForm)
        detailIntent.putExtra("date", date)
        startActivity(detailIntent)
        finish()
    }
}