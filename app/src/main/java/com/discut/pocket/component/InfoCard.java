package com.discut.pocket.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.discut.pocket.R;

public class InfoCard extends ConstraintLayout {

    private final TypedArray typedArray;

    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;
    private TextView titleView;
    private TextView detailsView;

    public InfoCard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.info_card, this, true);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.InfoCard);

        initView();
    }

    public String getTitle() {
        if (titleView != null) {
            return titleView.getText().toString();
        }
        return null;
    }

    public void setTitle(String title) {
        if (this.titleView != null) {
            this.titleView.setText(title);
        }
    }

    public String getDetails() {
        if (detailsView != null) {
            return detailsView.getText().toString();
        }
        return null;
    }

    public void setDetails(String details) {
        if (detailsView != null) {
            detailsView.setText(details);
        }
    }

    private void initView() {
        titleView = findViewById(R.id.info_card_title);
        detailsView = findViewById(R.id.info_card_details);

        setTitle(typedArray.getString(R.styleable.InfoCard_infoCardTitle));
        setDetails(typedArray.getString(R.styleable.InfoCard_infoCardDetails));

        findViewById(R.id.card_box).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });

        findViewById(R.id.card_box).setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    return longClickListener.onLongClick(v);
                }
                return false;
            }
        });

    }

    public void setOnClickListener(@Nullable OnClickListener l) {
        clickListener = l;
    }

    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        longClickListener = l;
    }
}
