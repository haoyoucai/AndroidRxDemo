package com.shnu.androidrxdemo.simple;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/04/25
 * @description： （一句话解释一下！！）
 */
public interface DownStream {

    /**
     * 下游事件
     *
     * @param string
     */
    void pull(String string);

}
