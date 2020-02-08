package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import pojo.JsonPathValidate;

import java.util.List;

public class ValidateUtils {

	//判断json字符串是数组格式还是单个对象格式
	public static void checkJsonType(String json) {
		Object object = JSONObject.parse(json);
		if(object instanceof JSONObject) {			//object是JSONObject类的对象吗？
			System.out.println("JSONObject :" + json);
		}else if(object instanceof JSONArray) {		//object是JSONArray类的对象吗？
			System.out.println("JSONArray :" + json);
		}
	}
	
	public static void main(String[] args) {
		String json1 = "[{\"value\":0,\"expression\":\"$.code\"},{\"value\":\"OK\",\"expression\":\"$.msg\"}]";
		String json2 = "{\"code\":1,\"msg\":\"密码为空\",\"data\":null,\"copyright\":\"Copyright 柠檬班 © 2017-2019 湖南省零檬信息技术有限公司 All Rights Reserved\"}";
		String json3 = json1;
		checkJsonType(json1);
		checkJsonType(json2);
		System.out.println(validate(json1,json2));
		System.out.println(validate(json1,json3));
	}
	
	public static String validate(String expect, String actual) {
		// 1、首先判断字符串是否等值
		if (expect.equals(actual)) {
			//等值成功
			return "Pass";
		}
		// 2.1、判断expect是否为json对象
		Object object = JSONObject.parse(expect);
		if(object instanceof JSONObject) {
			//等值失败
			return "Fail";
		}
		// 2.2、最后通过jsonpath断言具体取值是否一致
		// expect：[{"value":0,"expression":"$.code"},{"value":"OK","expression":"$.msg"}]
		// 通过jsonpath的expression匹配actual，如果返回值和value相等那么断言通过
		List<JsonPathValidate> list = JSONObject.parseArray(expect, JsonPathValidate.class);
		for (JsonPathValidate jpv : list) {
			String expression = jpv.getExpression();
			Object expectValue = jpv.getValue();
			// 最好是判断 expression和expectValue是否为null
			Object actualValue = JSONPath.read(actual, expression);
			if (!expectValue.equals(actualValue)) {
				return "Fail";
			}
		}
		return "Pass";
	}
}
