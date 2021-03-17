package com.example.myapplication.rxjava;


/**
 * Created by Administrator on 2018/5/28.
 */
//人民日报（被观察者Observable）

public class Obserable<T> {
    //服务员  发射器
//    角色  ： 编辑部门
    private ObservableOnSubscribe onSubscribe;

    private Obserable(ObservableOnSubscribe onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    //    创造操作符 create
    public static <T> Obserable<T> create(ObservableOnSubscribe<T> onSubscribe) {
        return new Obserable<T>(onSubscribe);
    }

//    Observer<? super T>    存   add     set   subscribe   变换操作符   区别 存
    public  void subscrible(Observer<? super T> subscrible){
        onSubscribe.subscribe(subscrible);
    }

    public <R> Obserable<R> map(Function<? super T,? extends R> function){
        return new Obserable<>(new OnSubscribleLift(onSubscribe,function));
    }

}
