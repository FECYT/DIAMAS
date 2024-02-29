package es.soltel.recolecta.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.SystemLogsEntity;
import es.soltel.recolecta.vo.SystemLogsVO;

@Component
public class SystemLogsConverter {
    public SystemLogsVO toVO(SystemLogsEntity entity) {
        SystemLogsVO vo = new SystemLogsVO();
        vo.setId(entity.getId());
        vo.setUserId(entity.getIdUserDNET());
        vo.setActionType(entity.getActionType());
        vo.setDateTime(entity.getDateTime());
        vo.setSourceIp(entity.getSourceIp());
        vo.setStatus(entity.getStatus());
        vo.setNDeleteState(entity.getNDeleteState());
        return vo;
    }

    public List<SystemLogsVO> toVOList(List<SystemLogsEntity> entities) {
        return entities.stream().map(this::toVO).collect(Collectors.toList());
    }

    public SystemLogsEntity toEntity(SystemLogsVO vo) {
        SystemLogsEntity entity = new SystemLogsEntity();
        entity.setId(vo.getId());
        entity.setActionType(vo.getActionType());
        entity.setDateTime(vo.getDateTime());
        entity.setSourceIp(vo.getSourceIp());
        entity.setStatus(vo.getStatus());
        entity.setNDeleteState(vo.getNDeleteState());
        entity.setIdUserDNET(vo.getUserId());
        entity.setNumRepositories(vo.getNumRepositories());
        return entity;
    }

    public void updateEntityFromVO(SystemLogsVO vo, SystemLogsEntity entity) {
        entity.setActionType(vo.getActionType());
        entity.setDateTime(vo.getDateTime());
        entity.setSourceIp(vo.getSourceIp());
        entity.setStatus(vo.getStatus());
        entity.setNDeleteState(vo.getNDeleteState());
    }
}

