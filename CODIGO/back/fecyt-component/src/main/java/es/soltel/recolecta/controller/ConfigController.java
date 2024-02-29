package es.soltel.recolecta.controller;

import es.soltel.recolecta.service.ConfigService;
import es.soltel.recolecta.service.UserRepositoryService;
import es.soltel.recolecta.vo.ConfigVO;
import es.soltel.recolecta.vo.UserRepositoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService service;

    public ConfigVO findById(@PathVariable Long id) {
        return service.getConfig();
    }

    @GetMapping("/getAutomaticRegister")
    public Boolean getAutomaticRegister(){
        return service.getAutomaticRegister();
    }

    @GetMapping("/setAutomaticRegister/{status}")
    public void setAutomaticRegister(@PathVariable Boolean status){
        service.setAutomaticRegister(status);
    }

}
