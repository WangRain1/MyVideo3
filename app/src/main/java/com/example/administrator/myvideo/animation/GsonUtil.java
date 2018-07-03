package com.example.administrator.myvideo.animation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * json字符串处理类，Gson可以转成对象
 *
 */
public class GsonUtil {
	
	/**
	 * 
	 * @param {"NAME":"张三","AGE":12}
	 * @return
	 */
	public static Map parseStringMap(String jsonData)
	{
		try{
			Type mapType = new TypeToken<Map<String,String>>(){}.getType();
			Gson gson = new Gson();
			Map map = gson.fromJson(jsonData, mapType);
			return map;
		}catch (Exception e) {
		}
		return new HashMap();
	}
	
	/**
	 * 返回一个Object 
	 * @return
	 */
	public static Map<String,Object> parseStringMap2(String jsonData)
	{
		try{
			Type mapType = new TypeToken<Map<String,Object>>(){}.getType();
			Gson gson = new Gson();
			Map<String,Object> map = gson.fromJson(jsonData, mapType);
			return map;
		}catch (Exception e) {
		}
		return new HashMap<String,Object>();
	}
	
	/**
	 * 
	 * @param {"TEMPLATE_NAME":"覆盖验收模板","TEMPLATE_ID":100},{"TEMPLATE_NAME":"工维优化模板","TEMPLATE_ID":101},{"TEMPLATE_NAME":"日常测试模板","TEMPLATE_ID":102}]
	 * @return
	 */
	public static List parseMapList(String jsonData)
	{
		try{
			Type mapType = new TypeToken<List<Map<String,String>>>(){}.getType();
			Gson gson = new Gson();
			List list = gson.fromJson(jsonData, mapType);
			return list;
		}catch (Exception e) {
		}
		return new ArrayList();
	}
	
	public static LinkedList parseMapLinkedList(String jsonData)
	{
		try{
			Type mapType = new TypeToken<LinkedList<Map<String,String>>>(){}.getType();
			Gson gson = new Gson();
			LinkedList list = gson.fromJson(jsonData, mapType);
			return list;
		}catch (Exception e) {
		}
		return new LinkedList();
	}
	
	public static List parseMapObjectList(String jsonData)
	{
		try{
			Type mapType = new TypeToken<List<Map<String,Object>>>(){}.getType();
			Gson gson = new Gson();
			List list = gson.fromJson(jsonData, mapType);
			return list;
		}catch (Exception e) {
		}
		return new ArrayList();
	}
	
	
	public static void main(String[] args)
	{
		//String jsonData="{\"NAME\":\"1\",\"AGE\":\"1\" }";
		String json="[{\"TEMPLATE_NAME\":\"覆盖验收模板\",\"TEMPLATE_ID\":100},{\"TEMPLATE_NAME\":\"工维优化模板\",\"TEMPLATE_ID\":101},{\"TEMPLATE_NAME\":\"日常测试模板\",\"TEMPLATE_ID\":102}]";
		List list=parseMapList(json);
	
		System.out.println(list);
	}
	
	
	
	public static String list2Json(List<Map<String,String>> list)
	{
		//Type mapType = new TypeToken<Map<String,String>>(){}.getType();
		Gson gson = new Gson();
		String result=gson.toJson(list);
		return result;
	}
	
	public static String  map2Json(Map<String,String> map)
	{
		//Type mapType = new TypeToken<Map<String,String>>(){}.getType();
		Gson gson = new Gson();
		String result=gson.toJson(map);
		return result;
	}
	
	public static String object2Json(Object object)
	{
		Gson gson = new Gson();
		String result=gson.toJson(object);
		return result;
	}
	
}
