package com.discut.pocket.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.discut.pocket.R;

public class BaseItem<L> extends RelativeLayout {
    private String title;
    @ColorInt
    private int titleColor;
    private String details;
    @ColorInt
    private int detailsColor;
    private L listener;

    @SuppressLint("ResourceAsColor")
    public BaseItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseItem);
        this.titleColor = typedArray.getColor(R.styleable.BaseItem_fontPrimaryColor, R.color.font_color_primary);
        this.detailsColor = typedArray.getColor(R.styleable.BaseItem_fontSecondaryColor, R.color.font_color_second);
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        TextView titleView = findViewById(R.id.title);
        titleView.setTextColor(titleColor);
    }

    public int getDetailsColor() {
        return detailsColor;
    }

    public void setDetailsColor(int detailsColor) {
        this.detailsColor = detailsColor;
        TextView titleView = findViewById(R.id.details);
        titleView.setTextColor(detailsColor);
    }

    public L getListener() {
        return listener;
    }

    public void setListener(L listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            this.title = title;
            TextView titleView = findViewById(R.id.title);
            titleView.setText(title);
            titleView.setTextColor(titleColor);
        }
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        if (details != null && !details.isEmpty()) {
            this.details = details;
            TextView detailsView = findViewById(R.id.details);
            detailsView.setText(details);
            detailsView.setTextColor(detailsColor);

        }
    }
}
