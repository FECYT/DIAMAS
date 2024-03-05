package es.soltel.recolecta.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lowagie.text.pdf.codec.Base64;
import es.soltel.herramientaevaluador.common.model.RegisterBean;
import es.soltel.recolecta.converters.RolConverter;
import es.soltel.recolecta.converters.RolRelationConverter;
import es.soltel.recolecta.repository.RolRelationRepository;
import es.soltel.recolecta.repository.RolRepository;
import es.soltel.recolecta.service.ConfigService;
import es.soltel.recolecta.service.RolRelationService;
import es.soltel.recolecta.utils.Base64Util;
import es.soltel.recolecta.utils.PasswordSecurity;
import es.soltel.recolecta.vo.ChangePasswordBean;
import es.soltel.recolecta.vo.RolRelationVO;
import es.soltel.recolecta.vo.UserPlusRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.UserEntity;
import es.soltel.recolecta.repository.UserRepository;
import es.soltel.recolecta.service.UserService;
import es.soltel.recolecta.converters.UserConverter;
import es.soltel.recolecta.vo.UserVO;

@Service
public class UsersServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    private ConfigService configService;

    @Autowired
    private RolRelationService rolRelationService;

    @Autowired
    private RolRelationRepository rolRelationRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    public UsersServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserVO> findAll() {
        return repository.findAll().stream().map(UserConverter::toVO).collect(Collectors.toList());
    }

    @Override
    public UserVO findById(Long id) {
        Optional<UserEntity> entity = repository.findById(id);
        return entity.map(UserConverter::toVO).orElse(null);
    }

    @Override
    public UserVO findByRepositoryId(Long id) {
        return UserConverter.toVO(repository.findUserByRepoId(id));
    }

    @Override
    public UserVO findByEmail(String email) {
    	UserEntity user = repository.findByEmail(email);

        // Verificar si el usuario fue encontrado
        if (user != null) {
            return UserConverter.toVO(user);
        } else {
            // Manejar el caso en el que el usuario no es encontrado (puedes devolver null o lanzar una excepción, según tus necesidades)
            return null;
        }
    }

    @Override
    public void registerUser(RegisterBean newUser) {
        repository.save(UserConverter.toEntity(UserConverter.registerUserToVo(newUser,configService.getAutomaticRegister())));
    }

    @Override
    public List<UserVO> findAdmins(){
        return UserConverter.ToVO(repository.findUsersByRoleName("ADMINISTRADOR"));
    }


    @Override
    public UserVO create(UserVO user) {
        UserEntity entity = UserConverter.toEntity(user);
        entity = repository.save(entity);
        return UserConverter.toVO(entity);
    }

    @Override
    @Async
    public UserVO createAsincrono(UserVO user) {
        UserEntity entity = UserConverter.toEntity(user);
        entity = repository.save(entity);
        return UserConverter.toVO(entity);
    }


    @Override
    public UserVO update(UserVO user) {

        UserEntity original = repository.findById(user.getId()).orElse(null);

        UserEntity entity = UserConverter.toEntity(user);

        entity.setPassword(original.getPassword());

        entity = repository.save(entity);
        return UserConverter.toVO(entity);
    }

    @Override
    public void updatePlusRoles(UserPlusRoles user) {

        if (user.getRolesIds() == null) return;

        UserEntity originalUser = repository.findById(user.getUser().getId()).orElse(null);

        user.getUser().setPassword(originalUser.getPassword());
        repository.save(UserConverter.toEntity(user.getUser()));

        List<RolRelationVO> currentRoles = rolRelationService.getUserRoles(user.getUser().getId());
        List<Long> userRolesIds = user.getRolesIds();

        List<RolRelationVO> rolesToDelete = new ArrayList<>();
        List<Long> rolesToAdd = new ArrayList<>();

        for (RolRelationVO rolRelation : currentRoles) {
            if (!userRolesIds.contains(rolRelation.getRol().getId())) {
                rolesToDelete.add(rolRelation);
            }
        }

        for (Long roleId : userRolesIds) {
            if (currentRoles.stream().noneMatch(rolRelation -> rolRelation.getRol().getId().equals(roleId))) {
                rolesToAdd.add(roleId.longValue());
            }
        }

        for (RolRelationVO rolToDelete : rolesToDelete) {
            rolRelationRepository.delete(RolRelationConverter.convertToEntity(rolToDelete));
        }


        for (Long roleIdToAdd : rolesToAdd) {

            RolRelationVO newRelation = new RolRelationVO();
            newRelation.setUser(user.getUser());
            newRelation.setRol(RolConverter.convertToVo(Objects.requireNonNull(rolRepository.findById(roleIdToAdd).orElse(null))));

            rolRelationRepository.save(RolRelationConverter.convertToEntity(newRelation));
        }
    }


    @Override
    public void toggleUser(Long userId, Boolean status) {
        UserEntity user = repository.findById(userId).orElse(null);
        user.setActive(status);
        repository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordBean changePasswordBean) {

        changePasswordBean.setEmail(Base64Util.decodificarDesdeBase64(changePasswordBean.getEmail()));
        changePasswordBean.setPassword(Base64Util.decodificarDesdeBase64(changePasswordBean.getPassword()));

        UserEntity user = repository.findByEmail(changePasswordBean.getEmail());

        String hashedPassword = PasswordSecurity.encrypt(changePasswordBean.getPassword());
        user.setPassword(hashedPassword);

        repository.save(user);

    }

    @Override
    public void delete(Long id) {
        Optional<UserEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            UserEntity userEntity = entity.get();
            userEntity.setNDeleteState(2);
            repository.save(userEntity);
        }
    }

}
