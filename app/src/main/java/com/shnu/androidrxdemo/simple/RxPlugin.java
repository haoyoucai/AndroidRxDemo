package com.shnu.androidrxdemo.simple;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/04/25
 * @description： （一句话解释一下！！）
 */
public class RxPlugin {

    /**
     * {@link UpStream}
     * 上游事件接口
     */
    private UpStream upStream;
    /**
     * {@link DownStream}
     * 下游事件接口
     */
    private DownStream downStream;

    /**
     * 构建上游事件
     * @param upStream
     * @return
     */
    public RxPlugin createUP(UpStream upStream) {
        this.upStream = upStream;
        return this;
    }

    /**
     * 关联下游事件
     * @param downStream
     */
    public void subscribe(DownStream downStream) {
        this.downStream = downStream;
        if (upStream == null || downStream == null) {
            return;
        }
        String str;
        str = upStream.push();
        downStream.pull(str);
    }

}
