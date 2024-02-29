package es.soltel.recolecta.service.impl;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.entity.PasswordRecoverEntity;
import es.soltel.recolecta.entity.UserEntity;
import es.soltel.recolecta.repository.PasswordRecoverRepository;
import es.soltel.recolecta.repository.UserRepository;
import es.soltel.recolecta.service.MailSending;
import es.soltel.recolecta.utils.Base64Util;
import es.soltel.recolecta.utils.CodeGenerator;
import es.soltel.recolecta.vo.PasswordRecoverVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PasswordRecoverServiceImpl implements es.soltel.recolecta.service.PasswordRecoverService {

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSending mailSending;

    private ExecutorService executorService = Executors.newFixedThreadPool(2);


    @NoLogging
    @Override
    public Boolean isCodeCorrect(PasswordRecoverVO entry) {

        PasswordRecoverEntity entity = passwordRecoverRepository.findByUserEmail(entry.getEmail());

        Boolean result = entity.getCode().equals(entry.getCode());
        if (result) { passwordRecoverRepository.deleteByUserId(entity.getUser().getId());}

        return result;
    }

    @NoLogging
    @Override
    @Transactional
    public void generateRecoverCode(String email) {

        email = Base64Util.decodificarDesdeBase64(email);

        //Buscar passwordRecover y borrar
        PasswordRecoverEntity passEntity = passwordRecoverRepository.findByUserEmail(email);
        if (passEntity != null) passwordRecoverRepository.deleteByUserId(passEntity.getUser().getId());

        PasswordRecoverEntity entry = new PasswordRecoverEntity();

        //Buscar user
        UserEntity user = userRepository.findByEmail(email);
        if (user == null){
            //Usuario no existe en la BBDD, por tanto no se sigue
            return;
        }

        //Generar codigo
        String code = CodeGenerator.generateCode();

        entry.setCode(code);
        entry.setUser(user);

        passwordRecoverRepository.save(entry);

        String finalEmail = email;
        executorService.submit(() -> mailSending.sendMailUsuarioCambiarContraseña(finalEmail,code));

    }

}

