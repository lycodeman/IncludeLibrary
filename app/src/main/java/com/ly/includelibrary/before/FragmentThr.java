package com.ly.includelibrary.before;


import android.support.v4.app.Fragment;
import android.view.View;

import com.ly.includelibrary.R;
import com.ly.rxlibrary.base.fragment.BaseLazyFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThr extends BaseLazyFragment {


    public FragmentThr() {
        // Required empty public constructor
    }

    @Override
    public int requestLayout() {
        return R.layout.fragment_fragment_thr;
    }

    @Override
    public void fetchData() {
        toast("thr"+isVisible+"=="+isPrepared+"=="+isFirstLoad);
    }

    @Override
    protected void processClick(View view) {

    }

}
