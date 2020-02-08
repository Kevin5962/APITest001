package utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpRequestBase;

import com.alibaba.fastjson.JSONPath;

public class AuthenticationUtils {

	public static Map<String, String> cache = new HashMap<>();

	// 从responseBody中提取token存储到cache
	public static Map<String,String> storeToken(String responseBody) {
		// 先判断code是否0
		String code = defaultString(JSONPath.read(responseBody, "$.code"));
		if ("0".equals(code)) {
			/**
			 *方案1：
			// 从 responseBody 取出token 和 memberId 存储起来。
			String token = defaultString(JSONPath.read(responseBody, "$.data.token_info.token"));
			String id = defaultString(JSONPath.read(responseBody, "$.data.id"));
			//最后加上是否null判断
			if(!="null".equals(token)){cache.put("token", token); }
			if(!="null".equals(id)){cache.put("member_id", id);}
			 */
			//方案2：
			Object token = JSONPath.read(responseBody, "$.data.token_info.token");
			Object id = JSONPath.read(responseBody, "$.data.id");
			//最后加上是否null判断
			if (token != null) {
				cache.put("token", token.toString());
				System.out.println("token："+token.toString());
			}
			if (id != null) {
				cache.put("member_id", id.toString());
				System.out.println("member_id："+id.toString());
			}
		}
		return cache;
	}

	// 从缓存中取出token存储到header中 POST PATCH--->父类--->反射
	public static void addTokenInHeader(HttpRequestBase request) {
		String token = cache.get("token");
		if (StringUtils.isNotBlank(token)) {
			String tokenHeader = "Bearer " + cache.get("token");
			request.setHeader("Authorization", tokenHeader);
			System.out.println("tokenHeader："+tokenHeader);
		}
	}

	public static String defaultString(Object o) {
		return o == null ? "null" : o.toString();
	}

}
