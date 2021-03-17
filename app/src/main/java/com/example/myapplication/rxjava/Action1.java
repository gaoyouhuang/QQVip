package com.example.myapplication.rxjava;

/**
 * Created by Administrator on 2018/5/28.
 */

public interface Action1<T> {
//   通知顾客  开始点技师 ? super T
    void subscribe(T t);
}
