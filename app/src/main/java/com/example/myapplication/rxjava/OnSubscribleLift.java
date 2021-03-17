package com.example.myapplication.rxjava;


/**
 * Created by Administrator on 2018/5/30.
 */
//角色:  造纸厂的制作部门
// rxjava:发射器  与发射器之间的引用    不是一个环 与另外一个环的直接引用
public class OnSubscribleLift<T, R> implements ObservableOnSubscribe<R> {


    ObservableOnSubscribe<T> panert;
    Operator<? extends R, ? super T> operator;

    public OnSubscribleLift(ObservableOnSubscribe onSubscribe, Function<? super T, ? extends R> function) {
        this.panert = onSubscribe;
        this.operator = new OperatorMap<T,R>(function);
    }

    @Override
    public void subscribe(Observer<? super R> observer) {
        Observer<? super T> apply = operator.apply(observer);
        panert.subscribe(apply);
    }
}
