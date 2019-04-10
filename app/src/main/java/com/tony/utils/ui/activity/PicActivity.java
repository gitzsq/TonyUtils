package com.tony.utils.ui.activity;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.tony.utils.R;
import com.tony.utils.app.AppConstants;
import com.tony.utils.ui.adapter.GridAdapter;
import com.tony.utils.utils.PermissionsUtils;
import com.tony.utils.utils.ToastUtil;
import com.tony.utils.utils.pic.pictools.Bimp;
import com.tony.utils.utils.pic.picviews.PhotoActivity;
import com.tony.utils.utils.pic.picviews.TestPicActivity;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;

import java.io.File;

public class PicActivity extends BaseActivity {
    private GridView noScrollgridview;
    private GridAdapter adapter;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic;
    }

    @Override
    protected void initView() {
        mContext = this;
        InitPic();// 初始化照片控件
    }

    @Override
    protected void initData() {
        //两个日历权限和一个数据读写权限
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
//        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    @Override
    protected void initListener() {

    }

    // 初始化照片控件
    public void InitPic() {
        // 清空Bimp
        Bimp.bmp.clear();
        Bimp.max = 0;
        Bimp.drr.clear();
        Bimp.act_bool = false;

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        //		adapter.update1();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(mContext, noScrollgridview);
                } else {
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

    }

    // 照片弹窗_Tony
    public class PopupWindows extends PopupWindow {

        public PopupWindows(final Context mContext, View parent) {

            super(mContext);

            View view = View.inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

            setWidth(LinearLayout.LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    photo();// 拍照
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // 相册选择
                    Intent intent = new Intent(mContext, TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
    }

    // 拍照方法
    public void photo() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(Environment.getExternalStorageDirectory() + "/myimage/");
            if (!dir.exists())
                dir.mkdirs();

            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(dir, String.valueOf(System.currentTimeMillis()) + ".jpg");
            path = file.getPath();
//            Uri imageUri = Uri.fromFile(file);
            Uri imageUri = null;
            //加载路径
            if (android.os.Build.VERSION.SDK_INT < 24) {
                imageUri = Uri.fromFile(new File(path));
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, path);
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }

            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            ToastUtil.show("没有储存卡", mContext);
        }
    }

    protected void onRestart() {
        super.onRestart();
        // 刷新图片适配器列表
        adapter.update1();
    }

    // 界面回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:// 拍照回调
                if (Bimp.drr.size() < AppConstants.PIC_MAX_NUM && resultCode == -1) {
                    Bimp.drr.add(path);
                }

                break;
        }
    }

    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            ToastUtil.show("权限通过，可以做其他事情!", mContext);
        }

        @Override
        public void forbitPermissons() {
            ToastUtil.show("权限不通过!", mContext);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 清空Bimp
        Bimp.bmp.clear();
        Bimp.max = 0;
        Bimp.drr.clear();
        Bimp.act_bool = false;
    }
}
