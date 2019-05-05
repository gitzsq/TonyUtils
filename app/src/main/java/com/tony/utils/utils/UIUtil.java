package com.tony.utils.utils;

import android.content.Context;
import android.widget.Toast;

import com.tony.utils.customview.MyProgressDialog;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class UIUtil {

	private static MyProgressDialog dialog;

	private UIUtil() {
	}

	public static void showToast(Context c, int resId) {
		Toast.makeText(c, resId, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context c, String desc) {
		Toast.makeText(c, desc, Toast.LENGTH_SHORT).show();
	}

	public static void showProgressDialog(Context c, String msg) {
		dialog = new MyProgressDialog(c,msg);
		dialog.show();
	}

	public static void showProgressDialog(Context c, int resId) {
		dialog = new MyProgressDialog(c,resId);
		dialog.show();
	}

	public static void cancelProgressDialog() {
		if (dialog != null) {
			dialog.cancel();
			dialog = null;
		}
	}

	public static String timeStamp2Date(String seconds) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		String format = "yyyy/MM/dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds)));
	}
}