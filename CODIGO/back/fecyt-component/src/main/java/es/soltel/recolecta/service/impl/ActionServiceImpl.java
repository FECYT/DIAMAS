package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.entity.ActionEntity;
import es.soltel.recolecta.repository.ActionRepository;
import es.soltel.recolecta.service.ActionService;
import es.soltel.recolecta.vo.ActionVO;
import es.soltel.recolecta.converters.ActionConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionConverter actionConverter;

    @Override
    public ActionVO createAction(ActionVO action) {
        ActionEntity actionEntity = actionConverter.convertToEntity(action);
        actionRepository.save(actionEntity);
        return actionConverter.convertToVo(actionEntity);
    }

    @Override
    public ActionVO updateAction(ActionVO action) {
        ActionEntity actionEntity = actionRepository.findById(action.getId()).orElse(null);
        if (actionEntity != null) {
            actionEntity.setTypeAction(action.getTypeAction());
            actionEntity.setDescription(action.getDescription());
            actionEntity.setnDeleteState(action.getnDeleteState());
            actionRepository.save(actionEntity);
        }
        return actionConverter.convertToVo(actionEntity);
    }

    @Override
    public ActionVO getActionById(Long id) {
        ActionEntity actionEntity = actionRepository.findById(id).orElse(null);
        return actionConverter.convertToVo(actionEntity);
    }

    @Override
    public List<ActionVO> getAllActions() {
        return actionRepository.findAll().stream().map(ActionConverter::convertToVo).collect(Collectors.toList());
    }

    @Override
    public void deleteAction(Long id) {
        ActionEntity actionEntity = actionRepository.findById(id).orElse(null);
        if (actionEntity != null) {
            actionEntity.setnDeleteState(2);
            actionRepository.save(actionEntity);
        }
    }

    @Override
    public List<ActionVO> getAllDeletedActions() {
        return actionRepository.findBynDeleteState(2).stream().map(ActionConverter::convertToVo).collect(Collectors.toList());
    }
}

