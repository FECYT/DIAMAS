package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.vo.InstitucionVO;

public interface InstitucionService {

    List<InstitucionVO> getAll();

    InstitucionVO getById(Long id);

    InstitucionVO create(InstitucionVO vo);

    InstitucionVO update(InstitucionVO vo);

    void delete(Long id);
    
    InstitucionVO findByInstitucionName(String nombre);

    InstitucionVO findByUserId(Long id);

}

