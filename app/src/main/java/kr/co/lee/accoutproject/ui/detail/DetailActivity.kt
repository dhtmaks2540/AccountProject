package kr.co.lee.accoutproject.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.R
import kr.co.lee.accoutproject.base.BaseActivity
import kr.co.lee.accoutproject.databinding.ActivityDetailBinding
import kr.co.lee.accoutproject.model.TypeEntity
import kr.co.lee.accoutproject.ui.adapter.DetailRecyclerViewAdapter
import kr.co.lee.accoutproject.ui.adapter.OnEntityClickListener

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail), OnEntityClickListener {
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            viewModel = detailViewModel

            ivBack.setOnClickListener { backButtonClick() }
            ivCancel.setOnClickListener { cancelButtonClick() }
        }

        getIntentData()
        subscribeUi()
    }

    // Observer 등록
    private fun subscribeUi() {
        detailViewModel.types.observe(this) {
            setRecyclerAdapter(it)
        }
    }

    // Intent 데이터 획득
    private fun getIntentData() {
        intent.getStringExtra("money")?.let { detailViewModel.setMoney(it) }
        intent.getIntExtra("type", 0).let { detailViewModel.setTypes(it) }
        intent.getStringExtra("date")?.let { detailViewModel.setDate(it) }
        intent.getLongExtra("doubleMoney", 0)?.let { detailViewModel.setDoubleMoney(it) }
    }

    // RecyclerView Adapter 설정
    private fun setRecyclerAdapter(typeList: List<TypeEntity>) {
        val detailRecyclerViewAdapter = DetailRecyclerViewAdapter(typeList, this)
        binding.rcvDetail.adapter = detailRecyclerViewAdapter
    }

    // 뒤로가기 버튼
    private fun backButtonClick() {
        setResult(RESULT_CANCELED)
        finish()
    }

    // 취소 버튼
    private fun cancelButtonClick() {
        setResult(RESULT_OK)
        finish()
    }

    // Type 클릭 이벤트(Account Insert)
    override fun onEntityClick(entity: TypeEntity) {
        detailViewModel.setAccount(detailViewModel.doubleMoney.value!!, detailViewModel.content.value!!, detailViewModel.date.value!!, entity.typeId)
        setResult(RESULT_OK)
        finish()
    }
}