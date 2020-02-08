package pojo;

public class JsonPathValidate {
	private String expression;
	private Object value;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "JsonPathValidate [expression=" + expression + ", value=" + value + "]";
	}

}
