package kr.co.lee.accoutproject.view.addactivity.view

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
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.databinding.ActivityAddBinding
import kr.co.lee.accoutproject.view.addactivity.viewmodel.AddViewModel
import kr.co.lee.accoutproject.view.detailactivity.view.DetailActivity
import java.text.DecimalFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private val decimalFormat = DecimalFormat("#,###")
    private var result: String = ""
    private val viewModel: AddViewModel by viewModels()


    // TextWatcher - 금액 EditText 설정
    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(!TextUtils.isEmpty(charSequence.toString()) && charSequence.toString() != result){
                val money = charSequence.toString().replace(",","").toDouble()
                result = decimalFormat.format(money)
                binding.krwEditView.setText(result)
                binding.krwEditView.setSelection(result.length)
                viewModel.setMoneyItem(result)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DataBinding 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        binding.lifecycleOwner = this
        binding.addViewModel = viewModel
        binding.addActivity = this

        setContentView(binding.root)

        setSupportActionBar()
        editTextSetting()

        intent.getStringExtra("date")?.let { viewModel.setDateItem(it) }
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

    // EditText 설정
    private fun editTextSetting() {
        val krwText = Currency.getInstance(Locale.KOREA).symbol;
        binding.krwLabel.text = krwText

        binding.krwEditView.addTextChangedListener(watcher)
        binding.krwEditView.requestFocus()

        //키보드 보이게 하는 부분
//	    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.showSoftInput(binding.krwEditView, InputMethodManager.SHOW_IMPLICIT)
    }

    fun buttonClick(moneyItem: String) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("money", moneyItem)
        startActivity(detailIntent)
//        finish()
    }
}