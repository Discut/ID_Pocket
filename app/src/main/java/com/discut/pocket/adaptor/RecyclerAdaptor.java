package com.discut.pocket.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.discut.pocket.R;
import com.discut.pocket.bean.account.Account;
import com.discut.pocket.bean.Tag;
import com.discut.pocket.component.AccountCard;
import com.discut.pocket.utils.ColorTransform;

import java.util.List;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.InnerHolder> {

    private final List<Account> mdata;
    private Context context;
    //private ChipGroup chipGroup;
    private LinearLayout chipGroup;
    private ItemClickListener listener;

    public RecyclerAdaptor(List<Account> mdata) {
        this.mdata = mdata;
    }

    public ItemClickListener getListener() {
        return listener;
    }

    public void setListener(ItemClickListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        AccountCard view = new AccountCard(context, null);//(AccountCard) AccountCard.inflate(context, R.layout.account_card_layout, null);
//        chipGroup = view.findViewById(R.id.account_card_chipGroup);
        chipGroup = view.findViewById(R.id.chips_box);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder: position" + position);
        holder.setData(mdata.get(position));
    }


    @Override
    public int getItemCount() {
        if (mdata != null) {
            return mdata.size();
        }
        return 0;
    }

    public abstract static class ItemClickListener {
        public abstract void onClick(View v, Account account);

        public void onLongClick(View v) {
        }
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView details;
        private final TextView account;
        private final AccountCard accountCard;
        //private final View item;
        /*        private final AccountCard view;*/

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            //((ConstraintLayout)itemView);
            title = itemView.findViewById(R.id.card_title);
            details = itemView.findViewById(R.id.card_details);
            account = itemView.findViewById(R.id.card_main);
            accountCard = (AccountCard) itemView;
            //item = itemView;
        }

        public void setData(Account listItem) {
            accountCard.setAccount(listItem);
            title.setText(listItem.getTitle());
            details.setText(listItem.getNote());
            account.setText(listItem.getAccount());
            itemView.findViewById(R.id.card_box).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v, listItem);
                    }
                }
            });

            if (null == listItem.getTags() || chipGroup.getChildCount() == listItem.getTags().length)
                return;
            for (Tag tag : listItem.getTags()) {
                @SuppressLint("ResourceType") TextView newChip =
                        (TextView) LayoutInflater.from(context).inflate(R.xml.chip_show, chipGroup, false);
                newChip.setText(tag.getName());
                newChip.setBackgroundTintList(ColorTransform.from(context.getResources(), R.color.purple_200));
                chipGroup.addView(newChip);
            }
        }
    }
}
