package com.example.myapplication.gitflow.weight.matrix

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 雷达
 */
class RadarView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    lateinit var sweepGradient: SweepGradient

    //TODO 雷达直径比例
    var radiusList = floatArrayOf(0F, 0.2F, 0.4F, 0.6F, 0.8F)
    var _paint = Paint()
    var _paintBg = Paint()
    var _matrix: Matrix = Matrix()

    //TODO 宽高
    var _width = 0
    var _height = 0
    var _radius = 0

    //TODO 旋转角度
    var nowDegrees = 0
    var speed = 10

    init {
        _paint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 1F
        }
        _paintBg.apply {
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
        }
        post(object : Runnable {
            override fun run() {
                nowDegrees = (nowDegrees + speed) % 360
                _matrix.setRotate(nowDegrees.toFloat(), (_width / 2).toFloat(), (_height / 2).toFloat())
                invalidate()
                postDelayed(this, 50)
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        _width = measuredWidth
        _height = measuredHeight
        _radius = min(_width, _height) / 2
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        sweepGradient = SweepGradient((_width / 2).toFloat(), (_height / 2).toFloat(), intArrayOf(Color.TRANSPARENT, Color.GRAY), floatArrayOf(0F, 1F))
        _paintBg.shader = sweepGradient
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.BLACK)
        radiusList.forEach {
            canvas?.drawCircle((_width / 2).toFloat(), (_height / 2).toFloat(), it * _radius.toFloat(), _paint)
        }
        canvas?.save()
        sweepGradient.setLocalMatrix(_matrix)
        canvas?.drawCircle((_width / 2).toFloat(), (_height / 2).toFloat(), _radius.toFloat(), _paintBg)
        canvas?.restore()
    }
}