package es.soltel.recolecta.vo;

public class QuestionnaireTypeVO {
    private Long id;
    private String es;
    private String en;
    private Integer nDeleteState;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public String getEn() {
		return en;
	}
	public void setEn(String en) {
		this.en = en;
	}
	public Integer getnDeleteState() {
		return nDeleteState;
	}
	public void setnDeleteState(Integer nDeleteState) {
		this.nDeleteState = nDeleteState;
	}

}

