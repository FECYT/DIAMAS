package es.soltel.recolecta.converters;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.EvaluationEntity;
import es.soltel.recolecta.vo.EvaluationVO;
import es.soltel.recolecta.vo.UserRepositoryVO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluationConverter {

    public static EvaluationVO entityToVO(EvaluationEntity entity) {
    	if (entity == null) {
            return null; // Handle the case where the input entity is null
        }
    	
        EvaluationVO vo = new EvaluationVO();
        vo.setId(entity.getId());
        vo.setLastEdited(entity.getLastEdited());
        vo.setEvaluationState(entity.getEvaluationState());
        vo.setCloseDate(entity.getCloseDate());
        vo.setEvaluationGrade(entity.getEvaluationGrade());
        vo.setnDeleteState(entity.getnDeleteState());
        
        if (entity.getUserRepository() != null) 
    	{
        	vo.setUserRepository(UserRepositoryConverter.toVO(entity.getUserRepository()));
            vo.getUserRepository().removePassword();
    	}
        
        if (entity.getQuestionnaireType() != null) vo.setQuestionnaireType(QuestionnaireTypeConverter.entityToVo(entity.getQuestionnaireType()));
        
        return vo;
    }

    public static EvaluationEntity ToEntity(EvaluationVO vo) {
    	if (vo == null) {
            return null; // Handle the case where the input vo is null
        }
        EvaluationEntity entity = new EvaluationEntity();
        entity.setId(vo.getId());
        entity.setLastEdited(vo.getLastEdited());
        entity.setEvaluationState(vo.getEvaluationState());
        entity.setCloseDate(vo.getCloseDate());
        entity.setEvaluationGrade(vo.getEvaluationGrade());
        entity.setnDeleteState(vo.getnDeleteState());
        
        
        if (vo.getUserRepository() != null) 
    	{
            vo.getUserRepository().removePassword();
        	entity.setUserRepository(UserRepositoryConverter.toEntity(vo.getUserRepository()));
    	}
        
        if (vo.getQuestionnaireType() != null) entity.setQuestionnaireType(QuestionnaireTypeConverter.voToEntity(vo.getQuestionnaireType()));
        
        return entity;
    }

    public static List<EvaluationVO> ToVO(List<EvaluationEntity> entities) {
        return entities.stream().map(EvaluationConverter::entityToVO).collect(Collectors.toList());
    }

    public static List<EvaluationEntity> vosToEntities(List<EvaluationVO> vos) {
        return vos.stream().map(EvaluationConverter::ToEntity).collect(Collectors.toList());
    }
}
