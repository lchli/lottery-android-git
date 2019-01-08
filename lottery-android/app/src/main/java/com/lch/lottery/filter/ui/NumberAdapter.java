package com.lch.lottery.filter.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lch.lottery.R;
import com.lchli.utils.tool.VF;
import com.rey.material.widget.CheckBox;

import java.util.LinkedList;
import java.util.List;


public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private List<String> mValues;
    private List<String> mCheckedValues = new LinkedList<>();

    public List<String> getCheckedValues() {
        return mCheckedValues;
    }

    public void update(List<String> items) {
        mValues = items;
        notifyDataSetChanged();
    }


    public NumberAdapter(Context context, List<String> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_number, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String data = mValues.get(position);
        holder.checkBox.setText(data);
        holder.checkBox.setChecked(mCheckedValues.contains(data));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    mCheckedValues.add(data);
                } else {
                    mCheckedValues.remove(data);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;


        public ViewHolder(View view) {
            super(view);
            checkBox = VF.f(view, R.id.checkBox);
        }


    }

}