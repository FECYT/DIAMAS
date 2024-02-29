package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import es.soltel.recolecta.entity.RepositoryEntity;
import es.soltel.recolecta.vo.RepositoryVO;

public class RepositoryConverter {

    public static RepositoryVO convertEntityToVO(RepositoryEntity entity) {
        RepositoryVO vo = new RepositoryVO();
        vo.setId(entity.getId());
        if (entity.getInstitucion() != null) vo.setInstitucion(InstitucionConverter.convertEntityToVO(entity.getInstitucion()));
        vo.setnDeleteState(entity.getnDeleteState());
        return vo;
    }

    public static RepositoryEntity convertVOToEntity(RepositoryVO vo) {
        RepositoryEntity entity = new RepositoryEntity();
        entity.setId(vo.getId());
        if (vo.getInstitucion() != null) entity.setInstitucion(InstitucionConverter.convertVOToEntity(vo.getInstitucion()));
        entity.setnDeleteState(vo.getnDeleteState());
        return entity;
    }


    public static List<RepositoryVO> ToVO(List<RepositoryEntity> entities) {
        return entities.stream().map(RepositoryConverter::convertEntityToVO).collect(Collectors.toList());
    }

    public static List<RepositoryEntity> vosToEntities(List<RepositoryVO> vos) {
        return vos.stream().map(RepositoryConverter::convertVOToEntity).collect(Collectors.toList());
    }
}

