package com.ly.includelibrary.before;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ly.includelibrary.R;
import com.ly.rxlibrary.base.fragment.BaseLazyFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends BaseLazyFragment {


    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public int requestLayout() {
        return R.layout.fragment_fragment_one;
    }

    @Override
    public void fetchData() {
        toast("one"+isVisible+"=="+isPrepared+"=="+isFirstLoad);
    }

    @Override
    protected void processClick(View view) {

    }


}
