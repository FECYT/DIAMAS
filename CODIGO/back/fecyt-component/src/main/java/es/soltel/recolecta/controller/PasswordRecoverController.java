package es.soltel.recolecta.controller;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.service.ActionService;
import es.soltel.recolecta.service.PasswordRecoverService;
import es.soltel.recolecta.vo.ActionVO;
import es.soltel.recolecta.vo.PasswordRecoverVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/passwordRecover")
public class PasswordRecoverController {

    @Autowired
    private PasswordRecoverService service;

    @NoLogging
    @PutMapping
    public Boolean isCodeCorrect(@RequestBody PasswordRecoverVO code) {
        return service.isCodeCorrect(code);
    }

    @NoLogging
    @PostMapping("/email/{email}")
    public void generateNewCode(@PathVariable String email){
        service.generateRecoverCode(email);
    }

}
