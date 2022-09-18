package com.discut.pocket.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.discut.pocket.R;
import com.discut.pocket.adaptor.SelectItemAdaptor;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SelectItem extends BaseItem<SelectItem.ClickListener> {

    private String dialogTitle;
    private OptionListener optionListener;
    private SelectItemAdaptor adaptor;
    private boolean isShowConfirmButton = false;
    private boolean isShowCancelButton = false;
    private AlertDialog dialogInstance;

    public SelectItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.select_item_layout, this, true);

        @SuppressLint("Recycle") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectItem);
        setTitle(typedArray.getString(R.styleable.SelectItem_selectItemTitle));
        setDetails(typedArray.getString((R.styleable.SelectItem_selectItemDetails)));
        dialogTitle = typedArray.getString((R.styleable.SelectItem_selectItemDialogTitle));

        init();

    }

    public boolean isShowConfirmButton() {
        return isShowConfirmButton;
    }

    public void setShowConfirmButton(boolean showConfirmButton) {
        isShowConfirmButton = showConfirmButton;
    }

    public boolean isShowCancelButton() {
        return isShowCancelButton;
    }

    public void setShowCancelButton(boolean showCancelButton) {
        isShowCancelButton = showCancelButton;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public SelectItemAdaptor getAdaptor() {
        return adaptor;
    }

    public void setAdaptor(SelectItemAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    private void init() {
        findViewById(R.id.content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adaptor == null) {
                    return;
                }
                String title = adaptor.getDialogTitle();
                if (title.equals("")) {
                    if (null == getDialogTitle() || getDialogTitle().equals(""))
                        title = getResources().getString(R.string.select_dialog_default_title);
                    else title = getDialogTitle();
                }
                MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(getContext()).setTitle(title);

                if (isShowConfirmButton) {
                    dialogBuilder.setPositiveButton(getResources().getString(R.string.confirm), (dialog, which) -> {
                        if (optionListener != null && which != adaptor.getSelectedOption()) {
                            optionListener.optionChange(v, adaptor.getOptions()[which], which);
                        }
                    });
                }
                if (isShowCancelButton) {
                    dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), (dialog, which) -> {

                    });
                }

                dialogBuilder.setSingleChoiceItems(adaptor.getOptions(), adaptor.getSelectedOption(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isShowConfirmButton && which != adaptor.getSelectedOption()) {
                            if (dialogInstance != null) {
                                dialogInstance.cancel();
                            }
                            if (optionListener != null) {
                                optionListener.optionChange(v, adaptor.getOptions()[which], which);
                            }
                        }
                    }
                });

                dialogInstance = dialogBuilder.show();
                if (getListener() != null) {
                    getListener().onClicked(v);
                }
            }
        });


    }

    public OptionListener getOptionListener() {
        return optionListener;
    }

    public void setOptionListener(OptionListener optionListener) {
        this.optionListener = optionListener;
    }

    public interface ClickListener {
        void onClicked(View view);
    }

    /**
     * 选项监听器
     */
    public interface OptionListener {

        /**
         * 选项更改时触发
         *
         * @param v      点击的视图
         * @param option 更改后的选项
         */
        void optionChange(View v, String option, int position);
    }
}