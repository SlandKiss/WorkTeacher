package ubisyssafety.ubisys.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public interface IDaoSupport<T> {

    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);
//    插入数据

    public long insert(T t);

//   批量插入数据  检测性能
    public void insert(List<T> datas);

//    获取专门查询的支持类
     QuerySupport<T> querySupport();

//    按照语法查询
    int delete(String whereClause, String... whereArgs);


    int update(T obj, String whereClause, String... whereArgs);
}
