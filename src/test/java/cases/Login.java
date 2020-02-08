package cases;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import pojo.Case;
import utils.DataUtils;

public class Login extends BaseCase {
	
	private Logger logger = Logger.getLogger(Login.class);
	
	@DataProvider(name = "datas")
	@Override
	public Object[][] datas() {
		Object[][] datas = DataUtils.getDataProvider("2");
		return datas;
	}

	@Override
	public String validateSQL(Case c, Object beforeResult, Object afterResult) {
		return "Pass";
	}
}
