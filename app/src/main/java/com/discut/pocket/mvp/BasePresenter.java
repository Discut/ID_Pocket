package com.discut.pocket.mvp;

import java.lang.ref.WeakReference;

/**
 * Presenter基类
 * @version 1.0
 * @author Discut
 * @param <T> View的泛型
 */
public class BasePresenter<T extends IView> {
    /**
     * 弱引用
     */
    public WeakReference<T> reference;

    /**
     * 绑定 T
     * @param view 待绑定的视图对象
     */
    public void attachView(T view) {
        this.reference = new WeakReference<>(view);
    }

    /**
     * 解绑视图对象
     */
    public void detachView() {
        if (null == this.reference) return;
        this.reference.clear();
        this.reference = null;
    }
}
