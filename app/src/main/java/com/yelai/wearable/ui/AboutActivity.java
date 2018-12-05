package com.yelai.wearable.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.yelai.wearable.AppManager;
import com.yelai.wearable.R;
import com.yelai.wearable.base.BaseActivity;
import com.yelai.wearable.base.WebActivity;
import com.yelai.wearable.model.User;
import com.yelai.wearable.present.PViod;

import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;

/**
 * Created by wanglei on 2016/12/31.
 */
public class AboutActivity extends BaseActivity<PViod> {


    @Override
    public void initData(Bundle savedInstanceState) {
        initToolbar();
    }

    private void initToolbar() {
        showToolbar();
        mToolbar.setLeftIcon(R.drawable.ic_arrow_white_24dp);
        mToolbar.setMiddleText("关于XDroidMvp");
    }

    @OnClick({
            R.id.tv_githubMvc,
            R.id.tv_githubMvp
    })
    public void clickEvent(View view) {
        switch (view.getId()) {

            case R.id.tv_githubMvc:
                WebActivity.launch(context, "https://github.com/limedroid/XDroid", "XDroid");
                break;

            case R.id.tv_githubMvp:
                WebActivity.launch(context, "https://github.com/limedroid/XDroidMvp", "XDroid");
                break;
        }


        Observable<String> ObservablePassword = Observable.create(new ObservableOnSubscribe(){
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {



            }
        });


        RxTextView.textChanges(null).map(new Function<CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence) throws Exception {
                return false;
            }
        }).subscribe();


        Observable.combineLatest(
                RxTextView.textChanges(null),
                RxTextView.textChanges(null),
                RxTextView.textChanges(null),
                RxTextView.textChanges(null),

                new Function4<CharSequence, CharSequence, CharSequence, CharSequence, Object>() {
                    @Override
                    public Object apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4) throws Exception {
                        return null;
                    }
                }

        ).subscribe();



        Observable.combineLatest(
                Observable.just("123"),
                Observable.just(123),
                new BiFunction<String, Integer, Boolean>() {
                    @Override
                    public Boolean apply(String s, Integer integer) throws Exception {
                        return true;
                    }
                }
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        });


        Gson gson = new Gson();

        User user = gson.fromJson("", User.class);


    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(AboutActivity.class)
                .data(new Bundle())
                .launch();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public PViod newP() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            AppManager.finishCurrentActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}