package com.hl.juc;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 描述: 自定义AQS
 * 作者: panhongtong
 * 创建时间: 2020-08-11 22:51
 **/
public class MyAQSDemo extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        return super.tryAcquire(arg);
    }

    @Override
    protected boolean tryRelease(int arg) {
        return super.tryRelease(arg);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return super.tryAcquireShared(arg);
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        return super.tryReleaseShared(arg);
    }

    @Override
    protected boolean isHeldExclusively() {
        return super.isHeldExclusively();
    }
}
