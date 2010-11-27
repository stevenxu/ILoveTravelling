package org.agilesource.android.ilovetravelling.util;

import java.util.HashMap;
import java.util.Map;

public class CityUtil {

	public static Map getTranslateMap(){
		Map lanMap = new HashMap();
		lanMap.put("北京", "beijing");
		lanMap.put("天津", "tianjin");
		lanMap.put("上海", "shanghai");
		lanMap.put("杭州", "hangzhou");
		lanMap.put("广州", "guangzhou");
		lanMap.put("珠海", "zhuhai");
		lanMap.put("三亚", "sanya");
		return lanMap;
	}
	
	public static Map getExchangeMap(){
		Map lanMap = new HashMap();
		lanMap.put("人民币", "CNY");
		lanMap.put("港元", "HKD");
		lanMap.put("日元", "JPY");
		lanMap.put("台币", "TWD");
		lanMap.put("欧元", "EUR");
		lanMap.put("美元", "USD");
		lanMap.put("英镑", "GBP");
		lanMap.put("澳元", "AUD");
		lanMap.put("韩元", "KRW");
		return lanMap;
	}
}
