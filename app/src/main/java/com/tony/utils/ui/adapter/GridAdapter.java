package com.tony.utils.ui.adapter;

import java.io.IOException;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.tony.utils.R;
import com.tony.utils.app.AppConstants;
import com.tony.utils.utils.pic.pictools.Bimp;
import com.tony.utils.utils.pic.pictools.FileUtils;


public class GridAdapter extends BaseAdapter
{
	private LayoutInflater inflater; // ��ͼ����
	private int selectedPosition = -1;// ѡ�е�λ��
	private boolean shape;
	private Context context;

	public boolean isShape()
	{
		return shape;
	}

	public void setShape(boolean shape)
	{
		this.shape = shape;
	}

	public GridAdapter(Context context)
	{
		this.context=context;
		inflater = LayoutInflater.from(context);
	}

	public void update1()
	{
		loading1();
	}

	public int getCount()
	{
	
		    return (Bimp.bmp.size() + 1);
		
	}

	public Object getItem(int arg0)
	{

		return null;
	}

	public long getItemId(int arg0)
	{

		return 0;
	}

	public void setSelectedPosition(int position)
	{
		selectedPosition = position;
	}

	public int getSelectedPosition()
	{
		return selectedPosition;
	}


	public View getView(int position, View convertView, ViewGroup parent)
	{
		//final int coord = position;
		ViewHolder holder = null;
		
//		System.out.println("�����±�="+position);
		if (convertView == null)
		{

			convertView = inflater.inflate(R.layout.item_published_grida,
					parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.item_grida_image);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.image.setVisibility(View.VISIBLE);
	
		if (position == Bimp.bmp.size())
		{
			holder.image.setImageBitmap(BitmapFactory.decodeResource(
					this.context.getResources(), R.drawable.icon_addpic_unfocused));
			
		} else
		{
			holder.image.setImageBitmap(Bimp.bmp.get(position));
		}
		
		if (position == AppConstants.PIC_MAX_NUM)
		{
			holder.image.setVisibility(View.GONE);
		}

		return convertView;
	}

	public class ViewHolder
	{
		public ImageView image;
	}

	Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 1:
				notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};

	public void loading1()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					if (Bimp.max == Bimp.drr.size())
					{
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else
					{
						try
						{
							String path = Bimp.drr.get(Bimp.max);
							System.out.println(path);
							Bitmap bm = Bimp.revitionImageSize(path);
							Bimp.bmp.add(bm);
							String newStr = path.substring(
									path.lastIndexOf("/") + 1,
									path.lastIndexOf("."));
							FileUtils.saveBitmap(bm, "" + newStr);
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						} catch (IOException e)
						{

							String path = Bimp.drr.get(Bimp.max);
							Bimp.bmp.add(null);
							String newStr = path.substring(
									path.lastIndexOf("/") + 1,
									path.lastIndexOf("."));
							FileUtils.saveBitmap(null, "" + newStr);
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
					
				}
			}
		}).start();
	}
}
