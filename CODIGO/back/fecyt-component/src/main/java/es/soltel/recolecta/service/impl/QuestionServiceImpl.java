package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.QuestionEntity;
import es.soltel.recolecta.entity.TitleEntity;
import es.soltel.recolecta.repository.QuestionRepository;
import es.soltel.recolecta.repository.TitleRepository;
import es.soltel.recolecta.service.QuestionService;
import es.soltel.recolecta.converters.QuestionConverter;
import es.soltel.recolecta.converters.TitleConverter;
import es.soltel.recolecta.vo.QuestionByYearAndFileDTO;
import es.soltel.recolecta.vo.QuestionVO;
import es.soltel.recolecta.vo.QuestionnaireVO;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TitleService titleService;
    
    @Autowired
    private TitleRepository titleRepository;

    @Override
    public List<QuestionEntity> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public QuestionEntity getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public QuestionVO createQuestion(QuestionVO question) {
    	if (question.getTitle().getId() == null || question.getTitle().getId() == 0) {
            // Si la ID es nula o 0, crea el título
            TitleEntity titleEntity = TitleConverter.voToEntity(question.getTitle());
            titleEntity = titleRepository.save(titleEntity);
            
            // Asigna la ID generada al objeto de pregunta
            question.setTitle(TitleConverter.entityToVo(titleEntity));
    	}
    	
        return QuestionConverter.convertToVO(questionRepository.save(QuestionConverter.convertToEntity(question)));
    }

    @Override
    public QuestionVO updateQuestion(QuestionVO question) {

        titleService.update(question.getTitle());

        return QuestionConverter.convertToVO(questionRepository.save(QuestionConverter.convertToEntity(question)));
    }


    @Override
    public void deleteQuestion(Long id) {
        QuestionEntity question = getQuestionById(id);
        if (question != null) {
            question.setnDeleteState(2);
            questionRepository.save(question);
        }
    }

    @Override
    public List<QuestionVO> getQuestionsByEvaluationPeriodId(Long id) {
    	List<QuestionVO> entities = QuestionConverter.ToVO(questionRepository.findByPeriodId(id));
    	return entities.stream()
                .filter(entity -> entity.getnDeleteState() != 2)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<QuestionByYearAndFileDTO> getQuestionsWithFileByYear(Long idType, Long year) {
    	List<Object[]> entities = questionRepository.findByYear(idType, year);
    	List<QuestionByYearAndFileDTO> dtos = new ArrayList<>();

        for (Object[] entity : entities) {
        	QuestionByYearAndFileDTO dto = new QuestionByYearAndFileDTO();
            dto.setQuestionId((BigInteger) entity[0]);
            dto.setDescription((String) entity[1]);
            java.sql.Timestamp timestamp = (java.sql.Timestamp) entity[2];
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            // Asigna el valor a tu objeto DTO
            dto.setFinishDate(localDateTime);
            dto.setTitle((String) entity[3]);
            dto.setCategoryType((String) entity[4]);
            dto.setIsWritableByEvaluator((Integer) entity[5]);
            dto.setTypeQuestion((String) entity[6]);
            dto.setWeight((Float) entity[7]);
            dto.setInstitution((String) entity[8]);
            dto.setUserName((String) entity[9]);
            dto.setUserSurname((String) entity[10]);

            dtos.add(dto);
        }

        return dtos;
    }

    /* 
    @Override
    public List<QuestionVO> getQuestionsByEvaluationId(Long id) {
    	List<QuestionVO> entities = QuestionConverter.ToVO(questionRepository.findByEvaluationId(id));
    	return entities.stream()
                .filter(entity -> entity.getnDeleteState() != 2)
                .collect(Collectors.toList());
    }
    */

    @Override
    public List<QuestionVO> insertQuestionSet(List<QuestionVO> questions) {
        List<QuestionEntity> questionEntities = QuestionConverter.vosToEntities(questions);
        
        List<QuestionEntity> savedEntities = new ArrayList<>();
        for (QuestionEntity entity : questionEntities) {
            savedEntities.add(questionRepository.save(entity));
        }

        return QuestionConverter.ToVO(savedEntities);
    }

    @Override
    public Set<QuestionVO> getQuestionsByEvaluations(List<QuestionnaireVO> questionnaires) {
        
        Set<QuestionVO> results = new HashSet<>();

        for (QuestionnaireVO questionnaire: questionnaires){

            results.addAll(getQuestionsByEvaluationPeriodId(questionnaire.getPeriod().getId()));

        }

        return results;

    }

    


    
}
