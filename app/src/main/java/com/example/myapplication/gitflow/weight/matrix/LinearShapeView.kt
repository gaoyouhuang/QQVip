package com.example.myapplication.gitflow.weight.matrix

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 跑马灯
 */
class LinearShapeView
    : AppCompatTextView {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
    }

    lateinit var linearGradient: LinearGradient
    lateinit var myPaint: TextPaint
    private var measureText = 0
    private var changeSize = 0
    private var nowChange = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!::myPaint.isInitialized) {
            myPaint = paint
            val showText = text.toString()
            //当行总长度
            measureText = myPaint.measureText(showText).toInt()
            changeSize = measureText / showText.length * 2
            linearGradient = LinearGradient((-changeSize).toFloat(), 0F, 0F, 0F, intArrayOf(
                    Color.RED, Color.WHITE, Color.RED), floatArrayOf(0F, 0.5F, 1F), Shader.TileMode.CLAMP)
            myPaint.shader = linearGradient
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        nowChange += changeSize
        if (nowChange >= measureText) nowChange = 0
        var shapeMatrix = Matrix()
        shapeMatrix.setTranslate(nowChange.toFloat(), 0F)
        myPaint.shader.setLocalMatrix(shapeMatrix)
        postInvalidateDelayed(100)
    }
}