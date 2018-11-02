package com.shnu.androidrxdemo.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/10/10
 * @description： （一句话解释一下！！）
 */
public class RxTest {

    public static void main(String[] args) {

        /**
         * observable 可订阅的（上游事件）
         */

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);

            }
        });
        /**
         * observer 下游事件
         */
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                System.out.println("接收到上游的内容：" + o.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");

            }
        };
        /**
         * 上下游事件互相订阅，关联
         */
        observable.subscribe(observer);
    }
}
