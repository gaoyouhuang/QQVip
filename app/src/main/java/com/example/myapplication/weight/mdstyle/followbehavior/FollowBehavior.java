package com.example.myapplication.weight.mdstyle.followbehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.myapplication.R;

public class FollowBehavior extends CoordinatorLayout.Behavior<TextView> {

    /**
     * @param context
     * @param attrs
     */
//    TODO 需要被重写，通过解析获取到xml所声明对应的behavior
//    TODO 通过获取该构造方法去反射，生成对应的behavior 不然会找不到构造方法 报错
//    TODO CoordinatorLayout#parseBehavior方法去创建
    public FollowBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置behavior所以来的View
     * @param parent
     * @param child      设置behavior的View
     * @param dependency 会遍历CoordinatorLayout所有的直接子控件
     * @return true表示依赖
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {

        if (dependency.getId() == R.id.btn1)
            return true;
        else
            return false;

    }

    /**
     * 设置依赖的动画
     * 当观察者状态（如位置 大小）发生变化的时候，会被回调
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
//    TODO 页面加载出来，就会被立马调用
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return
     */

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull TextView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }


    /**
     * setContentView的时候，通过LayoutInflater#inflate方法，通过pull的方式去解析xml
     * 首先或解析最外层的Viewgroup，如CoordinatorLayout回去重写generateLayoutParams（AttributeSet），从而实现解析app：behavior属性
     * 在解析CoordinatorLayout的直接子控件的时候，会调用外层的ViewGroup的generateLayoutParams，从而解析到behavior
     */
}
