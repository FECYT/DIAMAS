package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.converters.RolRelationConverter;
import es.soltel.recolecta.converters.TitleConverter;
import es.soltel.recolecta.entity.RolEntity;
import es.soltel.recolecta.entity.RolRelationEntity;
import es.soltel.recolecta.entity.UserEntity;
import es.soltel.recolecta.repository.RolRelationRepository;
import es.soltel.recolecta.repository.RolRepository;
import es.soltel.recolecta.repository.TitleRepository;
import es.soltel.recolecta.repository.UserRepository;
import es.soltel.recolecta.service.RolRelationService;
import es.soltel.recolecta.service.TitleService;
import es.soltel.recolecta.vo.RolRelationVO;
import es.soltel.recolecta.vo.TitleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TitleServiceImpl implements TitleService {


    @Autowired
    private TitleRepository titleRepository;

    @Override
    public TitleVO update(TitleVO titleVO) {
        return TitleConverter.entityToVo(titleRepository.save(TitleConverter.voToEntity(titleVO)));
    }
}
