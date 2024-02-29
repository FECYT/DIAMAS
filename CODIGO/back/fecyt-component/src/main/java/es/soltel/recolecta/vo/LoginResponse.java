package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

public class LoginResponse {

    private UserVO userVO;
    private int responseCode;

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
