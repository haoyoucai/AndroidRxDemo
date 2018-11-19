package com.shnu.androidrxdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shnu.animation.androidrxdemo.R;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private EditText etCenter;

    private TextView tvRequest;

    public Subscription subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        etCenter = (EditText) findViewById(R.id.tv);

//        rxFlowable01();
    }


    /**
     * 使用timer创建上游事件
     */

    public void rxTimer(){
        Observable.timer(2000,TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

            }
        });



    }

    /**
     * 使用Interval 创建上游事件
     */
    public void rxInterval() {

        Disposable disposable = Observable.interval(2, 3, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "EMITTER : " + aLong + " in thread------" + Thread.currentThread().getName());
            }
        });

        //结束轮询
        //disposable.dispose();
    }


    /**
     * Flowable
     */


    public void rxFlowable01() {
        tvRequest = (TextView) findViewById(R.id.tv_request);
        tvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscription.request(5);
            }
        });
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                    Log.e(TAG, "EMITTER : " + i + " in thread------" + Thread.currentThread().getName());
                }
            }
        }, BackpressureStrategy.MISSING);
        FlowableSubscriber<Integer> subscriber = new FlowableSubscriber<Integer>() {

            @Override
            public void onSubscribe(final Subscription s) {
                subscription = s;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "RECEIVE :" + integer + " in thread-----" + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "ERROR :" + t.toString());

            }

            @Override
            public void onComplete() {

            }
        };
        flowable.subscribe(subscriber);
    }

    /**
     * 介绍RxJava 中最基本的线程使用方式；
     */
    public void rxJavaThread01() {

        /**
         * 上游事件，通过Schedule 控制线程
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Welcome to RxJava!");
                Log.e(TAG, "EMITTER : in thread------" + Thread.currentThread().getName());
            }
        }).subscribeOn(AndroidSchedulers.mainThread())  //上游事件在主线程中
                .observeOn(Schedulers.computation());   //下游事件在计算密集型的线程中

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "RECEIVE :" + s + " in thread-----" + Thread.currentThread().getName());
            }
        };
        observable.subscribe(consumer);
    }


    public void rxFlowable() {
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> emitter) throws Exception {

            }
        }, BackpressureStrategy.BUFFER);

        Consumer consumer = new Consumer<String>() {
            @Override
            public void accept(String string) throws Exception {

            }
        };


    }


    /**
     * RxJava  Flat map 的使用
     */
    public void rxFlatMap() {
        Observable.just("Welcome ", "To ", "RxJava").flatMap(new Function<String, ObservableSource<Character>>() {
            @Override
            public ObservableSource<Character> apply(final String s) throws Exception {
                return Observable.create(new ObservableOnSubscribe<Character>() {
                    @Override
                    public void subscribe(ObservableEmitter<Character> emitter) throws Exception {
                        char[] chars = s.toCharArray();
                        for (int i = 0; i < s.length(); i++) {
                            emitter.onNext(chars[i]);
                            Thread.sleep((long) (Math.random() * 1000));
                        }
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Character>() {
                    @Override
                    public void accept(final Character character) throws Exception {
                        etCenter.setText(etCenter.getText().toString() + character.toString());
                        etCenter.setSelection(etCenter.getText().length());
                        Log.e("Rx--FlatMap" + Thread.currentThread().getName(), character.toString());
                    }
                });
    }


    /**
     * RxJava  Flat map 的使用
     */
    public void rxConcatMap() {
        Observable.just("Welcome ", "To ", "RxJava").concatMap(new Function<String, ObservableSource<Character>>() {
            @Override
            public ObservableSource<Character> apply(final String s) throws Exception {
                return Observable.create(new ObservableOnSubscribe<Character>() {
                    @Override
                    public void subscribe(ObservableEmitter<Character> emitter) throws Exception {
                        char[] chars = s.toCharArray();
                        for (int i = 0; i < s.length(); i++) {
                            emitter.onNext(chars[i]);
                            Thread.sleep((long) (Math.random() * 1000));
                        }
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Character>() {
                    @Override
                    public void accept(final Character character) throws Exception {
                        etCenter.setText(etCenter.getText().toString() + character.toString());
                        etCenter.setSelection(etCenter.getText().length());
                        Log.e("Rx--FlatMap" + Thread.currentThread().getName(), character.toString());
                    }
                });
    }

    // 最简单的RxJava 代码

    /**
     * Really, You Got the RxJava !!!
     */
    public void pureRxSample() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(123);
                emitter.onNext(123);
                emitter.onNext(123);
                emitter.onNext(123);
                emitter.onNext(456);

                emitter.onComplete();
                //                emitter.onError(new Throwable("123"));
                //                try {
                //                    Integer integer = Integer.parseInt("778o");
                //                } catch (Exception e) {
                //                    emitter.onError(e);
                //                }
                emitter.onNext(456);
                emitter.onNext(456);
                emitter.onNext(456);
                emitter.onNext(456);

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("mainActivity", integer + "");

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    //1、如果只要进行消费，不关心onError  Exception

    public void rxCustomer() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                Log.e(TAG, "consumer" + str);
            }
        };

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Monday is the first day of the week!");
                emitter.onNext("Tuesday is tht second day of the week !");
                emitter.onNext("Wednesday is the third day of the week!");
                emitter.onNext("Thursday is the forth day of the Week!");
                emitter.onNext("Friday is the fifth day of the week!");
                //                emitter.onError(new Throwable("onError"));
                emitter.onComplete();
                emitter.onNext("Saturday is the sixth day of the week!");
                emitter.onNext("Sunday is the seventh day of the week!");

            }
        }).subscribe(consumer);
    }

    //Rx 线程调度

    public void rxThreadSchedule() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Monday is the first day of the week!");
                emitter.onNext("Tuesday is tht second day of the week !");
                emitter.onNext("Wednesday is the third day of the week!");
                emitter.onNext("Thursday is the forth day of the Week!");
                emitter.onNext("Friday is the fifth day of the week!");
                //                emitter.onError(new Throwable("onError"));
                emitter.onComplete();
                emitter.onNext("Saturday is the sixth day of the week!");
                emitter.onNext("Sunday is the seventh day of the week!");
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "consumer" + s + "   " + Thread.currentThread().getName());

                    }
                });
    }

    //发生Error 继续运行

    /**
     * 使用的情形 就是发生Exception 我们处理其他事情
     * <p>
     * (构造三种错误的方法）
     * 1、 emitter.onError(new Throwable("error"))
     * <p>
     * 2、  emitter.onNext(Integer.parseInt("0001w") + "");
     * <p>
     * 3、   emitter.onNext(Integer.parseInt("0001w") + "");  try
     */
    public void rxonErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("123");
                emitter.onNext("456");
                //                    emitter.onError(new Throwable("error"));

                try {
                    emitter.onNext(Integer.parseInt("0001w") + "");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                emitter.onNext("789");
            }
        }).onErrorResumeNext(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                //如果出错就走下面的代码  如果不出错就不执行下面的代码
                emitter.onNext("777");
                emitter.onNext("888");
                emitter.onError(new Throwable("000"));
                emitter.onNext("999");
            }
        })).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 最简单的方法  map
     * <p>
     * <p>
     * map 重点不是中间函数能处理什么东西
     * 而是可以将Observeable 的数据源修改为 Observe的数据
     */

    public void rxMap() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("first");
                emitter.onNext("second");
                emitter.onNext("third");
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return s.length();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                Log.e(TAG, s + "");

            }
        });
    }

    public void rxMapSuper() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("abc");
                emitter.onNext("efg");
                emitter.onNext("hij");
            }
        }).map(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("123");
                        emitter.onNext("456");
                        emitter.onNext("789");
                    }
                });
            }
        }).subscribe(new Consumer<ObservableSource<String>>() {
            @Override
            public void accept(ObservableSource<String> source) throws Exception {
                source.subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, s);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

        });

    }
}
