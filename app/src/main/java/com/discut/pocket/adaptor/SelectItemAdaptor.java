package com.discut.pocket.adaptor;

public abstract class SelectItemAdaptor {
    public abstract int getSelectedOption();

    public abstract String[] getOptions();

    public String getDialogTitle() {
        return "";
    }

}
