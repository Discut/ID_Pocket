package com.discut.pocket.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discut.pocket.R;

public class BaseItem<L> extends RelativeLayout {
    private String title;
    private String details;
    private L listener;

    public BaseItem(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        }
    }
}
