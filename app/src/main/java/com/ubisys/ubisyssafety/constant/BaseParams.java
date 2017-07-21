package com.ubisys.ubisyssafety.constant;

import java.util.HashMap;
import java.util.Map;

public class BaseParams {
	public static String Devicetype = "android";
	public static String QINIUHOST = "http://of8fbr3f2.bkt.clouddn.com/";
	/** 移除账户 */
	public static final String ACCOUNT_REMOVED = "accountremoved";
	/** 每日作业 */
	public static final String EVERYSUBJECT = "everydaysubject";
	private static Map<String, String> map = new HashMap<String, String>();
	private static Map<String, String> avtarMap = new HashMap<String, String>();

	public static Map<String, String> getMap() {
		return map;
	}

	public static Map<String, String> getAvtarMap() {
		return avtarMap;
	}

	public static void setMap(Map<String, String> map) {
		BaseParams.map = map;
	}

	public static void setAvtarMap(Map<String, String> map) {
		BaseParams.avtarMap = map;
	}

}
