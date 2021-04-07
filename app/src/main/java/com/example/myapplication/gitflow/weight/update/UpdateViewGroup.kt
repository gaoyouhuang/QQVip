package com.example.myapplication.gitflow.weight.update

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlin.math.abs

class UpdateViewGroup : LinearLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var listener: UpdateListener? = null
    lateinit var updateAnnotation: UpdataAnnotation

    //TODO 刷新区域的高度
    private var refreshHeight = 0
    private var refreshState = DEFULT

    //TODO 刷新区域的Viewx
    var refreshRoot: View = inflate(context, R.layout.headerview_moren, null)
    lateinit var childRecycle: RecyclerView
    var scroller: Scroller = Scroller(context)
    var lastY = 0F

    init {
        orientation = VERTICAL
        post {
            refreshRoot.apply {
                refreshHeight = height
                var marginLayoutParams: MarginLayoutParams = LinearLayout.LayoutParams(width, height)
                layoutParams = marginLayoutParams.run {
                    setMargins(0, -height, 0, 0)
                    this
                }
            }
        }
        updateAnnotation = UpdateAnnotaionImpl(refreshRoot)
        addView(refreshRoot, 0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.apply {
            when (action) {
                MotionEvent.ACTION_MOVE -> {
                    //TODO 手势向下滑动 recyele item 无偏移量 才拦截
                    if (ev.y - lastY >= 0) {
                        Log.e(TAG, "向下滑动 !checkRecycleIsMove() ${!checkRecycleIsMove()}")
                        return !checkRecycleIsMove()
                    }
                    //TODO 向上滑动  当刷新视图可见的时候 且无偏移量的时候 拦截 目的是为了回退刷新偏移量
                    if (ev.y - lastY <= 0) {
                        if (scrollY < 0 && !checkRecycleIsMove())
                            return true
                    }
                }
            }
            lastY = ev.y
        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun checkRecycleIsMove(): Boolean {
        val findFirstVisibleItemPosition = (childRecycle.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (findFirstVisibleItemPosition < 0)
            return false
        val top = (childRecycle.layoutManager as LinearLayoutManager).findViewByPosition(findFirstVisibleItemPosition)?.top
        Log.e(TAG, "recycle move $top")
        return top != 0
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        childRecycle = (child as RecyclerView?)!!
        super.addView(child, params)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!scroller.isFinished)
                        scroller.abortAnimation()
                }
                MotionEvent.ACTION_MOVE -> {
                    //TODO 手动回退过程 避免把RecycleView也回退了
                    Log.e(TAG, "ACTION_MOVE scrollY $scrollY  ev.y - lastY ${ev.y - lastY}")
                    if (scrollY > 0) {
                        refreshState = DEFULT
                        return@apply
                    }
                    //TODO 刷新状态
                    refreshState = SCROLLER_BACK
                    if (abs(scrollY) in 0..maxRefresh * refreshHeight) {
                        //TODO 自动回退
                        if (abs(scrollY) in 0..refreshHeight)
                            refreshState = SCROLLER_ING
                        scrollBy(0, (-(ev.y - lastY)).toInt())
                        updateAnnotation.scrollIng(abs(scrollY), refreshHeight.toFloat())
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    when (refreshState) {
                        //TODO 初始化
                        DEFULT -> {
                            if (scrollY != 0)
                                toScroll(0, -scrollY)
                        }
                        //TODO 回退到0
                        SCROLLER_ING -> {
                            toScroll(0, -scrollY)
                            refreshState = DEFULT
                        }
                        //TODO 回退到刷新状态
                        SCROLLER_BACK -> {
                            toScroll(0, -refreshHeight - scrollY)
                            refreshState = REFRESH_ING
                            listener?.loadMore()
                            updateAnnotation.scrollBack(refreshHeight.toFloat()) {
                                Log.e(TAG, "annotation over")
                                refreshState = DEFULT
                                toScroll(0, -scrollY)
                            }
                        }
                    }
                }
            }
            lastY = ev.y
        }
        return true
    }

    private fun toScroll(x: Int, y: Int) {
        scroller.startScroll(0, scrollY, x, y, 1000)
        invalidate()
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.currY)
            updateAnnotation.scrollIng(abs(scroller.currY), refreshHeight.toFloat())
            postInvalidate()
        }
    }

    open fun loadOver() {
        updateAnnotation.scrollOver()
    }

    open interface UpdateListener {
        fun loadMore()
    }

    companion object {
        /**
         * 默认值
         * 下拉滑动中
         * 用户释放，回弹效果
         * 刷新中
         */
        const val DEFULT = 1
        const val SCROLLER_ING = 2
        const val SCROLLER_BACK = 3
        const val REFRESH_ING = 4
        const val TAG = "UpdataViewGroup"
        const val maxRefresh = 2

    }

    open interface UpdataAnnotation {
        fun scrollIng(distenceY: Int, unit: Float)
        fun scrollBack(unit: Float, over: () -> Unit)
        fun scrollOver()
    }

}