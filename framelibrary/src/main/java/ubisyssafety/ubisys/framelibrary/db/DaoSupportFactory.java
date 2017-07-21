package ubisyssafety.ubisys.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/6/29.
 */

public class DaoSupportFactory {

    private static DaoSupportFactory mFactory;

//    持有外部数据库的引用
    private SQLiteDatabase mSqLiteDatebase;

    public DaoSupportFactory() {
//        把数据库存放在内存卡上面 判断是否有存储卡 6.0 要动态的添加申请权限
        File dbRoot=new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +File.separator+"safeteacher"+ File.separator + "database");
        if (!dbRoot.exists()){
            dbRoot.mkdirs();
        }
        File dbFile=new File(dbRoot,"safeteacher.db");
//        打开或者创建一个数据库
        mSqLiteDatebase=SQLiteDatabase.openOrCreateDatabase(dbFile,null);
    }

    public static DaoSupportFactory getFactory(){
        if (mFactory==null){
            synchronized (DaoSupportFactory.class){
                if (mFactory==null){
                    mFactory=new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz){
        IDaoSupport<T> daoSupport=new DaoSupport<>();
        daoSupport.init(mSqLiteDatebase,clazz);
        return daoSupport;
    }
 }
