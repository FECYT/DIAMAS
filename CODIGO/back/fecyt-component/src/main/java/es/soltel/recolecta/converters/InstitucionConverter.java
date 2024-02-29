package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import es.soltel.recolecta.entity.InstitucionEntity;
import es.soltel.recolecta.vo.InstitucionVO;

public class InstitucionConverter {

    public static InstitucionVO convertEntityToVO(InstitucionEntity entity) {
    	if (entity == null) {
            return null; // or throw an exception, depending on your logic
        }
    	
    	InstitucionVO vo = new InstitucionVO();
        vo.setId(entity.getId());
        vo.setNombre(entity.getNombre());
        vo.setAcronimo(entity.getAcronimo());
        vo.setUrl(entity.getUrl());
        vo.setnDeleteState(entity.getnDeleteState());
        return vo;
    }

    public static InstitucionEntity convertVOToEntity(InstitucionVO vo) {
    	if (vo == null) {
            return null; // or throw an exception, depending on your logic
        }
    	
    	InstitucionEntity entity = new InstitucionEntity();
        entity.setId(vo.getId());
        entity.setNombre(vo.getNombre());
        entity.setAcronimo(vo.getAcronimo());
        entity.setUrl(vo.getUrl());
        entity.setnDeleteState(vo.getnDeleteState());
        return entity;
    }


    public static List<InstitucionVO> ToVO(List<InstitucionEntity> entities) {
        return entities.stream().map(InstitucionConverter::convertEntityToVO).collect(Collectors.toList());
    }

    public static List<InstitucionEntity> vosToEntities(List<InstitucionVO> vos) {
        return vos.stream().map(InstitucionConverter::convertVOToEntity).collect(Collectors.toList());
    }
}

