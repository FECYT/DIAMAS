package es.soltel.recolecta.service.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import org.springframework.beans.factory.annotation.Value;

import es.soltel.recolecta.PortType.ISLookUpServicePortType;

@WebServiceClient(name = "ISLookUpServiceService", targetNamespace = "http://services.dnetlib.eu/", wsdlLocation = "http://192.168.74.107:8080/is/services/isLookUp?wsdl")
public class DnetWebServiceImpl extends Service {

    private static URL DNETWEBSERVICEIMPL_WSDL_LOCATION;
    private final static WebServiceException DNETWEBSERVICEIMPL_EXCEPTION = null;
    private final static QName DNETWEBSERVICEIMPL_QNAME = new QName("http://services.dnetlib.eu/",
            "ISLookUpServiceService");

    @Value("${dnetls.host}")
    private static String dnetlsHost;

    @Value("${dnet.alternativePort}")
    private static String dnetPort;

    static {
        try {
            DNETWEBSERVICEIMPL_WSDL_LOCATION = new URL(dnetlsHost + ":" + dnetPort + "/is/services/isLookUp?wsdl");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize WSDL location", e);
        }
    }

    public DnetWebServiceImpl() {
        super(__getWsdlLocation(), DNETWEBSERVICEIMPL_QNAME);
    }

    public DnetWebServiceImpl(WebServiceFeature... features) {
        super(__getWsdlLocation(), DNETWEBSERVICEIMPL_QNAME, features);
    }

    public DnetWebServiceImpl(URL wsdlLocation) {
        super(wsdlLocation, DNETWEBSERVICEIMPL_QNAME);
        DNETWEBSERVICEIMPL_WSDL_LOCATION = wsdlLocation;
    }

    public DnetWebServiceImpl(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, DNETWEBSERVICEIMPL_QNAME, features);
    }

    public DnetWebServiceImpl(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DnetWebServiceImpl(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    @WebEndpoint(name = "ISLookUpServicePort")
    public ISLookUpServicePortType getISLookUpServicePort() {
        return super.getPort(new QName("http://services.dnetlib.eu/", "ISLookUpServicePort"),
                ISLookUpServicePortType.class);
    }

    @WebEndpoint(name = "ISLookUpServicePort")
    public ISLookUpServicePortType getISLookUpServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://services.dnetlib.eu/", "ISLookUpServicePort"),
                ISLookUpServicePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (DNETWEBSERVICEIMPL_EXCEPTION != null) {
            throw DNETWEBSERVICEIMPL_EXCEPTION;
        }
        return DNETWEBSERVICEIMPL_WSDL_LOCATION;
    }
}
