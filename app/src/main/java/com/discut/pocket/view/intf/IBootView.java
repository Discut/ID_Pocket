package com.discut.pocket.view.intf;

import com.discut.pocket.mvp.IView;

public interface IBootView extends IView {
    void navigateTo(Class<?> clazz);
}
