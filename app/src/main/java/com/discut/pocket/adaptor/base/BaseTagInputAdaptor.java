package com.discut.pocket.adaptor.base;

import com.discut.pocket.component.TagInputView;
import com.google.android.material.chip.Chip;

import java.util.List;

public abstract class BaseTagInputAdaptor <T>{
    public abstract int getChipsCount();
    public abstract Chip onBindDate(Chip chip, int position);
    public abstract void onCreateView(TagInputView view);
    public abstract ChipsListener getListener();
    public abstract List<T> getTag();

    public interface ChipsListener{
        void onAddChip(String text);
    }
}
