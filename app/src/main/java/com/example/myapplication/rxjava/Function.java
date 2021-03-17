package com.example.myapplication.rxjava;

/**
 * Created by Administrator on 2018/5/30.
 */
public interface Function<T, R> {
    /**
     * Apply some calculation to the input value and return some other value.
     * @param t the input value
     * @return the output value
     * @throws Exception on error
     */
//    找到下一个观察者  为了去通知下一个观察者的   调用方法
     R apply(T t) ;
}