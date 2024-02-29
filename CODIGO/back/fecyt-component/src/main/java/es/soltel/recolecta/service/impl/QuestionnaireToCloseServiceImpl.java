package es.soltel.recolecta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.repository.QuestionnaireToCloseRepository;
import es.soltel.recolecta.service.QuestionnaireToCloseService;
import es.soltel.recolecta.converters.QuestionnaireToCloseConverter;
import es.soltel.recolecta.vo.QuestionnaireToCloseVO;

@Service
public class QuestionnaireToCloseServiceImpl implements QuestionnaireToCloseService {

    @Autowired
    private QuestionnaireToCloseRepository repository;


    @Override
    public List<QuestionnaireToCloseVO> getAll() {
        return QuestionnaireToCloseConverter.ToVO(repository.findAll());
    }

    @Override
    public QuestionnaireToCloseVO getById(Long id) {
        return QuestionnaireToCloseConverter.toVO(repository.findById(id).orElse(null));
    }

    @Override
    public QuestionnaireToCloseVO create(QuestionnaireToCloseVO vo) {
        return QuestionnaireToCloseConverter.toVO(repository.save(QuestionnaireToCloseConverter.toEntity(vo)));
    }

    @Override
    public QuestionnaireToCloseVO update(QuestionnaireToCloseVO vo) {
        return QuestionnaireToCloseConverter.toVO(repository.save(QuestionnaireToCloseConverter.toEntity(vo)));
    }

    @Override
    public void delete(Long id) {
        repository.hardDeleteById(id);
    }

    @Override
    public QuestionnaireToCloseVO getByQuestionnaireId(Long id) {
        return QuestionnaireToCloseConverter.toVO(repository.findByQuestionnaireId(id));
    }

}