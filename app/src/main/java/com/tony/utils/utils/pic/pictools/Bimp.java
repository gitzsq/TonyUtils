package com.tony.utils.utils.pic.pictools;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

public class Bimp
{
	public static int max = 0;
	public static boolean act_bool = true;
	public static List<Bitmap> bmp = new ArrayList<Bitmap>();
	public static List<String> drr = new ArrayList<String>();
	



//	public static Bitmap revitionImageSize(String path) throws IOException
//	{
//		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
//				new File(path)));
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeStream(in, null, options);
//		in.close();
//		int i = 0;
//		Bitmap bitmap = null;
//		while (true)
//		{
//			if ((options.outWidth >> i <= 1000)
//					&& (options.outHeight >> i <= 1000))
//			{
//				in = new BufferedInputStream(
//						new FileInputStream(new File(path)));
//				options.inSampleSize = (int) Math.pow(2.0D, i);
//				options.inJustDecodeBounds = false;
//				bitmap = BitmapFactory.decodeStream(in, null, options);
//				break;
//			}
//			i += 1;
//		}
//		return bitmap;
//	}
    //图片占用内存压缩
	public static Bitmap revitionImageSize(String path) throws IOException
	{
		Bitmap	bit = BitmapFactory.decodeFile(path);

		Log.i("wechat", "压缩前图片的大小" + (bit.getByteCount() / 1024 / 1024)
				+ "M宽度为" + bit.getWidth() + "高度为" + bit.getHeight());
		Matrix matrix = new Matrix();
		matrix.setScale(0.5f, 0.5f);
		Bitmap bm = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
				bit.getHeight(), matrix, true);
		Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
				+ "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());
		return bm;
	}

}
