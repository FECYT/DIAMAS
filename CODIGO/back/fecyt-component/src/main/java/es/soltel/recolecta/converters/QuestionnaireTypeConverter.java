package es.soltel.recolecta.converters;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.entity.QuestionnaireTypeEntity;
import es.soltel.recolecta.vo.CatQuestionVO;
import es.soltel.recolecta.vo.QuestionnaireTypeVO;

@Component
public class QuestionnaireTypeConverter {

    public static QuestionnaireTypeVO entityToVo(QuestionnaireTypeEntity entity) {
    	QuestionnaireTypeVO vo = new QuestionnaireTypeVO();
        vo.setId(entity.getId());
        vo.setEs(entity.getEs());
        vo.setEn(entity.getEn());
        vo.setnDeleteState(entity.getNDeleteState());
        return vo;
    }

    public static QuestionnaireTypeEntity voToEntity(QuestionnaireTypeVO vo) {
    	QuestionnaireTypeEntity entity = new QuestionnaireTypeEntity();
        entity.setId(vo.getId());
        entity.setEs(vo.getEs());
        entity.setEn(vo.getEn());
        entity.setNDeleteState(vo.getnDeleteState());
        return entity;
    }
}
