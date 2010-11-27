package org.agilesource.android.ilovetravelling.util;

import java.util.HashMap;
import java.util.Map;

public class CityUtil {

	public static Map getTranslateMap(){
		Map lanMap = new HashMap();
		lanMap.put("����", "beijing");
		lanMap.put("���", "tianjin");
		lanMap.put("�Ϻ�", "shanghai");
		lanMap.put("����", "hangzhou");
		lanMap.put("����", "guangzhou");
		lanMap.put("�麣", "zhuhai");
		lanMap.put("����", "sanya");
		return lanMap;
	}
	
	public static Map getExchangeMap(){
		Map lanMap = new HashMap();
		lanMap.put("�����", "CNY");
		lanMap.put("��Ԫ", "HKD");
		lanMap.put("��Ԫ", "JPY");
		lanMap.put("̨��", "TWD");
		lanMap.put("ŷԪ", "EUR");
		lanMap.put("��Ԫ", "USD");
		lanMap.put("Ӣ��", "GBP");
		lanMap.put("��Ԫ", "AUD");
		lanMap.put("��Ԫ", "KRW");
		return lanMap;
	}
}
