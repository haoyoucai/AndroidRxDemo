package com.shnu.androidrxdemo.simple;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/10/09
 * @description： （Rx测试类！！）
 */
public class SimpleRxTest {

    public static void main(String[] args) {

        RxPlugin rxPlugin = new RxPlugin();
        rxPlugin.createUP(new UpStream() {
            @Override
            public void push(Emitter emitter) {
                emitter.onNext("first");
                emitter.onNext("second");
                emitter.onNext("three");
                emitter.onNext("are you ok?");
                emitter.onNext("I am fine!");

            }
        }).subscribe(new DownStream() {
            int count = 0;

            @Override
            public void pull(String string) {
                System.out.println(++count + ":" + string);
            }
        });
    }
}
