package cn.droidlover.xdroidmvp.base;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.R;

/**
 * Date:2018/1/25 <p>
 * Author:chenzehao@danale.com <p>
 * Description:
 */

public class SimpleToolbar extends RelativeLayout {


    ImageView ivLeft;
    TextView tvLeft;
    TextView tvMiddle;
    ImageView ivRight;
    TextView tvRight;
    View divider;
    RelativeLayout rlRoot;

    public SimpleToolbar(Context context) {
        this(context, null);
    }

    public SimpleToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        inflate(getContext(), R.layout.layout_toolbar, this);
        ivLeft = findViewById(R.id.iv_left);
        tvLeft = findViewById(R.id.tv_left);
        tvMiddle = findViewById(R.id.tv_middle);
        ivRight = findViewById(R.id.iv_right);
        tvRight = findViewById(R.id.tv_right);
        divider = findViewById(R.id.divider);
        rlRoot = findViewById(R.id.rl_root);



        ivLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onIvLeftClicked();
            }
        });

        ivRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onIvRightClicked();
            }
        });

        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTvRightClicked();
            }
        });

        tvLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTvLeftClicked();
            }
        });


    }

    public void onIvLeftClicked() {
        if (onToolbarActionsClickListener != null) {
            onToolbarActionsClickListener.onToolbarActionsClick(LEFT_ICON, ivLeft);
        }
    }

    public void onIvRightClicked() {
        if (onToolbarActionsClickListener != null) {
            onToolbarActionsClickListener.onToolbarActionsClick(RIGHT_ICON, ivRight);
        }
    }

    public void onTvRightClicked() {
        if (onToolbarActionsClickListener != null) {
            onToolbarActionsClickListener.onToolbarActionsClick(RIGHT_TEXT, tvRight);
        }
    }

    public void onTvLeftClicked(){
        if (onToolbarActionsClickListener != null) {
            onToolbarActionsClickListener.onToolbarActionsClick(LEFT_TEXT, tvLeft);
        }
    }
    public void setIvLeftVisibility(int visibility) {
        ivLeft.setVisibility(visibility);
    }

    public void setTvLeftVisibility(int visibility){
        tvLeft.setVisibility(visibility);
    }

    public void setLeftIcon(int resId) {
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setImageResource(resId);
    }

    public void setMiddleText(String text, int color) {
        tvMiddle.setVisibility(View.VISIBLE);
        tvMiddle.setText(text);
        tvMiddle.setTextColor(color);
    }

    public void setMiddleText(String text){
        tvMiddle.setVisibility(View.VISIBLE);
        tvMiddle.setText(text);
        tvMiddle.setTextColor( Color.parseColor("#000000"));
    }

    public TextView getTvRight(){
        return tvRight;
    }

    public void setRightIcon(int resId) {
        tvRight.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(resId);
    }

    public void setRightText(String text, int color) {
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(text);
        tvRight.setTextColor(color);
    }

    public void setRightTextVisibility(int visibility) {
        tvRight.setVisibility(visibility);

    }

    public void setRightText(String text){
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(text);
        tvRight.setTextColor( Color.parseColor("#000000"));
    }

    public void setLeftText(String text, int color) {
        ivLeft.setVisibility(View.GONE);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
        tvLeft.setTextColor(color);
    }

    public void setLeftText(String text){
        ivLeft.setVisibility(View.GONE);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText(text);
        tvLeft.setTextColor( Color.parseColor("#000000"));
    }



    public void setDividerVisible(boolean visible) {
        divider.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setBackgroundColor(int color) {
        rlRoot.setBackgroundColor(color);
    }

    public interface OnToolbarActionsClickListener {
        void onToolbarActionsClick(int which, View view);
    }

    public static final int LEFT_ICON = 0;
    public static final int MIDDLE_TEXT = 1;
    public static final int RIGHT_ICON = 2;
    public static final int RIGHT_TEXT = 3;
    public static final int LEFT_TEXT = 4;

    private OnToolbarActionsClickListener onToolbarActionsClickListener;

    public void setOnToolbarActionsClickListener(OnToolbarActionsClickListener listener) {
        onToolbarActionsClickListener = listener;
    }


}
