package com.ly.includelibrary.after.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ly.includelibrary.R;
import com.ly.includelibrary.after.activity.HomeActivity;
import com.ly.rxlibrary.base.adapter.recylerview.BaseQuickAdapter;
import com.ly.rxlibrary.base.adapter.recylerview.BaseViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class HomeAdapter extends BaseQuickAdapter<Map<String,Class>,BaseViewHolder> {


    public HomeAdapter(@LayoutRes int layoutResId, @Nullable List<Map<String, Class>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Map<String, Class> item) {
        for (String s :item.keySet()) {
            ((TextView)(helper.getView(R.id.home_bt))).setText(s);
            Class aClass=item.get(s);
            helper.getView(R.id.home_bt).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(helper.itemView.getContext(), aClass);
                    helper.itemView.getContext().startActivity(intent);
                }
            });
        }

    }
}
