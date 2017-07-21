package ubisyssafety.ubisys.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/6/29.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class DaoSupport<T> implements IDaoSupport<T> {
//   SQLiteDatabase
    private SQLiteDatabase mSqliteDatabase;
//    泛型类
    private Class<T> mClazz;

    private static final Object[] mPutMethodArgs=new Object[2];

    private static final Map<String,Method> mPutMethods=new ArrayMap<>();

    private String TAG = "DaoSupport";
    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSqliteDatabase=sqLiteDatabase;
        this.mClazz=clazz;
        // 创建表
        /*"create table if not exists Person ("
                + "id integer primary key autoincrement, "
                + "name text, "
                + "age integer, "
                + "flag boolean)";*/

        StringBuffer sb=new StringBuffer();

        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append(" (id integer primary key autoincrement, ");

        Field[] fields=mClazz.getDeclaredFields();
        for (Field field:fields){
            field.setAccessible(true);
            String name=field.getName();
            String type=field.getType().getSimpleName();
            sb.append(name).append(DaoUtil.getColumnType(type)).append(", ");
        }
        sb.replace(sb.length()-2,sb.length(),")");

        String createTableSql=sb.toString();
        Log.e(TAG, "表语句--> " + createTableSql);
        mSqliteDatabase.execSQL(createTableSql);
    }


    @Override
    public long insert(T obj) {
        // 使用的其实还是  原生的使用方式，只是我们是封装一下而已
        ContentValues values=contentValuesByObj(obj);
        return mSqliteDatabase.insert(DaoUtil.getTableName(mClazz),null,values);
    }
//    将obj转成ContentValues
    private ContentValues contentValuesByObj(T obj) {
//
        ContentValues values=new ContentValues();

//        封装values  getDeclaredFields()获取自己声明的各种字段，包括public，protected，private
        Field[] fields=mClazz.getDeclaredFields();

        for (Field field:fields){
            try {
//              设置权限私有的共有的都可以访问
                field.setAccessible(true);
                String key=field.getName();
//              获取value 取得obj对象这个Field上的值
                Object value=field.get(obj);

                mPutMethodArgs[0]=key;
                mPutMethodArgs[1]=value;
//               方法使用反射 反射在一定程度上会影响性能
//                源码里面  activity实例 反射  View创建反射

                String filedTypeName=field.getType().getName();

                Method putMethod=mPutMethods.get(filedTypeName);
                if (putMethod==null){
                    putMethod = ContentValues.class.getDeclaredMethod("put",
                            String.class, value.getClass());
                    mPutMethods.put(filedTypeName, putMethod);
                }
                // 通过反射执行
                putMethod.invoke(values, mPutMethodArgs);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                mPutMethodArgs[0]=null;
                mPutMethodArgs[1]=null;
            }
        }
        return values;
    }

    @Override
    public void insert(List<T> datas) {
//        批量插入采用 事务
        mSqliteDatabase.beginTransaction();
        for (T data:datas){
//         调用单条插入
            insert(data);
        }
        mSqliteDatabase.setTransactionSuccessful();
        mSqliteDatabase.endTransaction();
    }

    private QuerySupport<T> mQuerySupport;
    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport==null){
            mQuerySupport=new QuerySupport<>(mSqliteDatabase,mClazz);
        }
        return mQuerySupport;
    }

    @Override
    public int delete(String whereClause, String... whereArgs) {
        return mSqliteDatabase.delete(DaoUtil.getTableName(mClazz),whereClause,whereArgs);
    }

    @Override
    public int update(T obj, String whereClause, String... whereArgs) {
        ContentValues values=contentValuesByObj(obj);
        return mSqliteDatabase.update(DaoUtil.getTableName(mClazz),values,whereClause,whereArgs);
    }
}
