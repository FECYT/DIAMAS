package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.herramientaevaluador.common.model.RegisterBean;
import es.soltel.recolecta.vo.ChangePasswordBean;
import es.soltel.recolecta.vo.UserPlusRoles;
import es.soltel.recolecta.vo.UserVO;

public interface UserService {

    List<UserVO> findAll();

    UserVO findById(Long id);

    UserVO findByRepositoryId(Long id);

    List<UserVO> findAdmins();

    UserVO create(UserVO user);

    UserVO update(UserVO user);

    void updatePlusRoles(UserPlusRoles user);
    void toggleUser(Long userId, Boolean status);

    void changePassword(ChangePasswordBean changePasswordBean);

    void delete(Long id);

    List<UserVO> findUsersNotInRepository(Long repositoryId);

    UserVO createAsincrono(UserVO user);

    UserVO findByIdDnet(String idDnet);

    UserVO findByEmail(String email);

    void registerUser(RegisterBean newUser);
}
