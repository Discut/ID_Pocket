package com.discut.pocket.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.discut.pocket.R;
import com.google.android.material.chip.ChipGroup;

public class AccountCard extends RelativeLayout {

    private String title;
    private String details;
    private String main;
    private ChipGroup chipGroup;

    public AccountCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.account_card_layout, this, true);

/*        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomUI1);
        this.title = typedArray.getString(R.styleable.MyCustomUI1_cardTitle);
        this.details = typedArray.getString((R.styleable.MyCustomUI1_cardDetails));

        init();*/

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !title.isEmpty()) {
            TextView titleView = findViewById(R.id.card_title);
            titleView.setText(title);
            this.title = title;
        }
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        if (details != null && !details.isEmpty()) {
            TextView detailsView = findViewById(R.id.card_details);
            detailsView.setText(details);
            this.details = details;
        }
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        if (main != null && !main.isEmpty()) {
            ((TextView) findViewById(R.id.card_main)).setText(main);
            this.main = main;
        }
    }


}