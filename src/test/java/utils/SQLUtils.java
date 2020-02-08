package utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class SQLUtils {

	public static Object query(String sql) {
		Object result = null;
		// 1、替换sql
		if (StringUtils.isNotBlank(sql)) {
			String member_id = AuthenticationUtils.cache.get("member_id");
			if (member_id == null) {
				return result;
			}
			sql = sql.replace("${member_id}", member_id);
			// 执行sql语句DBUtils
			// 使用DBUtils必须创建 QueryRunner 对象
			QueryRunner qr = new QueryRunner();
			Connection conn = JDBCUtils.getConnection();
			try {
				// qr执行查询操作传入连接对象、查询sql语句、结果封装对象。
				result = qr.query(conn, sql, new ScalarHandler<Object>());
				System.out.println("sql result :" + result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
