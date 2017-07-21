package ubisyssafety.ubisys.framelibrary.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import ubisyssafety.ubisys.framelibrary.R;


public class FillSystemUtil {
	private Activity mActivity;

	public FillSystemUtil(Activity activity) {
		// TODO Auto-generated constructor stub
		mActivity = activity;
	}

	/**
	 * 沉浸式状态栏
	 */
	public void fillSystemBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(mActivity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.tree_green);// 通知栏所需颜色

	}

	/**
	 * 沉浸式状态栏颜色改为透明
	 */
	public void fillSystemBarTM() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(mActivity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.toolbar_subtitle);// 通知栏所需颜色
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = mActivity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

}
