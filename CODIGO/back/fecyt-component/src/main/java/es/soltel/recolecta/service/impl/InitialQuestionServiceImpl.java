package es.soltel.recolecta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.InitialQuestionEntity;
import es.soltel.recolecta.repository.InitialQuestionRepository;
import es.soltel.recolecta.service.InitialQuestionService;

@Service
public class InitialQuestionServiceImpl implements InitialQuestionService {
    
    @Autowired
    private InitialQuestionRepository initialQuestionRepository;

    @Override
    public List<InitialQuestionEntity> findAll() {
        return initialQuestionRepository.findAll();
    }
    
    @Override
    public List<InitialQuestionEntity> findAllWithSameType(Long idType) {
        return initialQuestionRepository.findAllWithSameType(idType);
    }

}
