package es.soltel.herramientaevaluador.common.base.vo;

import java.util.List;

public class RequestVO {

	private List<FilterVO> filters;

	private OrderVO order;
	
	private String logic;

	public RequestVO() {
	}

	public RequestVO(List<FilterVO> filters) {
		this.filters = filters;
	}

	public List<FilterVO> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterVO> filters) {
		this.filters = filters;
	}

	public OrderVO getOrder() {
		return order;
	}

	public void setOrder(OrderVO order) {
		this.order = order;
	}
	
	public String getLogic() {
		return logic;
	}
	
	public void setLogic(String logic) {
		this.logic = logic;
	}


}
