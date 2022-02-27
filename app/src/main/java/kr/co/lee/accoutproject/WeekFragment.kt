package kr.co.lee.accoutproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.lee.accoutproject.databinding.FragmentWeekBinding
import kr.co.lee.accoutproject.viewmodels.MainViewModel

@AndroidEntryPoint
class WeekFragment: Fragment() {
    private var _binding: FragmentWeekBinding? = null
    private val binding: FragmentWeekBinding
        get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_week, container, false)

        binding.apply {

        }

        return binding.root
    }
}