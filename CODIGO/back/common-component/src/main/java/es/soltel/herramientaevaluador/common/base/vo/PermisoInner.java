package es.soltel.herramientaevaluador.common.base.vo;

import org.springframework.security.core.GrantedAuthority;

public class PermisoInner implements GrantedAuthority {
    /**
     * 
     */
    private static final long serialVersionUID = -6740049529278480058L;

    private String authority;
    private String ajeno;


    public String getAjeno() {
        return ajeno;
    }

    public void setAjeno(String ajeno) {
        this.ajeno = ajeno;
    }

    public PermisoInner(String authority, String ajeno) {
        super();
        this.authority = authority;
        this.ajeno = ajeno;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}