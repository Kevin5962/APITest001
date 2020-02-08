package cases;

import constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pojo.API;
import pojo.Case;
import pojo.WriteBackData;
import utils.AuthenticationUtils;
import utils.ExcelUtils;
import utils.HttpUtils;
import utils.SQLUtils;
import utils.ValidateUtils;

import java.util.Set;

public abstract class BaseCase {

	private Logger logger = Logger.getLogger(BaseCase.class);
	
	@BeforeSuite
	public void init() {
		logger.info("===========开始执行测试套件============");
	}
	
	
	@Test(dataProvider = "datas")
	public void test(API api, Case c) throws Exception {
		logger.info("===========测试方法开始执行============用例编号" + c.getId()+"============");
		//1、参数化替换
		String params = replaceParams(c.getParams());
		c.setParams(params);
		//2、前置sql
		String sql = c.getSql();
		Object beforeResult = SQLUtils.query(sql);
		//3、执行接口
		String responsebody = HttpUtils.call(api.getUrl(), api.getType(), c.getParams(), api.getContentType());
		//4、后置sql
		Object afterResult = SQLUtils.query(sql);
		//5、断言前置sql和后置sql
		String validateSQL = "Pass";
		if(StringUtils.isNotBlank(sql)) {
			validateSQL = validateSQL(c, beforeResult, afterResult);
		}
		//6、添加实际接口响应结果到回写集合中
		int rowNum = Integer.parseInt(c.getId()); //用例编号
		int cellNum = Constants.ACTUAL_RESPONSE_DATA_CELLNUM;//列号固定值
		String content = responsebody;
		addWriteBackData(rowNum, cellNum, content);
		//7、实际接口响应断言
		String validateResponse = ValidateUtils.validate(c.getExpectData(), responsebody);
		//8、sql断言和接口响应断言是否通过，添加到回写集合中
		String isPass = "Fail";
		if("Pass".equals(validateSQL) && validateSQL.equals(validateResponse)) {
			isPass = "Pass";
		}
		cellNum = Constants.IS_PASS_CELLNUM;
		addWriteBackData(rowNum, cellNum, isPass);
		
	}
	
	public String replaceParams(String params) {

		//入参：{"mobile_phone":"${toBeRegisterMobilephone}","pwd":"${toBeRegisterPassword}"}
		//返回：{"mobile_phone":"18900492233","pwd":"12345678"}
		//入参：{"member_id":"${member_id}","amount":"6300"}
		//返回：{"member_id":"5315","amount":"6300"}

		if(params.contains("${member_id}")){
			String member_id = AuthenticationUtils.cache.get("member_id");
			if(StringUtils.isNotBlank(member_id)) {
				params = params.replace(Constants.MEMBER_ID, member_id);
			}
		}else{
			Set<String> keySet = Constants.map.keySet();
			for (String key : keySet) {
				String value = Constants.map.get(key);
				params = params.replace(key, value);
			}

		}
		System.out.println("请求体："+params);
		return params;
	}
	
	/**
	   *   前置sql和后置sql断言方法
	 * @param c
	 * @param beforeResult
	 * @param afterResult
	 * @return
	 */
	public abstract String validateSQL(Case c,Object beforeResult,Object afterResult);
	

	public void addWriteBackData(int rowNum, int cellNum, String content) {
		//一条用例需要回写的数据
		WriteBackData wdb = new WriteBackData(rowNum, cellNum, content);
		//我们需要做一次性回写，所以每次回写的内容存储到list集合中。
		ExcelUtils.wbdList.add(wdb);
	}
	
	@AfterSuite
	public void finish() {
		ExcelUtils.backWrite("用例");
		logger.info("===========测试套件执行完毕============");
	}
	
	public abstract Object[][] datas();
}
