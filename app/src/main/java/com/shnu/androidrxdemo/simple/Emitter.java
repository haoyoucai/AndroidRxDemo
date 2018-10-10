package com.shnu.androidrxdemo.simple;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/10/10
 * @description： （上游发射器）
 */
public interface Emitter {

    /**
     * 上游发射器
     *
     * @param emitter 发射内容
     */
    void onNext(String emitter);

}
