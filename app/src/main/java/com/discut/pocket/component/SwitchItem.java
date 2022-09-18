package com.discut.pocket.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.discut.pocket.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * 自定义SwitchItem组件
 * @version 1.0
 * @author Discut
 */
public class SwitchItem extends BaseItem<SwitchItem.CheckedListener> {

    private SwitchMaterial switchView;
    public SwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.switch_item_layout, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchItem);
        this.setTitle(typedArray.getString(R.styleable.SwitchItem_switchItemTitle));
        this.setDetails(typedArray.getString((R.styleable.SwitchItem_switchItemDetails)));

        init();

    }

    private void init() {

        switchView = findViewById(R.id.switch_item);

        RelativeLayout content = findViewById(R.id.content);
        content.setOnClickListener(v -> {
            Log.d("TAG", "onClick");
            switchView.setChecked(!switchView.isChecked());
        });

        switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (getListener() != null) {
                    getListener().onCheck(this);
                }
            } else {
                if (getListener() != null) {
                    getListener().uncheck(this);
                }
            }

        });
    }

    public void setCheck(boolean isCheck){
        SwitchMaterial switchMaterial = findViewById(R.id.switch_item);
        switchMaterial.setChecked(isCheck);
    }

    public interface CheckedListener {
        void onCheck(SwitchItem view);

        void uncheck(SwitchItem view);
    }
}