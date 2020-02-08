package utils;

import constant.Constants;
import java.sql.Connection;
import java.sql.DriverManager;


public class JDBCUtils {

	public static Connection getConnection() {
		//定义数据库连接对象
		Connection conn = null;
		try {
			//你导入的数据库驱动包， mysql。
			//jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8
			conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.JDBC_USER, Constants.JDBC_PASSWORD);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}