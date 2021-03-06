package com.tony.utils.utils.pic.pictools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class FileUtils
{

	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/formats/";

	public static void saveBitmap(Bitmap bm, String picName)
	{
		Log.e("", "保存图片");
		try
		{
			if (!isFileExist(""))
			{
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			if (f.exists())
			{
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			try {
			
				if(bm!=null){
					bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
				}else{

				}
			} catch (Exception e) {
				// TODO: handle exception
				out.flush();
				out.close();
				e.printStackTrace();
			}
			out.flush();
			out.close();
			Log.e("", "已经保存");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static File createSDDir(String dirName) throws IOException
	{
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName)
	{
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	public static void delFile(String fileName)
	{
		File file = new File(SDPATH + fileName);
		if (file.isFile())
		{
			file.delete();
		}
		file.exists();
	}

	public static void deleteDir()
	{
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles())
		{
			if (file.isFile())
				file.delete(); // 删除�?有文�?
			else if (file.isDirectory())
				deleteDir(); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	public static boolean fileIsExists(String path)
	{
		try
		{
			File f = new File(path);
			if (!f.exists())
			{
				return false;
			}
		} catch (Exception e)
		{

			return false;
		}
		return true;
	}
	public interface GlideLoadBitmapCallback {
		public void getBitmapCallback(Bitmap bitmap);
	}

	public static void getBitmap(Context context, String uri, final GlideLoadBitmapCallback callback) {
		Glide.with(context).load(uri).asBitmap().into(new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
				callback.getBitmapCallback(resource);
			}
		});

	}

}
