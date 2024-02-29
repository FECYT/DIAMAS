package es.soltel.recolecta.converters;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import es.soltel.herramientaevaluador.common.model.RegisterBean;
import es.soltel.recolecta.entity.UserEntity;
import es.soltel.recolecta.vo.UserVO;

public class UserConverter {

    public static UserVO toVO(UserEntity entity) {

        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setLastLogin(entity.getLastLogin());
        vo.setNombre(entity.getNombre());
        vo.setApellidos(entity.getApellidos());
        vo.setNDeleteState(entity.getNDeleteState());
        vo.setEmail(entity.getEmail());
        vo.setPassword(entity.getPassword());
        vo.setActive(entity.getActive());
        vo.setAfiliacion_institucional(entity.getAfiliacion_institucional());
        vo.setPais(entity.getPais());

        //vo.setRoles(RolRelationConverter.ToVO(entity.getRoles()));

        if (entity.getRoles() != null){
            // Obtén una lista de los nombres de las entidades concatenados con comas
            List<String> namesList = entity.getRoles().stream()
                    .map(rolRelation -> rolRelation.getRol().getName())
                    .collect(Collectors.toList());

            vo.setRol(namesList);
        }

        return vo;
    }
    
    public static List<UserVO> toVOList(UserEntity entity) {
        if (entity != null) {
            UserVO vo = toVO(entity);
            return Collections.singletonList(vo);
        }
        return Collections.emptyList();
    }

    public static UserEntity toEntity(UserVO vo) {

        UserEntity entity = new UserEntity();

        entity.setId(vo.getId());
        entity.setLastLogin(vo.getLastLogin());
        entity.setNombre(vo.getNombre());
        entity.setApellidos(vo.getApellidos());
        entity.setNDeleteState(vo.getNDeleteState());
        entity.setEmail(vo.getEmail());
        entity.setPassword(vo.getPassword());
        entity.setActive(vo.getActive());
        entity.setAfiliacion_institucional(vo.getAfiliacion_institucional());
        entity.setPais(vo.getPais());

        //entity.setRoles(RolRelationConverter.ToEntity(vo.getRoles()));

        return entity;
    }

    public static UserVO registerUserToVo(RegisterBean newUser, Boolean active){

        UserVO vo = new UserVO();

        vo.setNombre(newUser.getNombre());
        vo.setApellidos(newUser.getApellidos());
        vo.setNDeleteState(1);
        vo.setEmail(newUser.getEmail());
        vo.setPassword(newUser.getPassword());
        vo.setActive(active);
        vo.setAfiliacion_institucional(newUser.getAfiliacion_institucional());
        vo.setPais(newUser.getPais());

        return vo;
    }

    public static UserEntity toEntityFromList(List<UserVO> vos) {
        if (vos != null && !vos.isEmpty()) {
            UserVO vo = vos.get(0);
            return toEntity(vo);
        }
        return null; // o lanza una excepción o devuelve un valor por defecto según tus necesidades
    }

    
    public static List<UserVO> ToVO(List<UserEntity> entities) {
        return entities.stream().map(UserConverter::toVO).collect(Collectors.toList());
    }

    public static List<UserEntity> vosToEntities(List<UserVO> vos) {
        return vos.stream().map(UserConverter::toEntity).collect(Collectors.toList());
    }
}
