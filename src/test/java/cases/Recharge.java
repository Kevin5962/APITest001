package cases;

import org.testng.annotations.DataProvider;

import com.alibaba.fastjson.JSONPath;
import pojo.Case;
import utils.DataUtils;

public class Recharge extends BaseCase {
	
	@DataProvider(name = "datas")
	@Override 
	public Object[][] datas() {
		Object[][] datas = DataUtils.getDataProvider("3");
		return datas;
	}

	@Override
	public String validateSQL(Case c, Object beforeResult, Object afterResult) {
		//{"member_id":"631","amount":"6300"}
		if(beforeResult == null || afterResult == null) {
			return "Fail";
		}
		String moneyStr = JSONPath.read(c.getParams(),"$.amount").toString();
		
		double money = Double.parseDouble(moneyStr);
		double beforeMoney = Double.parseDouble(beforeResult.toString());
		double afterMoney = Double.parseDouble(afterResult.toString());
		System.out.println("money ：" + money);
		System.out.println("beforeMoney ：" + beforeMoney);
		System.out.println("afterMoney ：" + afterMoney);
		if(afterMoney - beforeMoney == money) {
			return "Pass";
		}
		return "Fail";
	}

}
