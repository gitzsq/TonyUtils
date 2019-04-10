package com.tony.utils.utils.pic.picviews;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.tony.utils.R;
import com.tony.utils.app.AppConstants;
import com.tony.utils.app.TonyApplication;
import com.tony.utils.ui.activity.PicActivity;
import com.tony.utils.utils.pic.pictools.AlbumHelper;
import com.tony.utils.utils.pic.pictools.Bimp;
import com.tony.utils.utils.pic.pictools.ImageGridAdapter;
import com.tony.utils.utils.pic.pictools.ImageGridAdapter.TextCallback;
import com.tony.utils.utils.pic.pictools.ImageItem;

/**
 * @author yyszsq2017年6月15日
 * 相册小类界面
 */
public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";

    // ArrayList<Entity> dataList;//鐢ㄦ潵瑁呰浇鏁版嵁婧愮殑鍒楄〃
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣�
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ImageGridActivity.this, "最多选择" + AppConstants.PIC_MAX_NUM + "张图片", 400).show();
                    break;

                default:
                    break;
            }
        }
    };

    private TextView pic_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_grid);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList =  TonyApplication.getImageList();
        Collections.reverse(dataList);//颠倒list顺序

        initView();
        //取消
        pic_back_btn = (TextView) findViewById(R.id.pic_back_btn);
        pic_back_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        //确定按钮
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }
                if (Bimp.act_bool) {
                    //需加判断
                    Intent intent = new Intent(ImageGridActivity.this,
                            PicActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Bimp.act_bool = false;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (Bimp.drr.size() < AppConstants.PIC_MAX_NUM) {
                        Bimp.drr.add(list.get(i));
                    }


                }
                finish();
            }

        });
    }

    /**
     * 鍒濆鍖杤iew瑙嗗浘
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                adapter.notifyDataSetChanged();
            }

        });

    }
}
