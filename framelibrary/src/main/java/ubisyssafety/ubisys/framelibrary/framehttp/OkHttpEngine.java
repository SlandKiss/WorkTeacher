package ubisyssafety.ubisys.framelibrary.framehttp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ubisyssafety.ubisys.baselibray.http.EngineCallBack;
import ubisyssafety.ubisys.baselibray.http.HttpUtils;
import ubisyssafety.ubisys.baselibray.http.IHttpEngine;

/**
 * Created by Administrator on 2017/6/29.
 */

public class OkHttpEngine implements IHttpEngine {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    public void get(final boolean cache, Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        final String finalUrl = HttpUtils.jointParams(url, params);  //打印
        Log.e("Get请求路径：", finalUrl);
//       1.判断需不需要缓存，然后判断有没有
        if(cache){
             String resultJson=CacheDataUtil.getCacheResultJson(finalUrl);
             if (!TextUtils.isEmpty(resultJson)){
                 Log.e("TAG","以读到缓存");
//                 需要缓存 并且数据库中有缓存 直接就去执行
                 callBack.onSuccess(resultJson);
             }
        }
        Request.Builder requestBuilder=new Request.Builder().url(finalUrl).tag(context);
//        可以省略 默认是GET请求
        final Request request=requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultJson=response.body().string();
//              获取数据之后执行成功的方法
                if (cache){
//                    拿到缓存的数据
                    String cacheResultJson=CacheDataUtil.getCacheResultJson(finalUrl);
                    if (!TextUtils.isEmpty(resultJson)) {
//                       比对内容
                        if (resultJson.equals(cacheResultJson)){
//                           内容一样 不需要执行成功方法 刷新页面
                            return;
                        }
                    }
                }
                callBack.onSuccess(resultJson);
                Log.e("Get返回结果：", resultJson);
                if (cache){
//                  数据缓存
                    CacheDataUtil.cacheData(finalUrl,resultJson);
                }
            }
        });
    }
    @Override
    public void post(final boolean cache, Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {

        final String joinUrl=HttpUtils.jointParams(url,params);
        Log.e("post请求路径",joinUrl);
//        了解一些OkHttp
        RequestBody requestBody=appendBody(params);
        Request request= new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 这个 两个回掉方法都不是在主线程中
                String result = response.body().string();
                Log.e("Post返回结果：", result);
                callBack.onSuccess(result);
                if (cache){
//                    拿到缓存的数据
                    String cacheResultJson=CacheDataUtil.getCacheResultJson(result);
                    if (!TextUtils.isEmpty(result)) {
//                       比对内容
                        if (result.equals(cacheResultJson)){
//                           内容一样 不需要执行成功方法 刷新页面
                            return;
                        }
                    }
                }
                callBack.onSuccess(result);
                Log.e("Post返回结果：", result);
                if (cache){
//                  数据缓存
                    CacheDataUtil.cacheData(joinUrl,result);
                }
            }
        });
    }


    private RequestBody appendBody(Map<String,Object> params){
//        MultipartBody.Builder builder=new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
        FormBody.Builder builder=new FormBody.Builder();
        addParams(builder,params);
        return builder.build();
    }

    private void addParams(FormBody.Builder builder, Map<String, Object> params) {
        if (params!=null&&!params.isEmpty()){
            for (String key:params.keySet()){
                builder.add(key,params.get(key)+"");
                Object value=params.get(key);
                if (value instanceof File){
//                    处理文件
                    File file= (File) value;
                    builder.add(key,file.getName());
                }else if (value instanceof List){
//               代表提交的是 List集合\
                    List<File> listFiles= (List<File>) value;
                    for (int i = 0; i < listFiles.size(); i++) {
//                        获取文件
                        File file=listFiles.get(i);
                        builder.add(key + i, file.getName());
                    }
                }else {
                    builder.add(key,value + "");
                }
            }
        }
    }

    private String guessMimeType(String absolutePath) {
        FileNameMap fileNameMap= URLConnection.getFileNameMap();
        String contentTypeFor=fileNameMap.getContentTypeFor(absolutePath);
        if (contentTypeFor==null){
            contentTypeFor="application/octet-stream";
        }
        return contentTypeFor;
    }
}
