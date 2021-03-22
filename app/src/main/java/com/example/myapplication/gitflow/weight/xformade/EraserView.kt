package com.example.myapplication.gitflow.weight.xformade

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.myapplication.R

class EraserView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    var mBitmapSrc: Bitmap
    var mBitmapText: Bitmap
    var myPaint: Paint

    var myPath: Path = Path()
    var mLastX = 0F
    var mLastY = 0F

    init {
        myPaint = Paint().apply {
            isAntiAlias = true
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 50F
        }
        mBitmapText = BitmapFactory.decodeResource(resources, R.mipmap.guaguaka_text1)
        mBitmapSrc = BitmapFactory.decodeResource(resources, R.mipmap.guaguaka)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawBitmap(mBitmapText, 0F, 0F, myPaint)

            val saveLayer = saveLayer(0F, 0F, mBitmapSrc.width.toFloat(), mBitmapSrc.height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
            drawPath(myPath, myPaint)
            myPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
            drawBitmap(mBitmapSrc, 0F, 0F, myPaint)
            myPaint.xfermode = null

            restoreToCount(saveLayer)

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    myPath.moveTo(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
//                    myPath.quadTo(mLastX, mLastY, (mLastX + event.x) / 2, (event.y + mLastY) / 2)
                    myPath.quadTo(mLastX, mLastY, event.x, event.y)
                    myPath.lineTo(event.x, event.y)
                }
            }
            mLastX = event.x
            mLastY = event.y
            postInvalidate()
        }
        return true
    }
}