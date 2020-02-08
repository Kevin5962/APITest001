package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Case {
	@Excel(name = "用例编号")
	private String id;
	@Excel(name = "用例描述")
	private String desc;
	@Excel(name = "参数")
	private String params;
	@Excel(name = "接口编号")
	private String apiId;
	@Excel(name = "期望响应数据")
	private String expectData;
	@Excel(name = "检验SQL")
	private String sql;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getExpectData() {
		return expectData;
	}

	public void setExpectData(String expectData) {
		this.expectData = expectData;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String toString() {
		return "Case [id=" + id + ", desc=" + desc + ", params=" + params + ", apiId=" + apiId + ", expectData="
				+ expectData + ", sql=" + sql + "]";
	}

}
