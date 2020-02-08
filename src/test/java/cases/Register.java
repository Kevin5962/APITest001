package cases;
import org.testng.annotations.DataProvider;
import pojo.Case;
import utils.DataUtils;

public class Register extends BaseCase {
	
	@DataProvider(name = "datas")
	@Override 
	public Object[][] datas() {
		Object[][] datas = DataUtils.getDataProvider("1");
		return datas;
	}

	@Override
	public String validateSQL(Case c, Object beforeResult, Object afterResult) {
		System.out.println("beforeResult :" + beforeResult);
		System.out.println("afterResult :" + afterResult);
		if(beforeResult == null && afterResult != null && (Long)afterResult == 1) {
			return "Pass";
		}
		return "Fail";
	}

}
