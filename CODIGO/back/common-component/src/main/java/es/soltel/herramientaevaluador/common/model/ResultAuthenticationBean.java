package es.soltel.herramientaevaluador.common.model;

import java.io.Serializable;

public class ResultAuthenticationBean implements Serializable {
    private static final long serialVersionUID = 1457973212915448563L;
    private String resultDescription;
    private boolean validTicket;
    //private List<CertificateInfo> certificateData;
    private String username;
    private String password;

    public ResultAuthenticationBean() {
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public boolean isValidTicket() {
        return validTicket;
    }

    public void setValidTicket(boolean validTicket) {
        this.validTicket = validTicket;
    }

    //	public List<CertificateInfo> getCertificateData() {
    //		return certificateData;
    //	}
    //
    //	public void setCertificateData(List<CertificateInfo> certificateData) {
    //		this.certificateData = certificateData;
    //	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}