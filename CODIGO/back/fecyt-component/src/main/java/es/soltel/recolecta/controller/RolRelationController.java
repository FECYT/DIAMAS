package es.soltel.recolecta.controller;

import es.soltel.recolecta.entity.RolRelationEntity;
import es.soltel.recolecta.service.RolRelationService;
import es.soltel.recolecta.vo.RolRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/rol_relation")
public class RolRelationController {

    @Autowired
    private RolRelationService service;

    @PostMapping("/setRol/userId/{userId}")
    public void setUserRoles(@PathVariable Long userId, @RequestBody List<Long> roles) {
        service.setUserRoles(userId,roles);
    }

    @PostMapping("/getRol/userId/{userId}")
    public List<RolRelationVO> getUserRoles(@PathVariable Long userId) {
        return service.getUserRoles(userId);
    }

}
