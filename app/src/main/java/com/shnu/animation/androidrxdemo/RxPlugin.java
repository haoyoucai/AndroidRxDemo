package com.shnu.animation.androidrxdemo;

/**
 * @author： 沈丹来 shendanlai@tniu.com
 * @date： 2018/04/25
 * @description： （一句话解释一下！！）
 */
public class RxPlugin {

    private UpStream upStream;
    private DownStream downStream;

    public RxPlugin createUP(UpStream upStream) {
        this.upStream = upStream;
        return this;
    }

    public RxPlugin createDown(DownStream downStream) {
        this.downStream = downStream;
        return this;
    }

    public void subscribe(RxPlugin rxPlugin) {
        if (upStream == null || downStream == null) {
            return;
        }
        String str;
        str = upStream.push();
        downStream.pull(str);
    }

}
