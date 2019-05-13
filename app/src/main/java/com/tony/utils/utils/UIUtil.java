package com.tony.utils.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
    //edittext输入监听
	public static void showKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}

	public static void hideKeyboard(View view) {
		InputMethodManager manager = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	//改变状态栏颜色
	@SuppressLint("NewApi")
	public static void setStatusBar(Window window, int color) {
		//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		//设置状态栏颜色
		window.setStatusBarColor(color);
	}
}
