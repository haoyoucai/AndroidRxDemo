package com.shnu.androidrxdemo.simple;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/04/25
 * @description： （上流事件！！）
 */
public interface UpStream {

    /**
     * 上游事件
     * {@link Emitter 上游发射器}
     *
     * @param emitter 上游发射器
     */
    void push(Emitter emitter);

}
