package com.discut.pocket.component;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义清扫手势 Recycler
 */
public class SwipeRecycler extends RecyclerView {
    public SwipeRecycler(@NonNull Context context) {
        super(context);
    }

    //itemTouchHelper.att


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);
    }

}
