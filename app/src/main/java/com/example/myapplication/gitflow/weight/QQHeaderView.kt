package com.example.myapplication.gitflow.weight

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Scroller
import com.example.myapplication.R


class QQHeaderView : ScrollView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    lateinit var myRoot: LinearLayout
    var headerRoot: View = inflate(context, R.layout.listview_header, null)
    lateinit var headerImg: ImageView
    lateinit var scroller: Scroller
    var imgBgHeight = 0
    var lastY = 0F
    var scaleMax = 2F
    var touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    init {
        headerImg = headerRoot.findViewById(R.id.layout_header_image) as ImageView
        headerImg.post { imgBgHeight = headerImg.height }

        myRoot = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addView(headerRoot)
        }
        scroller = Scroller(context)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        myRoot.addView(child, params)
        super.addView(myRoot, params)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        ev?.apply {
            when (action) {
                MotionEvent.ACTION_MOVE -> {
                    headerImg.apply {
//                        if (ev.y-lastY>touchSlop)
                        var moveY = ev.y - lastY
                        //TODO moveY>0向下滑动  <0向上滑动
                        var finalY = headerImg.layoutParams.height + moveY.toInt()
                        if (finalY < imgBgHeight || finalY >= imgBgHeight * scaleMax)
                            return@apply
                        headerImg.layoutParams.height = headerImg.layoutParams.height + moveY.toInt()
                        requestLayout()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (headerImg.layoutParams.height > imgBgHeight)
                        startAnimation()
                }
            }
            lastY = ev.y
        }

        return super.onTouchEvent(ev)
    }

    fun startAnimation() {
        ValueAnimator.ofInt(headerImg.layoutParams.height, imgBgHeight).apply {
            addUpdateListener {
                headerImg.layoutParams.height = it.animatedValue as Int
                headerImg.requestLayout()
            }
            duration = 500
            start()
        }
    }
}