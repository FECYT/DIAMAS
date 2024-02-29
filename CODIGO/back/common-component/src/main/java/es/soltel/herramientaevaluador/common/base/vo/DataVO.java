package es.soltel.herramientaevaluador.common.base.vo;

import org.springframework.data.domain.Page;

public class DataVO<T> {

	private T objects;
	private Long total;
	private Integer resultSize;
	private Integer page;

	public DataVO() {
	}

	public DataVO(T objects) {
		this.objects = objects;
	}

	public DataVO(T objects, Page<?> page) {
		this.objects = objects;
		this.total = page.getTotalElements();
		this.resultSize = page.getNumberOfElements();
		this.page = page.getNumber();
	}

	public T getObjects() {
		return objects;
	}

	public void setObjects(T objects) {
		this.objects = objects;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getResultSize() {
		return resultSize;
	}

	public void setResultSize(Integer resultSize) {
		this.resultSize = resultSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
