package com.example.myapplication.rxjava;

/**
 * Created by Administrator on 2018/5/30.
 */

public interface Operator<T,R> extends Function<Observer<? super T>,Observer<? super R>>{
}
