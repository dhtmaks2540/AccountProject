package kr.co.lee.accoutproject.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(rv: RecyclerView?, tv: TextView?) : RecyclerView.AdapterDataObserver() {

    private var emptyView: TextView? = null
    private var recyclerView: RecyclerView? = null

    init {
        recyclerView = rv
        emptyView = tv
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView!!.adapter != null) {
            val emptyViewVisible = recyclerView!!.adapter!!.itemCount == 0

            emptyView!!.visibility = if (emptyViewVisible)
                View.VISIBLE else View.INVISIBLE
            recyclerView!!.visibility = if (emptyViewVisible)
                View.INVISIBLE else View.VISIBLE
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
        super.onItemRangeChanged(positionStart, itemCount)
    }
}