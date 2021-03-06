package com.ycl.car.contract;

import com.ycl.car.BasePresenter;
import com.ycl.car.BaseView;

/**
 * 登录契约类
 * Created by y11621546 on 2017/1/21.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showLoading();

        void dismissLoading();

        void onSuccess();

        void onError();

        void showWarnMsg(String msg);
    }

    interface Presenter extends BasePresenter {
        void login(String userName, String userPwd);
    }
}
