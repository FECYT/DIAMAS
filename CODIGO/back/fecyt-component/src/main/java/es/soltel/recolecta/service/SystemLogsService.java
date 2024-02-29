package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.SystemLogsVO;

public interface SystemLogsService {
    SystemLogsVO create(SystemLogsVO systemLogsVO);
    SystemLogsVO getById(Long id);
    SystemLogsVO update(Long id, SystemLogsVO systemLogsVO);
    void delete(Long id);
    List<SystemLogsVO> getAll();
}
