package com.ly.includelibrary.after.mvp.contract;

import com.ly.includelibrary.after.mvp.BaseView;

/**
 * Created by CodeManLY on 2017/7/12 0012.
 */

public interface RegisterContract {
    interface Model {
        void registerSuccess();
        void registerFaile();
    }

    interface View extends BaseView {
        String getPd();
        String getName();
        String showResualt();
    }

    interface Presenter {
        void register();
    }
}
