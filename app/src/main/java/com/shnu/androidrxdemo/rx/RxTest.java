package com.shnu.androidrxdemo.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/10/10
 * @description： （一句话解释一下！！）
 */
public class RxTest {

    public static void main(String[] args) {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Welcome");
                emitter.onNext("To");
                emitter.onNext("RxJava");

            }
        }).map(new EncryptFun()).map(new EncryptFun()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

}


/**
 * 加密操作
 */
class EncryptFun implements Function<String, String> {
    @Override
    public String apply(String s) throws Exception {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            //由ASCII码表得知大写字母是65-90,小写字母是97-122
            if ((chars[i] >= 65 && chars[i] < 90) || (chars[i] >= 97 && chars[i] < 122)) {
                chars[i] = (char) (chars[i] + 1);
            }
            if (chars[i] == 90) {
                chars[i] = 'a';
            }
            if (chars[i] == 122) {
                chars[i] = 'A';
            }
        }
        return new String(chars);
    }
}