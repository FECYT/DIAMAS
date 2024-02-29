package es.soltel.recolecta.response;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class RepositoryResponse {

    private String dnetId;
    private String typology;
    private int maxSizeOfDataStructure;
    private String availableDiskspace;
    private int maxNumberOfDataStructure;
    private String officialName;
    private String englishName;
    private String iconUri;
    private String country;
    private double longitude;
    private double latitude;
    private double timezone;
    private String repositoryWebpage;
    private String repositoryInstitution;
    private String adminInfo;
    private String interfaceProtocol;
    private String baseUrl;
    private String registeredBy;
    private String datasourceType;
    private String datasourceOriginalId;
    private boolean datasourceAggregated;


    public String getDnetId() {
        return dnetId;
    }

    public void setDnetId(String dnetId) {
        this.dnetId = dnetId;
    }

    public String getTypology() {
        return typology;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public int getMaxSizeOfDataStructure() {
        return maxSizeOfDataStructure;
    }

    public void setMaxSizeOfDataStructure(int maxSizeOfDataStructure) {
        this.maxSizeOfDataStructure = maxSizeOfDataStructure;
    }

    public String getAvailableDiskspace() {
        return availableDiskspace;
    }

    public void setAvailableDiskspace(String availableDiskspace) {
        this.availableDiskspace = availableDiskspace;
    }

    public int getMaxNumberOfDataStructure() {
        return maxNumberOfDataStructure;
    }

    public void setMaxNumberOfDataStructure(int maxNumberOfDataStructure) {
        this.maxNumberOfDataStructure = maxNumberOfDataStructure;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTimezone() {
        return timezone;
    }

    public void setTimezone(double timezone) {
        this.timezone = timezone;
    }

    public String getRepositoryWebpage() {
        return repositoryWebpage;
    }

    public void setRepositoryWebpage(String repositoryWebpage) {
        this.repositoryWebpage = repositoryWebpage;
    }

    public String getRepositoryInstitution() {
        return repositoryInstitution;
    }

    public void setRepositoryInstitution(String repositoryInstitution) {
        this.repositoryInstitution = repositoryInstitution;
    }

    public String getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(String adminInfo) {
        this.adminInfo = adminInfo;
    }

    public String getInterfaceProtocol() {
        return interfaceProtocol;
    }

    public void setInterfaceProtocol(String interfaceProtocol) {
        this.interfaceProtocol = interfaceProtocol;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(String registeredBy) {
        this.registeredBy = registeredBy;
    }

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    public String getDatasourceOriginalId() {
        return datasourceOriginalId;
    }

    public void setDatasourceOriginalId(String datasourceOriginalId) {
        this.datasourceOriginalId = datasourceOriginalId;
    }

    public boolean isDatasourceAggregated() {
        return datasourceAggregated;
    }

    public void setDatasourceAggregated(boolean datasourceAggregated) {
        this.datasourceAggregated = datasourceAggregated;
    }


    public RepositoryResponse(Element returnElement) throws JDOMException, IOException {
        String configurationXml = returnElement.getTextTrim();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document configurationDoc = saxBuilder.build(new StringReader(configurationXml));
        Element configuration = configurationDoc.getRootElement();

        if (configuration != null) {
            this.typology = getText(configuration, "TYPOLOGY");
            this.maxSizeOfDataStructure = Integer.parseInt(getText(configuration, "MAX_SIZE_OF_DATASTRUCTURE"));
            this.availableDiskspace = getText(configuration, "AVAILABLE_DISKSPACE");
            this.maxNumberOfDataStructure = Integer.parseInt(getText(configuration, "MAX_NUMBER_OF_DATASTRUCTURE"));
            this.officialName = getText(configuration, "OFFICIAL_NAME");
            this.englishName = getText(configuration, "ENGLISH_NAME");
            this.iconUri = getText(configuration, "ICON_URI");
            this.country = getText(configuration, "COUNTRY");
            this.dnetId = extractFieldValue(configuration, "REPOSITORY_ID");

            Element location = configuration.getChild("LOCATION");
            if (location != null) {
                this.longitude = Double.parseDouble(getText(location, "LONGITUDE"));
                this.latitude = Double.parseDouble(getText(location, "LATITUDE"));
                this.timezone = Double.parseDouble(getText(location, "TIMEZONE"));
            }
            this.repositoryWebpage = getText(configuration, "REPOSITORY_WEBPAGE");
            this.repositoryInstitution = getText(configuration, "REPOSITORY_INSTITUTION");
            this.adminInfo = getText(configuration, "ADMIN_INFO");


            Element interfaces = configuration.getChild("INTERFACES");
            if (interfaces != null) {
                Element interfaceElement = interfaces.getChild("INTERFACE");
                if (interfaceElement != null) {
                    this.interfaceProtocol = getText(interfaceElement, "ACCESS_PROTOCOL");
                    this.baseUrl = getText(interfaceElement, "BASE_URL");
                }
            }

            this.registeredBy = getText(configuration, "REGISTERED_BY");
            this.datasourceType = getText(configuration, "DATASOURCE_TYPE");
            this.datasourceOriginalId = getText(configuration, "DATASOURCE_ORIGINAL_ID");
            this.datasourceAggregated = "true".equals(getText(configuration, "DATASOURCE_AGGREGATED"));
            // ... parsear otros campos ...
        }
    }

    private String extractFieldValue(Element parent, String fieldName) {
        Element extraFields = parent.getChild("EXTRA_FIELDS");
        if (extraFields != null) {
            for (Element field : extraFields.getChildren("FIELD")) {
                String key = getText(field, "key");
                if (fieldName.equals(key)) {
                    return getText(field, "value");
                }
            }
        }
        return null;
    }

    private String getText(Element parent, String childName) {
        Element child = parent.getChild(childName);
        return (child != null) ? child.getTextTrim() : null;
    }


}
