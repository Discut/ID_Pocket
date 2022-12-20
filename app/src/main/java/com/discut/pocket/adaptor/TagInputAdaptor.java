package com.discut.pocket.adaptor;

import android.view.View;

import androidx.annotation.NonNull;

import com.discut.pocket.adaptor.base.BaseTagInputAdaptor;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.component.TagInputView;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TagInputAdaptor extends BaseTagInputAdaptor<Tag> {
    private final List<Tag> mData;
    private TagInputView view;

    public TagInputAdaptor(@NonNull Tag[] mData) {
        this.mData = new ArrayList<>(Arrays.asList(mData));
    }
    public TagInputAdaptor(@NonNull List<Tag> mData){
        this.mData = mData;
    }
    public TagInputAdaptor(){
        this(new Tag[0]);
    }

    @Override
    public int getChipsCount() {
        return mData.size();
    }

    @Override
    public Chip onBindDate(Chip chip, int position) {
        chip.setText(mData.get(position).getName());
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = ((Chip) v).getText();
                Iterator<Tag> iterator = mData.iterator();
                int index = 0;
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getName().contentEquals(text)) {
                        index = i;
                        break;
                    }
                }
                mData.remove(index);
                view.update();
            }
        });
        return chip;
    }

    @Override
    public void onCreateView(TagInputView view) {
        this.view = view;
    }

    @Override
    public BaseTagInputAdaptor.ChipsListener getListener() {
        return text -> {
            Tag tag = new Tag();
            tag.setName(text);
            this.mData.add(tag);
        };
    }

    @Override
    public List<Tag> getTag() {
        return mData;
    }


}
