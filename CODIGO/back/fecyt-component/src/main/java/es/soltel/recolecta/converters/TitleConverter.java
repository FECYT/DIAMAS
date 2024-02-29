package es.soltel.recolecta.converters;

import es.soltel.recolecta.entity.CatQuestionEntity;
import es.soltel.recolecta.entity.TitleEntity;
import es.soltel.recolecta.vo.CatQuestionVO;
import es.soltel.recolecta.vo.TitleVO;
import org.springframework.stereotype.Component;

@Component
public class TitleConverter {

    public static TitleVO entityToVo(TitleEntity entity) {
        TitleVO vo = new TitleVO();
        vo.setId(entity.getId());
        vo.setEng(entity.getEng());
        vo.setEsp(entity.getEsp());
        return vo;
    }

    public static TitleEntity voToEntity(TitleVO vo) {
        TitleEntity entity = new TitleEntity();
        entity.setId(vo.getId());
        entity.setEng(vo.getEng());
        entity.setEsp(vo.getEsp());
        return entity;
    }
}
