package utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import pojo.API;
import pojo.Case;

public class DataUtils {
	//excel中所有的数据
	public static List<API> apiList = ExcelUtils.read(API.class,0);
	public static List<Case> caseList = ExcelUtils.read(Case.class,1);
	
	/**
	 * 	传入对应的接口编号，获取对应API实体和Case集合。
	 * 	把API和Case对应起来存入到Object[][]二维数组中。
	 *  
	 * @param apiId 接口编号
	 * @return
	 */
	public static Object[][] getDataProvider(String apiId) {
		API wantApi = getAPIById(apiId);
		List<Case> wantCaseList = getCaseByApiId(apiId);
		Object[][] datas = new Object[wantCaseList.size()][2];
		//依次获取每一个Case，然后和API匹配。
		for (int i = 0; i < datas.length; i++) {
			datas[i][0] = wantApi;
			datas[i][1] = wantCaseList.get(i);
		}
		return datas;
	}
	
	private static API getAPIById(String apiId) {
		if(StringUtils.isBlank(apiId)) {
			return null;
		}
		for (API api : apiList) {
			if(apiId.equals(api.getId())) {
				return api;
			}
		}
		return null;
	}
	
	private static List<Case> getCaseByApiId(String apiId) {
		List<Case> list = new ArrayList<>();
		if(StringUtils.isBlank(apiId)) {
			return list;
		}
		for (Case c : caseList) {
			if(apiId.equals(c.getApiId())) {
				list.add(c);
			}
		}
		return list;
	}
	
}
