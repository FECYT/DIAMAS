package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.converters.RolRelationConverter;
import es.soltel.recolecta.entity.RolEntity;
import es.soltel.recolecta.entity.RolRelationEntity;
import es.soltel.recolecta.entity.UserEntity;
import es.soltel.recolecta.repository.RolRelationRepository;
import es.soltel.recolecta.repository.RolRepository;
import es.soltel.recolecta.repository.UserRepository;
import es.soltel.recolecta.service.*;
import es.soltel.recolecta.vo.RolRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;


@Service
public class RolServiceImpl implements RolRelationService {

    @Autowired
    private RolRelationRepository repository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolRelationService service;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void setUserRoles(Long userId, List<Long> roles) {

        List<RolEntity> rolesEntity = rolRepository.findAllByIdIn(roles);

        UserEntity user = userRepository.findById(userId).orElse(null);

        for (RolEntity rol : rolesEntity) {

            RolRelationEntity entry = new RolRelationEntity();

            entry.setRol(rol);
            entry.setUser(user);

            repository.save(entry);

        }

    }


    @Override
    public List<RolRelationVO> getUserRoles(Long userId) {
        return RolRelationConverter.ToVO(repository.findByUserId(userId));
    }



}
