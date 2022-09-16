package com.discut.pocket.view;

import com.discut.pocket.mvp.IView;

public interface IBootView extends IView {
    void navigateTo(Class<?> clazz);
}
