package com.lch.lottery.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lch.lottery.R;
import com.lch.netkit.common.base.AbsAdapter;
import com.lch.netkit.common.tool.VF;
import com.rey.material.widget.CheckBox;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by lchli on 2016/3/26.
 */
public class RoncuoAdapter extends AbsAdapter<Integer> {

    private List<Integer> mCheckedValues = new LinkedList<>();


    public List<Integer> getCheckedValues() {
        return mCheckedValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_number, parent, false);
        return new ViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(AbsViewHolder absViewHolder, int position) {
        final ViewHolder holder = (ViewHolder) absViewHolder;
        final Integer data = getItem(position);

        holder.checkBox.setText(String.valueOf(data));
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


    static class ViewHolder extends AbsViewHolder

    {
        CheckBox checkBox;
        View item;

        public ViewHolder(int viewType, View view) {
            super(viewType);
            checkBox = VF.f(view, R.id.checkBox);
            item = view;
        }

        @Override
        protected View getItemView() {
            return item;
        }
    }
}
