package com.tony.utils.utils.pic.picviews;

import java.util.Collections;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.tony.utils.R;
import com.tony.utils.app.TonyApplication;
import com.tony.utils.utils.pic.pictools.AlbumHelper;
import com.tony.utils.utils.pic.pictools.ImageBucket;
import com.tony.utils.utils.pic.pictools.ImageBucketAdapter;


public class TestPicActivity extends Activity {
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;
	AlbumHelper helper;
	private TextView pic_back_btn;
	public static Bitmap bimap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_bucket);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		initData();
		initView();
	}

	private void initData()
	{

		dataList = helper.getImagesBucketList(true);
		Collections.reverse(dataList);//�ߵ�list˳��
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	private void initView()
	{
		pic_back_btn=(TextView)findViewById(R.id.pice_back_btn);
		pic_back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageBucketAdapter(TestPicActivity.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{

				Intent intent = new Intent(TestPicActivity.this,
						ImageGridActivity.class);
				for (int i = 0; i < dataList.get(position).imageList.size(); i++) {
					dataList.get(position).imageList.get(i).isSelected=false;
				}
				TonyApplication.setImageList(dataList.get(position).imageList);
				startActivity(intent);
				finish();
			}

		});
	}
}
