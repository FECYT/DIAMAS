package es.soltel.recolecta.service;

import es.soltel.recolecta.vo.PasswordRecoverVO;

import javax.transaction.Transactional;

public interface PasswordRecoverService {

    Boolean isCodeCorrect(PasswordRecoverVO entry);

    @Transactional
    void generateRecoverCode(String email);

}