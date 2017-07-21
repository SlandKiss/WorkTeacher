package ubisyssafety.ubisys.baselibray.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * Created by llf on 2017/2/27.
 * 基础的Application
 */

public class BaseApplication extends MultiDexApplication {
    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static BaseApplication instance;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
       super.onCreate();
        instance = this;

//        腾讯bug统计
//        CrashReport.initCrashReport(getApplicationContext());

        //检查内存泄漏
        refWatcher = LeakCanary.install(this);
    }

    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }
}
