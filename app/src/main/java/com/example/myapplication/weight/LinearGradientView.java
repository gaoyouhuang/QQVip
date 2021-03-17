package com.example.myapplication.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class LinearGradientView extends AppCompatTextView {
    public LinearGradientView(Context context) {
        super(context);
        init();
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    LinearGradient linearGradient;
    int changeLength = 0;
    int nowShowLength = 0;
    int sumLength = 0;
    public void init() {

    }

    TextPaint paint;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (paint == null) {
            //获取到画笔
            paint = getPaint();
            //获取到内容
            String s = getText().toString();
            //获取到单行的长度 可能会是多行
            sumLength = (int) paint.measureText(s);
            changeLength = (int) (sumLength / s.length() * 2);
            linearGradient = new LinearGradient(-changeLength, 0, 0, 0, new int[]{
                    0x22ffffff, 0xffffffff, 0x22ffffff}, null, Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        nowShowLength +=changeLength;
        if (nowShowLength>sumLength-1){
            nowShowLength = 0;
        }
        Matrix matrix = new Matrix();
        matrix.setTranslate(nowShowLength,0);
//        canvas.setMatrix(matrix);
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(100);
    }
}
