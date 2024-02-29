package es.soltel.recolecta.PortType;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.spi.ObjectFactory;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import es.soltel.herramientaevaluador.common.config.client.bean.integracion.Entrada;
import es.soltel.recolecta.response.UserResponse;

@WebService(name = "ISLookUpServicePortType", targetNamespace = "http://services.dnetlib.eu/")
@XmlSeeAlso({ ObjectFactory.class })
public interface ISLookUpServicePortType {
    @WebMethod(operationName = "ConsultarSubsistencia", action = "urn:ConsultarSubsistencia")
    @WebResult(targetNamespace = "http://services.java.main")
    @RequestWrapper(localName = "ConsultarSubsistencia", targetNamespace = "http://services.java.main")
    @ResponseWrapper(localName = "ConsultarSubsistenciaResponse", targetNamespace = "http://services.java.main")
    public UserResponse consultarSubsistencia(
            @WebParam(name = "entrada", targetNamespace = "http://services.java.main") Entrada entrada);

}
