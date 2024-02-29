package es.soltel.recolecta.converters;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.vo.CatQuestionVO;

@Component
public class CatQuestionConverter {

    public static CatQuestionVO entityToVo(CatQuestionEntity entity) {
        CatQuestionVO vo = new CatQuestionVO();
        vo.setId(entity.getId());
        vo.setCategoryType(entity.getCategoryType());
        vo.setTooltipType(entity.getTooltipType());
        vo.setNDeleteState(entity.getNDeleteState());
        vo.setOrden(entity.getOrden());
        vo.setHasQuestion(entity.getQuestions().size() > 0);
        if (entity.getQuestionnaireType() != null) vo.setQuestionnaireType(QuestionnaireTypeConverter.entityToVo(entity.getQuestionnaireType()));
        return vo;
    }

    public static CatQuestionEntity voToEntity(CatQuestionVO vo) {
        CatQuestionEntity entity = new CatQuestionEntity();
        entity.setId(vo.getId());
        entity.setCategoryType(vo.getCategoryType());
        entity.setTooltipType(vo.getTooltipType());
        entity.setNDeleteState(vo.getNDeleteState());
        entity.setOrden(vo.getOrden());
        if (vo.getQuestionnaireType() != null) entity.setQuestionnaireType(QuestionnaireTypeConverter.voToEntity(vo.getQuestionnaireType()));
        return entity;
    }
}
