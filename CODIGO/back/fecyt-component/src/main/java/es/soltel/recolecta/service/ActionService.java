package es.soltel.recolecta.service;

import es.soltel.recolecta.vo.ActionVO;

import java.util.List;

public interface ActionService {

    ActionVO createAction(ActionVO action);

    ActionVO updateAction(ActionVO action);

    ActionVO getActionById(Long id);

    List<ActionVO> getAllActions();

    void deleteAction(Long id);

    List<ActionVO> getAllDeletedActions();

}

