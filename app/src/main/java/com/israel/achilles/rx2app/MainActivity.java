package com.israel.achilles.rx2app;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Import the view.RxView class, so you can use RxView.clicks//

import com.jakewharton.rxbinding.view.RxView;

//Import widget.RxTextView so you can use RxTextView.textChanges//

import com.jakewharton.rxbinding.widget.RxTextView;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends RxAppCompatActivity {

    Observable<Integer> myObservable = Observable.range(0, 25);
Subscription subscription, subscription2;

//    public final Single<Thread> delay(final long time, final TimeUnit unit, final Scheduler scheduler) {
//        return null;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        lifecycle=RxLifecycleAndroid.bindActivity();
        Button button = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.textView);
        EditText editText = (EditText) findViewById(R.id.editText);
         subscription= RxView.clicks(button)
                 .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Toast.makeText(MainActivity.this, "RxView.clicks", Toast.LENGTH_SHORT).show();
                });

        subscription2=RxTextView.textChanges(editText)
                .delay(1000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    textView.setText(charSequence);
                });
    }

    @Override
    public void onResume() {
        super.onResume();
//        myObservable
//            .compose(bindToLifecycle())
//            .subscribe();
    }


    @Override
    protected void onStop() {
        super.onStop();
        subscription.unsubscribe();
        subscription2.unsubscribe();
    }
}
