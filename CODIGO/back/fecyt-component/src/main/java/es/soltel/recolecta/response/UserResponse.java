package es.soltel.recolecta.response;

import java.util.List;

public class UserResponse {

    private String userId;
    private List<String> roles;
    private String registrationDate;
    private boolean recommendationSendEmail;
    private String password;
    private String passdate;
    private boolean isUserManager;
    private boolean isSuperUser;
    private boolean isCommunityManager;
    private boolean isCollectionManager;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isRecommendationSendEmail() {
        return recommendationSendEmail;
    }

    public void setRecommendationSendEmail(boolean recommendationSendEmail) {
        this.recommendationSendEmail = recommendationSendEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassdate() {
        return passdate;
    }

    public void setPassdate(String passdate) {
        this.passdate = passdate;
    }

    public boolean isUserManager() {
        return isUserManager;
    }

    public void setIsUserManager(boolean isUserManager) {
        this.isUserManager = isUserManager;
    }

    public boolean isSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(boolean isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    public boolean isCommunityManager() {
        return isCommunityManager;
    }

    public void setIsCommunityManager(boolean isCommunityManager) {
        this.isCommunityManager = isCommunityManager;
    }

    public boolean isCollectionManager() {
        return isCollectionManager;
    }

    public void setIsCollectionManager(boolean isCollectionManager) {
        this.isCollectionManager = isCollectionManager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
