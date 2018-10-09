package com.shnu.androidrxdemo.simple;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/10/09
 * @description： （一句话解释一下！！）
 */
public class SimpleRxTest {

    public static void main(String[] args) {
        RxPlugin rxPlugin = new RxPlugin();
        rxPlugin.createUP(new UpStream() {
            @Override
            public String push() {
                return "山寨RxJava";
            }
        }).subscribe(new DownStream() {
            @Override
            public void pull(String string) {
                System.out.println(string);
            }
        });
    }
}
