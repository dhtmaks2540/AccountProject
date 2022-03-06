package kr.co.lee.accoutproject.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(rv: RecyclerView?, tv: TextView?) : RecyclerView.AdapterDataObserver() {
    // 데이터가 없을 경우 보여줄 View
    private var emptyView: TextView? = null
    // 데이터가 있을 경우 보여줄 View
    private var recyclerView: RecyclerView? = null

    init {
        recyclerView = rv
        emptyView = tv
        checkIfEmpty()
    }

    // 데이터의 개수에 따른 View Visibility 설정
    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView!!.adapter != null) {
            // 데이터의 개수가 0인지
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount == 0

            // 없으면 VISIBLE, 있으면 INVISIBLE
            emptyView!!.visibility = if (emptyViewVisible)
                View.VISIBLE else View.INVISIBLE
            // 없으면 INVISIBLE, 있으면 VISIBLE
            recyclerView!!.visibility = if (emptyViewVisible)
                View.INVISIBLE else View.VISIBLE
        }
    }

    // 데이터의 변화에 따라 호출되는 메소드
    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }
}