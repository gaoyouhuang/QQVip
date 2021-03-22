package com.example.myapplication.gitflow.weight.matrix

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.weight.MagnifierView

/**
 * 放大镜
 */
class MagnifierView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private lateinit var bitmapBg: Bitmap
    private lateinit var bitmapMagnifier: Bitmap
    private lateinit var shapeDrawable: ShapeDrawable
    private lateinit var _matrix: Matrix
    private var _paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        bitmapBg = BitmapFactory.decodeResource(resources, R.mipmap.book_bg)
        bitmapMagnifier = Bitmap.createScaledBitmap(bitmapBg, bitmapBg.width * FACTOR, bitmapBg.height * FACTOR, true)
        _matrix = Matrix()
        shapeDrawable = ShapeDrawable(OvalShape()).apply {
            paint.shader = BitmapShader(bitmapMagnifier, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            bounds = Rect(-RADIUS, -RADIUS, RADIUS, RADIUS)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawBitmap(bitmapBg, 0F, 0F, _paint)
            shapeDrawable.draw(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                _matrix.setTranslate(-event?.x * FACTOR + RADIUS, -event?.y * FACTOR + RADIUS)
                shapeDrawable.paint.shader.setLocalMatrix(_matrix)
                shapeDrawable.bounds = Rect(event?.x.toInt() - RADIUS, event?.y.toInt() - RADIUS,
                        event?.x.toInt() + RADIUS, event?.y.toInt() + RADIUS)
                invalidate()
            }
        }
        return true
    }

    companion object {
        const val FACTOR = 2
        const val RADIUS = 100
    }
}