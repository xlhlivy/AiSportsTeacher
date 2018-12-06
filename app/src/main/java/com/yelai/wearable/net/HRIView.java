package com.yelai.wearable.net;

import cn.droidlover.xdroidmvp.mvp.IView;

/**
 * Created by hr on 18/11/8.
 */

public interface HRIView<P> extends IView<P> {

    default void showError(String msg){}

    default void showIndicator(){}

    default void hideIndicator(){}

}
