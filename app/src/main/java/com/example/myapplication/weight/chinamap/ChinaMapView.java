package com.example.myapplication.weight.chinamap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.example.myapplication.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ChinaMapView extends View {
    Paint paint;
    List<PathBean> pathBeanArray;
    float svgWidth = 0, svgHeight = 0, viewWidth = 0, viewHeight = 0;
    float scale = 0;
    int[] colors = new int[]{Color.parseColor("#00ff00"), Color.parseColor("#ffff00"), Color.parseColor("#00ffff"),
            Color.parseColor("#ff0000"), Color.parseColor("#0000ff"), Color.parseColor("#ff00ff")};
    boolean isFinish = false;

    public ChinaMapView(Context context) {
        super(context);
        init();
    }

    public ChinaMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChinaMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        pathBeanArray = new ArrayList<>();
        readSVG();
    }

    @SuppressLint("RestrictedApi")
    private void readSVG() {
        new Thread() {
            @Override
            public void run() {
                isFinish = false;
                InputStream inputStream = getContext().getResources().openRawResource(R.raw.china);
                try {
                    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    Document parse = documentBuilder.parse(inputStream);
                    NodeList path = parse.getElementsByTagName("path");
                    //TODO 获取实际完整解析出来需要的大小
                    //TODO 可能需要更具View的实际大小进行缩放
                    float top = 0, left = 0, right = 0, bottom = 0;
                    for (int i = 0; i < path.getLength(); i++) {
                        Node itemNode = path.item(i);
                        String pathData = itemNode.getAttributes().getNamedItem("android:pathData").getNodeValue();
                        String provice = itemNode.getAttributes().getNamedItem("android:provice").getNodeValue();
                        Path _path = PathParser.createPathFromPathData(pathData);
                        RectF rectF = new RectF();
                        _path.computeBounds(rectF, true);

                        top = top < rectF.top ? top : rectF.top;
                        left = left < rectF.left ? left : rectF.left;
                        right = right > rectF.right ? right : rectF.right;
                        bottom = bottom > rectF.bottom ? bottom : rectF.bottom;

                        PathBean pathBean = new PathBean(_path, provice);
                        pathBeanArray.add(pathBean);
                    }
                    svgHeight = bottom - top;
                    svgWidth = right - left;
                    isFinish = true;
                    postInvalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFinish) {
            if (viewWidth > viewHeight)
                scale = viewHeight / svgHeight;
            else
                scale = viewWidth / svgWidth;
            System.out.println("haha svg" + svgWidth + " " + svgHeight);
        }
        canvas.drawColor(Color.WHITE);
        System.out.println("haha " + pathBeanArray.size() + " " + scale);
        if (scale > 0)
            for (int i = 0; i < pathBeanArray.size(); i++) {
                canvas.save();
                canvas.scale(scale, scale);
                int color = colors[i % 6];
                paint.setColor(color);
                pathBeanArray.get(i).draw(canvas, paint);
                canvas.restore();
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        for (int i = 0; i < pathBeanArray.size(); i++) {
            boolean isclick = pathBeanArray.get(i).isclick(x / scale, y / scale);
            if (isclick) {
                postInvalidate();
                break;
            }
        }
        return true;
    }

    class PathBean {
        Path path;
        String city;
        boolean isClick = false;

        public PathBean(Path path, String city) {
            this.path = path;
            this.city = city;
        }

        public void draw(Canvas canvas, Paint paint) {
            if (!isClick) {
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPath(path, paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(1);
                paint.setColor(Color.WHITE);
                canvas.drawPath(path, paint);
            } else {
                paint.setStyle(Paint.Style.FILL);
                paint.setAlpha(150);
                canvas.drawPath(path, paint);
                isClick = false;
            }
        }

        public boolean isclick(float x, float y) {

            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            Region region = new Region();
            region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            if (region.contains((int) x, (int) y)) {
                isClick = true;
                return true;
            }
            return false;
        }
    }
}
