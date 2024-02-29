package es.soltel.herramientaevaluador.common.base.vo;

import java.util.List;

public class FilterVO {

	private String field;
	private String value;
	private String operator;
	private String logic;
	private List<FilterVO> subfilters;
	
	public FilterVO() {
	}
	
	public FilterVO(String field, String value, String operator, List<FilterVO> subfilters, String logic) {
		super();
		this.field = field;
		this.value = value;
		this.operator = operator;
		this.subfilters = subfilters;
		this.logic = logic;
	}
	public FilterVO(String field, String value) {
		super();
		this.field = field;
		this.value = value;
		this.operator = "=";
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public List<FilterVO> getSubfilters() {
		return subfilters;
	}
	public void setSubfilters(List<FilterVO> subfilters) {
		this.subfilters = subfilters;
	}
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}

	@Override
	public String toString() {
		String informacion = "{\"field\": \"" + field + "\", \"value\": " + value + ", \"operator\": \"" + operator + "\"";
		if (logic != null) {
			informacion +=  ", \"logic\": \"" + logic + "\"";
		}
		informacion +=  ", \"subfilters\": [";
		if (subfilters != null) {
			for (int i = 0; i < subfilters.size(); i++) {
				FilterVO subfiltro = subfilters.get(i);
				informacion += subfiltro.toString();
				if (i < (subfilters.size() - 1)) {
					informacion += ", ";
				}
			}
		}
		informacion += "]}";
		return informacion;
	}
	
	
}
