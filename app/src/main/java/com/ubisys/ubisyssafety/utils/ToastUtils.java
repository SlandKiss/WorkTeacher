package com.ubisys.ubisyssafety.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

	/**
	 * Toast
	 */
	public static void showToastIncenter(Context context, String text) {
		Toast a = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		a.setGravity(Gravity.CENTER, 0, 0);
		a.show();
	}

	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}
