package es.soltel.recolecta.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

//Clase para el cuerpo SOAP
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapBody {
    @XmlElement(name = "return", namespace = "http://services.dnetlib.eu/") // nota el cambio aquí
    private List<String> returnElements; // y aquí

    // getters y setters
    public List<String> getReturn() { // y aquí
        return returnElements;
    }

    public void setReturn(List<String> returnElements) { // y aquí
        this.returnElements = returnElements;
    }
}