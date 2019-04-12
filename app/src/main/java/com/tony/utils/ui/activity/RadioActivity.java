package com.tony.utils.ui.activity;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.tony.utils.R;
import com.tony.utils.utils.ToastUtil;

import butterknife.BindView;

/**
 * 单选按钮
 *
 * @author Tony
 * @time 2019/4/11 10:24
 */
public class RadioActivity extends BaseActivity {
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_radio;
    }

    @Override
    protected void initView() {
        mContext=this;


    }

    @Override
    protected void initData() {
        //动态添加radiobutton
        for (int i = 0; i < 10; i++) {
            RadioButton tempButton = new RadioButton(this);
//            tempButton.setBackgroundResource(R.drawable.xxx);	// 设置RadioButton的背景图片
//            tempButton.setButtonDrawable(R.drawable.xxx);			// 设置按钮的样式
            tempButton.setPadding(80, 0, 0, 0); // 设置文字距离按钮四周的距离
            tempButton.setText("按钮 " + i);
            tempButton.setTag(i + "");
            radiogroup.addView(tempButton, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        //radiobutton点击事件
        radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                RadioButton tempButton = (RadioButton) findViewById(checkedId); // 通过RadioGroup的findViewById方法，找到ID为checkedID的RadioButton
                // 以下就可以对这个RadioButton进行处理了
                ToastUtil.show(tempButton.getText() + "tag:" + tempButton.getTag(), mContext);
            }
        });
    }

    @Override
    protected void initListener() {

    }

}
