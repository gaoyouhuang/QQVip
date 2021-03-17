package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.myapplication.rxjava.Function;
import com.example.myapplication.rxjava.Obserable;
import com.example.myapplication.rxjava.ObservableOnSubscribe;
import com.example.myapplication.rxjava.Observer;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ObservableOnSubscribe<String> observableOnSubscribe = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(Observer<? super String> observer) {

            }
        };

        Obserable.create(observableOnSubscribe)
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) {
                        return null;
                    }
                }).subscrible(new Observer<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
