package es.soltel.recolecta.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Component;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.response.RepositoryResponse;
import es.soltel.recolecta.response.UserResponse;
import es.soltel.recolecta.service.XMLResponseService;

@Component
public class XMLResponseServiceImpl implements XMLResponseService {

    

    @Override
    @NoLogging
    public UserResponse parseXMLToUserResponse(String xml) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xml));
        Element rootElement = document.getRootElement();
        Element responseBody = rootElement.getChild("Body", rootElement.getNamespace());
        Namespace ns3 = Namespace.getNamespace("ns3", "http://ws.profile.functionality.clients.dnetlib.eu/");
        Element searchUsersResponse = responseBody.getChild("searchUsersResponse", ns3);

        Element userReturn = searchUsersResponse.getChild("return");

        UserResponse response = new UserResponse();

        response.setEmail(userReturn.getChildText("email"));
        response.setUserId(userReturn.getChildText("userId"));
        response.setRegistrationDate(userReturn.getChildText("registrationDate"));
        response.setRecommendationSendEmail(Boolean.parseBoolean(userReturn.getChildText("recommendationSendEmail")));
        response.setPassword(userReturn.getChildText("password"));
        response.setPassdate(userReturn.getChildText("passdate"));
        response.setIsUserManager(Boolean.parseBoolean(userReturn.getChildText("isUserManager")));
        response.setIsSuperUser(Boolean.parseBoolean(userReturn.getChildText("isSuperUser")));
        response.setIsCommunityManager(Boolean.parseBoolean(userReturn.getChildText("isCommunityManager")));
        response.setIsCollectionManager(Boolean.parseBoolean(userReturn.getChildText("isCollectionManager")));

        List<String> roles = new ArrayList<>();
        for (Element role : userReturn.getChildren("roles")) {
            roles.add(role.getText());
        }
        response.setRoles(roles);

        return response;
    }

    @Override
    @NoLogging
    public List<UserResponse> parseXMLToUsersResponses(String xml) throws JDOMException, IOException {
        List<UserResponse> userResponses = new ArrayList<>();

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new ByteArrayInputStream(xml.getBytes()));
        Element rootNode = document.getRootElement();
        Element responseBody = rootNode.getChild("Body", rootNode.getNamespace());
        Namespace ns3 = Namespace.getNamespace("ns3", "http://ws.profile.functionality.clients.dnetlib.eu/");

        Element searchUsersResponse = responseBody.getChild("searchUsersResponse", ns3);

        List<Element> returnElements = searchUsersResponse.getChildren("return");

        for (Element returnElement : returnElements) {
            UserResponse userResponse = new UserResponse(); // Creación de una nueva instancia de UserResponse

            userResponse.setUserId(returnElement.getChildText("userId"));
            userResponse.setRoles(getMultipleTagValues("roles", returnElement)); // Asumiendo que hay un método getMultipleTagValues
            userResponse.setRegistrationDate(returnElement.getChildText("registrationDate"));
            userResponse.setRecommendationSendEmail(
                    Boolean.parseBoolean(returnElement.getChildText("recommendationSendEmail")));
            userResponse.setPassword(returnElement.getChildText("password"));
            userResponse.setPassdate(returnElement.getChildText("passdate"));
            userResponse.setIsUserManager(Boolean.parseBoolean(returnElement.getChildText("isUserManager")));
            userResponse.setIsSuperUser(Boolean.parseBoolean(returnElement.getChildText("isSuperUser")));
            userResponse.setIsCommunityManager(Boolean.parseBoolean(returnElement.getChildText("isCommunityManager")));
            userResponse
                    .setIsCollectionManager(Boolean.parseBoolean(returnElement.getChildText("isCollectionManager")));
            userResponse.setEmail(returnElement.getChildText("email"));

            List<String> roles = new ArrayList<>();

            for (Element role : returnElement.getChildren("roles")) {
                roles.add(role.getText());
            }
            userResponse.setRoles(roles);

            userResponses.add(userResponse); // Agregando la instancia de UserResponse a la lista
        }

        return userResponses; // Retornando la lista de UserResponses
    }


    private static String getTagValue(String tag, Element element) {
        Element childElement = element.getChild(tag);
        return childElement != null ? childElement.getText() : null;
    }

    private static List<String> getMultipleTagValues(String tag, Element element) {
        List<Element> childElements = element.getChildren(tag);
        List<String> values = new ArrayList<>();

        for (Element childElement : childElements) {
            values.add(childElement.getText());
        }

        return values;
    }


    public List<RepositoryResponse> parseXML(String xml) throws JDOMException, IOException {
        List<RepositoryResponse> responses = new ArrayList<>();

        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xml));
        Element rootElement = document.getRootElement();
        Namespace ns = rootElement.getNamespace();
        Element responseBody = rootElement.getChild("Body", ns);

        if (responseBody != null) {
            Namespace ns2 = Namespace.getNamespace("ns2", "http://services.dnetlib.eu/");
            Element searchRepositoryResponse = responseBody.getChild("quickSearchProfileResponse", ns2);

            if (searchRepositoryResponse != null) {
                List<Element> returns = searchRepositoryResponse.getChildren("return"); // Obtener todos los elementos 'return'

                for (Element child : returns) {
                    if ("return".equals(child.getName())) { // Comprobar si el nombre del elemento es 'return'
                        RepositoryResponse response = new RepositoryResponse(child);
                        responses.add(response);
                    }
                }
            }
        }

        return responses;
    }


}
