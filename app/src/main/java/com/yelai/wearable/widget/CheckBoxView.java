package com.yelai.wearable.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yelai.wearable.R;

/**
 * Created by wanglei on 2016/12/31.
 */

public class CheckBoxView extends LinearLayout {

    public ImageView checkBox;
    public TextView textView;

//    public CheckBoxView(Context context) {
//        super(context);
//        setupView(context);
//    }
//
    public CheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxView);

        String text = a.getString(R.styleable.CheckBoxView_text);

        textView.setText(text);
    }

    public CheckBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setupView(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CheckBoxView, defStyleAttr, 0);

        String text = a.getString(R.styleable.CheckBoxView_text);

        textView.setText(text);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.check_box_view, this);

        checkBox = findViewById(R.id.checkBox);
        textView = findViewById(R.id.textView);


//        this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
////                if(!buttonView.isEnabled())return;
////                setChecked(isChecked);
//
//            }
//        });

//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!isEnabled())return;
//                setChecked(!isChecked());
//            }
//        });

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        checkBox.setEnabled(enabled);

        if(!enabled){
            checkBox.setBackgroundResource(R.drawable.exa_icon_check_disable);
            textView.setBackgroundResource(R.drawable.ck_btn_background_disable_3);
            textView.setTextColor(ContextCompat.getColor(getContext(),  R.color.text_color_c));
        }

    }

    public void setChecked(boolean checked){
//        this.checkBox.setChecked(checked);

        this.checked = checked;
        textView.setTextColor(ContextCompat.getColor(getContext(), checked ? R.color.tab_text_selected : R.color.text_color_6));
        textView.setBackgroundResource(checked ? R.drawable.ck_btn_background_checked_3 : R.drawable.ck_btn_background_normal_3);
        checkBox.setBackgroundResource(checked ? R.drawable.exa_icon_check_check:R.drawable.exa_icon_check_normal);
    }

    private boolean checked;

    public boolean isChecked(){
        return checked;
    }

}
