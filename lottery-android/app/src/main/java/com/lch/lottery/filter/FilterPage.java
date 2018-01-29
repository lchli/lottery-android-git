package com.lch.lottery.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.filter.cores.FilterFacade;
import com.lch.lottery.filter.cores.FilterFactory;
import com.lch.lottery.util.Utils;
import com.lch.netkit.common.tool.VF;
import com.rey.material.app.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lchli on 2016/3/26.
 */
public class FilterPage extends TabPage implements View.OnTouchListener {

    private EditText etDanma;
    private EditText etHewei;
    private EditText etKuadu;
    private EditText etErma;
    private EditText etShama;
    private GridView rongCuoRecycler;
    private View btStartFilter;

    private RoncuoAdapter roncuoAdapter;


    public FilterPage(@NonNull Context context) {
        super(context);
        init();
    }

    public FilterPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void init() {
        View.inflate(getContext(), R.layout.activity_filter, this);

        etDanma = VF.f(this, R.id.etDanma);
        etHewei = VF.f(this, R.id.etHewei);
        etKuadu = VF.f(this, R.id.etKuadu);
        etErma = VF.f(this, R.id.etErma);
        etShama = VF.f(this, R.id.etShama);
        rongCuoRecycler = VF.f(this, R.id.rongCuoRecycler);
        btStartFilter = VF.f(this, R.id.btStartFilter);

        btStartFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new FilterTask().execute();
            }
        });

        etDanma.setOnTouchListener(this);
        etKuadu.setOnTouchListener(this);
        etHewei.setOnTouchListener(this);
        etErma.setOnTouchListener(this);
        etShama.setOnTouchListener(this);

        roncuoAdapter = new RoncuoAdapter();
        rongCuoRecycler.setAdapter(roncuoAdapter);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.etDanma:
                        showNumberPickDialog(etDanma, FilterUtils.getDanmaNumbers());
                        break;
                    case R.id.etKuadu:
                        showNumberPickDialog(etKuadu, FilterUtils.getDanmaNumbers());
                        break;
                    case R.id.etHewei:
                        showNumberPickDialog(etHewei, FilterUtils.getDanmaNumbers());
                        break;
                    case R.id.etErma:
                        showNumberPickDialog(etErma, FilterUtils.getErmaNumbers());
                        break;
                    case R.id.etShama:
                        showNumberPickDialog(etShama, FilterUtils.getDanmaNumbers());
                        break;
                }

                break;


        }
        return false;
    }

    private class FilterTask extends AsyncTask<Void, Integer, List<String>> {
        String danma = etDanma.getText().toString();
        String kuadu = etKuadu.getText().toString();
        String hewei = etHewei.getText().toString();
        String erma = etErma.getText().toString();
        String shama = etShama.getText().toString();

        @Override
        protected List<String> doInBackground(Void... params) {
            FilterFacade filterFacade = new FilterFacade();
            if (!TextUtils.isEmpty(danma)) {
                filterFacade.addFilter(FilterFactory.newFilter(FilterFactory.FilterType.FILTER_DING_DANMA, FilterUtils.formatInputNumber(danma)));
            }
            if (!TextUtils.isEmpty(kuadu)) {
                filterFacade.addFilter(FilterFactory.newFilter(FilterFactory.FilterType.FILTER_DING_KUADU, FilterUtils.formatInputNumber(kuadu)));
            }
            if (!TextUtils.isEmpty(hewei)) {
                filterFacade.addFilter(FilterFactory.newFilter(FilterFactory.FilterType.FILTER_DING_HEWEI, FilterUtils.formatInputNumber(hewei)));
            }
            if (!TextUtils.isEmpty(erma)) {
                filterFacade.addFilter(FilterFactory.newFilter(FilterFactory.FilterType.FILTER_DING_ERMA, FilterUtils.formatInputNumber(erma)));
            }
            if (!TextUtils.isEmpty(shama)) {
                filterFacade.addFilter(FilterFactory.newFilter(FilterFactory.FilterType.FILTER_SHA_MA, FilterUtils.formatInputNumber(shama)));
            }
            List<String> result = filterFacade.runRongCuoFilter(FilterUtils.getBaseFilterNumbers(getContext()), roncuoAdapter.getCheckedValues());
            LogUtils.e("filter result:%s", result);
            return result;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            showFilterResultDialog(getResources().getString(R.string.filter_result, result != null ? result.size() : 0, result != null ? result.toString() : ""));
        }
    }

    private void updateRongcuoUi() {
        List<Integer> rouncuo = new ArrayList<>();
        int init = 0;
        rouncuo.add(init);
        if (!TextUtils.isEmpty(etDanma.getText().toString())) {
            rouncuo.add(++init);
        }
        if (!TextUtils.isEmpty(etKuadu.getText().toString())) {
            rouncuo.add(++init);
        }
        if (!TextUtils.isEmpty(etHewei.getText().toString())) {
            rouncuo.add(++init);
        }
        if (!TextUtils.isEmpty(etErma.getText().toString())) {
            rouncuo.add(++init);
        }
        if (!TextUtils.isEmpty(etShama.getText().toString())) {
            rouncuo.add(++init);
        }
        roncuoAdapter.refresh(rouncuo);
    }

    private void showNumberPickDialog(final EditText editText, List<String> numbers) {
        BottomSheetDialog mDialog = new BottomSheetDialog(getContext());
        final NumberAdapter numberAdapter = new NumberAdapter(getContext(), numbers);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                List<String> checked = numberAdapter.getCheckedValues();
                if (!checked.isEmpty()) {
                    editText.setError(null);
                    editText.setText(checked.toString());
                }
                updateRongcuoUi();
            }
        });
        View root = View.inflate(getContext(), R.layout.dialog_number_choice, null);
        RecyclerView recyclerview = (RecyclerView) root.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        recyclerview.addItemDecoration(new GridDividerDecoration(getContext()));
        recyclerview.setAdapter(numberAdapter);
        mDialog.contentView(root)
                .heightParam(getResources().getDimensionPixelSize(R.dimen.filter_numbercheck_dialog_height))
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
    }

    private void showFilterResultDialog(final String result) {
        BottomSheetDialog mDialog = new BottomSheetDialog(getContext());
        View root = View.inflate(getContext(), R.layout.dialog_filter_result, null);
        EditText etResult = (EditText) root.findViewById(R.id.etResult);
        etResult.setText(result);
        etResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Utils.copy(result, getContext());

                ToastUtils.showShort(R.string.copy_success);
                return true;
            }
        });
        mDialog.contentView(root)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
    }

}
