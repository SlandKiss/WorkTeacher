package ubisyssafety.ubisys.baselibray.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/28.
 */

public interface EngineCallBack {
//   开始执行
    public void onPreExecute(Context context, Map<String, Object> params);
//    错误
    public void onError(Exception e);
//    成功 返回对象会出问题   成功  data对象{"",""}  失败  data:""
    public void onSuccess(String result);
//    默认
    public final EngineCallBack DEFUALT_CALL_BANK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }
        @Override
         public void onError(Exception e) {

        }
        @Override
        public void onSuccess(String result) {

        }
    };
}
