package es.soltel.recolecta.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.anottation.NoLogging;

@Service
public class RequestHttpServiceImpl {
    /*
     * @NoLogging public StringBuilder get(String url) throws Exception { return executeRequest(new HttpGet(url)); }
     * 
     * @NoLogging public String post(String url, String jsonPayload) throws Exception { HttpPost postRequest = new
     * HttpPost(url); if (jsonPayload != null) { postRequest.setEntity(new StringEntity(jsonPayload, "UTF-8"));
     * postRequest.setHeader("Content-Type", "application/json"); } return executeRequest(postRequest); }
     */
    /*
     * @NoLogging public String put(String url, String jsonPayload) throws Exception { HttpPut putRequest = new
     * HttpPut(url); if (jsonPayload != null) { putRequest.setEntity(new StringEntity(jsonPayload, "UTF-8"));
     * putRequest.setHeader("Content-Type", "application/json"); } return executeRequest(putRequest); }
     */
    // Puedes agregar más métodos para DELETE, PATCH, etc.

    @NoLogging
    private StringBuilder executeRequest(HttpUriRequest request) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Convert the response to a String first
                String responseString = EntityUtils.toString(entity);

                // Convert the string to StringBuilder and return
                return new StringBuilder(responseString);
            }
            return null;
        }
    }


    @NoLogging
    public StringBuilder postXml(String url, String xmlPayload) throws Exception {
        HttpPost postRequest = new HttpPost(url);
        if (xmlPayload != null) {
            postRequest.setEntity(new StringEntity(xmlPayload, "UTF-8"));
            postRequest.setHeader("Content-Type", "text/xml");
        }
        return executeRequest(postRequest);
    }

}
