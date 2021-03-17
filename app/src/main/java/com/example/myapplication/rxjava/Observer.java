package com.example.myapplication.rxjava;

/**
 * Created by Administrator on 2018/5/28.
 */
//老鸨  观察者
public abstract class Observer <T>{

    public abstract void onNext(T t);
    public abstract void onError(Throwable e);
    public abstract void onComplete();
}
