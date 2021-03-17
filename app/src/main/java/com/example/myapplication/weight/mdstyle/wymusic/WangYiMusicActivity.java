package com.example.myapplication.weight.mdstyle.wymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WangYiMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wang_yi_music);

        hook();
    }

    public void hook() {
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getMethod("currentActivityThread");
            Object o = currentActivityThread.invoke(null);

////            TODO -------
//            Class clz = Class.forName("android.app.ActivityTaskManager");
//            Field field = ReflectionUtil.reflectionField(clz, "IActivityTaskManagerSingleton");
//            Object objectSingleton = ReflectionUtil.getField(null, field);
//
//            Class classSingleton = Class.forName("android.util.Singleton");
//            Method get = ReflectionUtil.reflectionMethod(classSingleton, "get");
//            Object objectMInstance = ReflectionUtil.setMethod(objectSingleton, get);


//            TODO --------
            Class<? extends ClassLoader> aClass = getClassLoader().getClass();
            Method getLdLibraryPath = aClass.getMethod("getLdLibraryPath");
            getLdLibraryPath.setAccessible(true);
            String path = (String) getLdLibraryPath.invoke(getClassLoader());
            System.out.println("------  hook  getLdLibraryPath" + path);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("------error  hook " + e.getMessage());
        }
    }

//
//    /**======================
//     * 自身属性
//     *======================*/
//# 纱布
// TODO   app:contentScrim="?attr/colorPrimary" //完全折叠后的背景色
// TODO   app:scrimAnimationDuration="1000"     //纱布动画持续时间
// TODO   app:scrimVisibleHeightTrigger="20dp"  //纱布可见的最小距离(低于就会开始隐身)
//
// TODO           # 状态栏纱布(下面会特别介绍)
// TODO   app:statusBarScrim="@color/colorAccent"
//
// TODO          # 标题
// TODO   app:title="Hello"             //标题优先于"ToolBar标题Title"
// TODO   app:titleEnabled="true"       //是否使用大标题，true-使用；false-不使用
//
// TODO   app:expandedTitleMargin="1dp" //"自身Title"或者"ToolBar标题Title"的Margin值
// TODO   app:expandedTitleMarginStart="1dp"
// TODO   app:expandedTitleMarginEnd="1dp"
// TODO   app:expandedTitleMarginTop="1dp"
// TODO   app:expandedTitleMarginBottom="1dp"
// TODO   app:expandedTitleTextAppearance="?attr/cardBackgroundColor"
//
// TODO   app:expandedTitleGravity="bottom|center_horizontal" //"ToolBar标题Title"的重力
//
// TODO           # 折叠后Title的重力和样式
// TODO   app:collapsedTitleGravity="center_horizontal"
// TODO   app:collapsedTitleTextAppearance="?attr/colorAccent"
//
// TODO           # 需要参考AppBarLayout的`app:layout_scrollFlags`
// TODO   app:layout_scrollFlags=“xxxx”
//
//  TODO          # 指明toolbar的ID
//  TODO  app:toolbarId="@id/stars_toolbar"

}
