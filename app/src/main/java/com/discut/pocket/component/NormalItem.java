package com.discut.pocket.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.discut.pocket.R;

/**
 * 自定义SwitchItem组件
 *
 * @author Discut
 * @version 1.0
 */
public class NormalItem extends BaseItem<NormalItem.ClickListener> {

    public NormalItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.normal_item_layout, this, true);

        @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalItem);
        this.setTitle(typedArray.getString(R.styleable.NormalItem_normalItemTitle));
        this.setDetails(typedArray.getString((R.styleable.NormalItem_normalItemDetails)));

        init();

    }

    private void init() {
        findViewById(R.id.content).setOnClickListener(v -> {
            if (getListener() != null) {
                getListener().onClick(this);
            }
        });

    }

    public interface ClickListener {
        void onClick(NormalItem view);

    }
}