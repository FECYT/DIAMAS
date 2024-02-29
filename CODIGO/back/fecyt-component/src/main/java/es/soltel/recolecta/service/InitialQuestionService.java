package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.entity.InitialQuestionEntity;

public interface InitialQuestionService{
    List<InitialQuestionEntity> findAll();
    List<InitialQuestionEntity> findAllWithSameType(Long idType);
}
