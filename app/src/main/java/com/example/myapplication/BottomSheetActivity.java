package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class BottomSheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
//        demo1(list);
        demo3();
    }

    public void demo3() {
        Observable.just("0123").concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String string) throws Exception {
                if (string.equals("123"))
                    return Observable.just("456");
                return Observable.error(new Exception("error 123"));
            }
        }).concatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                if (s.equals("456"))
                    return Observable.just("789");
                else
                    return Observable.just("error 456");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("-----onSubscribe ");

            }

            @Override
            public void onNext(String s) {
                System.out.println("-----onNext "+s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("-----onError "+e.getMessage());

            }

            @Override
            public void onComplete() {
                System.out.println("-----onComplete ");

            }
        });
    }

    public void demo2(final List<String> list) {
        Observable.range(0, list.size()).buffer(3).flatMap(new Function<List<Integer>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final List<Integer> integers) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Integer integer : integers) {
                            stringBuffer.append(integer + " ");
                        }
                        String msg = stringBuffer.toString();
                        if (msg.contains("7")) {
                            e.onError(new RuntimeException(" have 77"));
                        }
                        e.onNext(msg);
                        e.onComplete();
                    }
                });
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("-----accept " + s);
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return "error";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("-----accept 111 " + s);

            }
        });
    }

    public void demo1(final List<String> list) {
        Observable.range(0, list.size()).buffer(3).flatMap(new Function<List<Integer>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final List<Integer> integers) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        StringBuffer stringBuffer = new StringBuffer();
                        for (Integer integer : integers) {
                            stringBuffer.append(integer + " ");
                        }
                        String msg = stringBuffer.toString();
                        if (msg.contains("7")) {
                            e.onError(new RuntimeException(" have 77"));
                        }
//                        e.onNext(msg);
                        e.onComplete();
                    }
                });
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("-----accept " + s);
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                return "error";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("-----onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("-----onNext " + s);

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("-----onError " + e.getMessage());

            }

            @Override
            public void onComplete() {
                System.out.println("-----onComplete");

            }
        });
    }

    public void demo(final List<String> list) {
        Observable.fromIterable(list).flatMap(new Function<String, ObservableSource<Boolean>>() {
            @Override
            public ObservableSource<Boolean> apply(String s) throws Exception {
                System.out.println("-----" + s);
                if (s.equals("4"))
                    return Observable.just(false);
                else
                    return Observable.just(true);
            }
        }).flatMap(new Function<Boolean, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final Boolean aBoolean) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        if (aBoolean) {
//                            e.onNext("-----success");
                        } else {
//                            e.onNext("-----fail");
                            e.onError(new RuntimeException(" fail "));
                        }
                        e.onComplete();
                    }
                });
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("-----onSubscribe ");
            }

            @Override
            public void onNext(String s) {
                System.out.println("-----onNext " + s);

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("-----onError " + e.getMessage());

            }

            @Override
            public void onComplete() {
                System.out.println("-----onComplete ");

            }
        });
    }
}