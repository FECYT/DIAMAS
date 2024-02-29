package es.soltel.recolecta.converters;

import es.soltel.recolecta.entity.ActionEntity;
import es.soltel.recolecta.vo.ActionVO;
import org.springframework.stereotype.Component;

@Component
public class ActionConverter {

    public static ActionVO convertToVo(ActionEntity actionEntity) {
        ActionVO actionVO = new ActionVO();
        actionVO.setId(actionEntity.getId());
        actionVO.setTypeAction(actionEntity.getTypeAction());
        actionVO.setDescription(actionEntity.getDescription());
        actionVO.setnDeleteState(actionEntity.getnDeleteState());
        return actionVO;
    }

    public static ActionEntity convertToEntity(ActionVO actionVO) {
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setId(actionVO.getId());
        actionEntity.setTypeAction(actionVO.getTypeAction());
        actionEntity.setDescription(actionVO.getDescription());
        actionEntity.setnDeleteState(actionVO.getnDeleteState());
        return actionEntity;
    }

    public ActionVO ToVO(ActionEntity actionId) {
        return null;
    }
}
