package es.soltel.recolecta.vo;

import java.util.List;

public class UserPlusRoles {

    private UserVO user;
    private List<Long> rolesIds;

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public List<Long> getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(List<Long> rolesIds) {
        this.rolesIds = rolesIds;
    }
}

