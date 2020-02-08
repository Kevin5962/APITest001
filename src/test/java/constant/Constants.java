package constant;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public final static String EXCEL_PATH = "src/test/resources/cases_v8.xlsx";
	public final static String X_LEMONBAN_MEDIA_TYPE = "lemonban.v2";
	public final static int ACTUAL_RESPONSE_DATA_CELLNUM = 5;
	public final static int IS_PASS_CELLNUM = 6;

										// jdbc:数据名称://数据库ip:端口/数据库名称?编码集
	public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
	public static final String JDBC_USER = "future";
	public static final String JDBC_PASSWORD = "123456";
	

	public static final String REGISTER_MOBILEPHONE_TEXT = "${toBeRegisterMobilephone}";
	public static final String REGISTER_PASSWORD_TEXT = "${toBeRegisterPassword}";
	public static final String LOGIN_MOBILEPHONE_TEXT = "${toBeLoginMobilephone}";
	public static final String LOGIN_PASSWORD_TEXT = "${toBeLoginPassword}";
	public static final String MEMBER_ID =  "${member_id}";

	public static final String REGISTER_MOBILEPHONE_VALUE = "18900492666";
	public static final String REGISTER_PASSWORD_VALUE = "12345678";
	public static final String LOGIN_MOBILEPHONE_VALUE = "18929328941";
	public static final String LOGIN_PASSWORD_VALUE = "12345678";
	
	public static final Map<String, String> map = new HashMap<String, String>();
	
	static {
		//静态代码块，特点：当本类被加载的时候，静态代码块就会被执行。
		//当调用本类的方法或者属性时，静态代码块会执行一次且只会执行一次。
		map.put(REGISTER_MOBILEPHONE_TEXT,REGISTER_MOBILEPHONE_VALUE);
		map.put(REGISTER_PASSWORD_TEXT,REGISTER_PASSWORD_VALUE);
		map.put(LOGIN_MOBILEPHONE_TEXT,LOGIN_MOBILEPHONE_VALUE);
		map.put(LOGIN_PASSWORD_TEXT,LOGIN_PASSWORD_VALUE);
	}
	
	
}
