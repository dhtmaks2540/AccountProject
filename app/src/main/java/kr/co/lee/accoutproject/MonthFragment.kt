package kr.co.lee.accoutproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.adapters.EmptyDataObserver
import kr.co.lee.accoutproject.adapters.MonthRecyclerAdapter
import kr.co.lee.accoutproject.calendar.CustomCalendarView
import kr.co.lee.accoutproject.data.AccountAndType
import kr.co.lee.accoutproject.databinding.FragmentMonthBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel
import org.joda.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MonthFragment: Fragment() {
    private var _binding: FragmentMonthBinding? = null
    private val binding: FragmentMonthBinding
        get() = _binding!!
    private lateinit var adapter: MonthRecyclerAdapter

    // Fragment KTX를 사용하여 Activity ViewModel 초기화
    private val mainViewModel: MainViewModel by activityViewModels()
    // Activity Result
    private val launcher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            when(it.resultCode) {
                Activity.RESULT_OK -> {
                    mainViewModel.setAccount()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // DataBinding
        _binding = DataBindingUtil.inflate<FragmentMonthBinding>(
            inflater, R.layout.fragment_month, container, false)
        binding.apply {
            viewModel = mainViewModel
            lifecycleOwner = this@MonthFragment

            // ClickHandler
            calendar.setEventHandler(object : CustomCalendarView.EventHandler {
                override fun onDayPress(localDate: LocalDate) {
                    if (mainViewModel.date.value?.monthOfYear == localDate.minusMonths(1).monthOfYear) { // 이전달 클릭
                        nextButtonClick()
                    } else if (mainViewModel.date.value?.monthOfYear == localDate.plusMonths(1).monthOfYear) { // 다음달 클릭
                        prevButtonClick()
                    }
                    mainViewModel.setDate(localDate)
                    setAdapter(mainViewModel.dateAccounts.value!!, localDate, launcher)
                }
            })
        }

        firstInit()
        subscribeUi()

        return binding.root
    }

    // Prev month button
    fun prevButtonClick() {
        val date = binding.calendar.prevButtonClick()
        mainViewModel.setDate(date)
        mainViewModel.setAccount()
    }

    // Next month button
    fun nextButtonClick() {
        val date = binding.calendar.nextButtonClick()
        mainViewModel.setDate(date)
        mainViewModel.setAccount()
    }

    // FirstInit
    private fun firstInit() {
        mainViewModel.setDate(binding.calendar.setFirstDate())
        mainViewModel.setAccount()
        val emptyObserver = EmptyDataObserver(binding.monthRecycler, binding.tvNoItem)
        adapter = MonthRecyclerAdapter(launcher)
        binding.monthRecycler.adapter = adapter
        adapter.registerAdapterDataObserver(emptyObserver)
    }

    // LiveData observe
    private fun subscribeUi() {
        mainViewModel.accounts.observe(viewLifecycleOwner) {
            mainViewModel.setDateAccounts()
        }

        mainViewModel.dateAccounts.observe(viewLifecycleOwner) {
            binding.calendar.updateCalendar(it)
            setAdapter(it, mainViewModel.date.value, launcher)
        }
    }

    // Adapter 설정
    private fun setAdapter(map: TreeMap<LocalDate, ArrayList<AccountAndType>?>, localDate: LocalDate?, launcher: ActivityResultLauncher<Intent>) {
        adapter.add(map[localDate])
        adapter.notifyDataSetChanged()
    }
    
    companion object {
        fun newInstance(title: String) = MonthFragment().apply{
            arguments = Bundle().apply {
                putString("title", title)
            }
        }
    }
}