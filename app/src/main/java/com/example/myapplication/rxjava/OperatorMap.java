package com.example.myapplication.rxjava;

/**
 * Created by Administrator on 2018/5/30.
 */
public class OperatorMap<T,R> implements Operator<R, T> {


    Function<? super T, ? extends R> function ;
    public   OperatorMap(Function<? super T, ? extends R> function) {
        this.function = function;

    }

    @Override
    public Observer<? super T> apply(Observer<? super R> observer) {

        return new InnerObserver(observer,function);
    }


    class InnerObserver<T,R> extends Observer<T>{
        Observer<? super R> observer;
        Function<? super T, ? extends R> function ;

        public InnerObserver(Observer<? super R> observer, Function<? super T, ? extends R> function) {
            this.observer = observer;
            this.function = function;
        }

        @Override
        public void onNext(T t) {
            R r = function.apply(t);
            observer.onNext(r);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
