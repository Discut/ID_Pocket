package com.discut.pocket.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.base.BaseTagInputAdaptor;
import com.discut.pocket.bean.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

public class TagInputView extends ConstraintLayout {
    private final ChipGroup chipGroup;
    private final TextInputLayout inputLayout;
    private final EditText editText;
    @SuppressLint("Recycle")
    private final TypedArray typedArray;

    private BaseTagInputAdaptor adaptor;

    public TagInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.tags_input_layout, this, true);

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagInputView);
        inputLayout = findViewById(R.id.input_view);
        editText = inputLayout.getEditText();
        chipGroup = findViewById(R.id.chips_box);

        update();
        initListener();
    }

    public void setAdaptor(BaseTagInputAdaptor adaptor) {
        this.adaptor = adaptor;
        update();
    }

    public void setTitle(String title) {
        inputLayout.setHint(title);
    }

    public void update() {
        this.setTitle(typedArray.getString(R.styleable.TagInputView_tagInputTitle));

        if (adaptor != null) {
            chipGroup.removeAllViews();
            for (int i = 0; i < adaptor.getChipsCount(); i++){
                @SuppressLint("ResourceType") Chip newChip =
                        (Chip) LayoutInflater.from(this.getContext()).inflate(R.xml.chip_item, chipGroup, false);
                newChip = adaptor.onBindDate(newChip, i);
                if (newChip != null) {
                    chipGroup.addView(newChip);
                }
            }
        }
    }

    private void initListener() {
        if (editText == null) {
            return;
        }
        editText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                if (editText.getText().toString().equals(" ")) {
                    editText.getText().clear();
                }
            } else {
                if (null == editText.getText() && chipGroup.getChildCount() > 0) {
                    editText.setText(" ");
                }
            }
        });

/*        editText.setOnKeyListener((view, i, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                onBackspacePressed, also edittext is empty
                if (chipGroup.getChildCount() <= 0) return false;
                Chip lastChip = (Chip) chipGroup.getChildAt(chipGroup.getChildCount() - 1);
                editText.append(lastChip.getText());
                chipGroup.removeView(lastChip);
            }
            return false;
        });*/

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 此处检测每个字符
                String text = editable.toString();
                Log.d("cyx", text);
                // 字符存在换行符 \n 则加入tag
                if (text.endsWith("\n") && text.length() != 1) {
                    text = text.replaceAll("\n", "");
                    //addNewChip(text.removeSuffix(","));
                    //addNewChip(text);

                    adaptor.getListener().onAddChip(text);
                    update();
                    editable.clear();
                } else if (text.length() == 1) {
                    editable.clear();
                }
            }
        });
    }

/*    private void addNewChip(String text) {
        @SuppressLint("ResourceType") Chip newChip =
                (Chip) LayoutInflater.from(this.getContext()).inflate(R.xml.chip_item, chipGroup, false);
        newChip.setId(ViewCompat.generateViewId());
        newChip.setText(text);
        newChip.setOnCloseIconClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.removeView(newChip);
            }
        });
        chipGroup.addView(newChip);
    }*/
}
