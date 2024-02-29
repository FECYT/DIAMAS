package es.soltel.recolecta.service;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;

import es.soltel.recolecta.response.RepositoryResponse;
import es.soltel.recolecta.response.UserResponse;


public interface XMLResponseService {


    UserResponse parseXMLToUserResponse(String xml) throws Exception;

    List<UserResponse> parseXMLToUsersResponses(String xml) throws Exception;

    // public RepositoryResponse parseXMLToRepositoryResponse(String xml) throws Exception;


    public List<RepositoryResponse> parseXML(String xml) throws JDOMException, IOException;

}
