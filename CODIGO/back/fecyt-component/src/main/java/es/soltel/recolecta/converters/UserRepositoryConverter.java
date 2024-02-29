package es.soltel.recolecta.converters;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.UserRepositoryEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryConverter {

    public static es.soltel.recolecta.vo.UserRepositoryVO toVO(UserRepositoryEntity entity) {

        if (entity == null)
            return null;

        es.soltel.recolecta.vo.UserRepositoryVO vo = new es.soltel.recolecta.vo.UserRepositoryVO();
        vo.setId(entity.getId());
        if (entity.getUser() != null)
            vo.setUser(UserConverter.toVO(entity.getUser()));
        if (entity.getRepository() != null)
            vo.setRepository(RepositoryConverter.convertEntityToVO(entity.getRepository()));
        vo.setNDeleteState(entity.getNDeleteState());

        return vo;
    }

    public static UserRepositoryEntity toEntity(es.soltel.recolecta.vo.UserRepositoryVO vo) {

        if (vo == null)
            return null;

        UserRepositoryEntity entity = new UserRepositoryEntity();
        entity.setId(vo.getId());
        if (vo.getUser() != null)
            entity.setUser(UserConverter.toEntity(vo.getUser()));
        if (vo.getRepository() != null)
            entity.setRepository(RepositoryConverter.convertVOToEntity(vo.getRepository()));
        entity.setNDeleteState(vo.getNDeleteState());

        return entity;
    }

    public static List<es.soltel.recolecta.vo.UserRepositoryVO> ToVO(List<UserRepositoryEntity> entities) {
        return entities.stream().map(UserRepositoryConverter::toVO).collect(Collectors.toList());
    }

    public static List<UserRepositoryEntity> vosToEntities(List<es.soltel.recolecta.vo.UserRepositoryVO> vos) {
        return vos.stream().map(UserRepositoryConverter::toEntity).collect(Collectors.toList());
    }
}
