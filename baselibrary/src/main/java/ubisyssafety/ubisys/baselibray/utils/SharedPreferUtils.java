package ubisyssafety.ubisys.baselibray.utils;

import android.content.Context;

public class SharedPreferUtils {

	/**
	 * 各业务模块中需要存储的值在此定义 常量名全部大写，如果是多个单词以"_"分割
	 */
	private static SharedPreferUtils instance;
	private final static String FILE_NAME = "CHINANEWS_APP"; // 数据记录文件名称

	private SharedPreferUtils() {
	}

	public static SharedPreferUtils getInstance() {
		if (instance == null) {
			instance = new SharedPreferUtils();
		}
		return instance;
	}

	/**
	 * 设置数据
	 */
	public boolean put(Context context, String key, String value) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
	}

	/**
	 * 获取数据
	 */
	public String get(Context context, String key) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, "");
	}

	public void putString(Context context, String key, String value) {
		context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
	}

	public String getString(Context context, String key, String def) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, def);
	}

	/**
	 * SharedPreferences存入Long型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putLong(Context context, String key, long value) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putLong(key, value).commit();
	}

	/**
	 * SharedPreferences获取Long型数据
	 * 
	 * @param context
	 * @param key
	 *            变量名
	 * @param def
	 *            默认值
	 * @return
	 */
	public long getLong(Context context, String key, long def) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getLong(key, def);
	}

	/**
	 * SharedPreferences存入Int型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putInt(Context context, String key, int value) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
	}

	/**
	 * SharedPreferences获取Int型数据
	 * 
	 * @param context
	 * @param key
	 *            变量名
	 * @param def
	 *            默认值
	 * @return
	 */
	public int getInt(Context context, String key, int def) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getInt(key, def);
	}

	/**
	 * SharedPreferences存入Float型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putFloat(Context context, String key, float value) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putFloat(key, value).commit();
	}

	/**
	 * SharedPreferences获取Float型数据
	 * 
	 * @param context
	 * @param key
	 *            变量名
	 * @param def
	 *            默认值
	 * @return
	 */
	public float getFloat(Context context, String key, float def) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getFloat(key, def);
	}

	/**
	 * SharedPreferences存入Boolean型数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putBoolean(Context context, String key, boolean value) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
	}

	/**
	 * SharedPreferences获取Boolean型数据
	 * 
	 * @param context
	 * @param key
	 *            变量名
	 * @param def
	 *            默认值
	 * @return
	 */
	public boolean getBoolean(Context context, String key, boolean def) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(key, def);
	}

	/**
	 * 清空数据
	 */
	public boolean remore(Context context, String key) {
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().remove(key).commit();
	}

}
