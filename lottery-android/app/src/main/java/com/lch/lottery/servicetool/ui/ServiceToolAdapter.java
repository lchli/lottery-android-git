package com.lch.lottery.servicetool.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.lottery.R;
import com.lch.lottery.servicetool.datainterface.ServiceTool;
import com.lchli.utils.base.BaseRecyclerAdapter;
import com.lchli.utils.tool.VF;

/**
 * Created by lichenghang on 2019/1/6.
 */

public class ServiceToolAdapter extends BaseRecyclerAdapter<ServiceTool> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new Vh(View.inflate(viewGroup.getContext(), R.layout.item_service_tool, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder absViewHolder, int position) {
        Vh vh = (Vh) absViewHolder;
        final ServiceTool data = getItem(position);
        if (data == null) {
            return;
        }

        vh.tvDesc.setText(data.getDesc());
        vh.ivIcon.setImageResource(data.getIconResId());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.launch();
            }
        });

    }


    private class Vh extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvDesc;

        public Vh(View itemView) {
            super(itemView);
            ivIcon = VF.f(itemView, R.id.ivIcon);
            tvDesc = VF.f(itemView, R.id.tvDesc);
        }


    }
}
