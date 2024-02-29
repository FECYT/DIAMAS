package es.soltel.recolecta.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//Clase para el envelope SOAP
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapEnvelope {
    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private SoapBody body;

    // getters y setters
    public SoapBody getBody() {
        return body;
    }

    public void setBody(SoapBody body) {
        this.body = body;
    }
}

