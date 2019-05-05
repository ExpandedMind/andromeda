package demos.expmind.andromeda.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class CaptionsRecyclerView @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        RecyclerView(ctx, attrs, defStyle) {

    var emptyView: View? = null
        set(value) {
            field = value
            initEmptyView()
        }

    private val dataObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            initEmptyView()
        }
    }

    private fun initEmptyView() {
        emptyView?.run {
            this.visibility = if (adapter == null || adapter.itemCount == 0) View.VISIBLE else View.GONE
            this@CaptionsRecyclerView.visibility =
                    if (adapter == null || adapter.itemCount == 0) View.GONE else View.VISIBLE
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        super.setAdapter(adapter)

        oldAdapter?.unregisterAdapterDataObserver(dataObserver)

        getAdapter()?.registerAdapterDataObserver(dataObserver)

    }

}