package com.example.myapplication.rxjava;

/**
 * Created by Administrator on 2018/5/28.
 */
//服务员  跟老鸨 打交道    特殊  ? super  发射器
public interface ObservableOnSubscribe<T>  extends Action1<Observer<? super  T>> {
}
