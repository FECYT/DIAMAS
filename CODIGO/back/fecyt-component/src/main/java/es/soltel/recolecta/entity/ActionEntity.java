package es.soltel.recolecta.entity;

import javax.persistence.*;

@Entity
@Table(name = "action")
public class ActionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String typeAction;

    @Column
    private String description;

    @Column
    private Integer nDeleteState;

    public ActionEntity(){
        
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
