package cn.droidlover.xdroidmvp.mvp;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IView<P> {
    void bindUI(View rootView);

    void bindEvent();

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    P newP();

    //声明性接口,只用于绑定声明周期
    <T> LifecycleTransformer<T> bindToLifecycle();


    }
