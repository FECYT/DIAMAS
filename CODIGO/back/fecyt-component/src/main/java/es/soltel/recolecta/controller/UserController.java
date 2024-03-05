package es.soltel.recolecta.controller;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.vo.ChangePasswordBean;
import es.soltel.recolecta.vo.UserPlusRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.UserService;
import es.soltel.recolecta.vo.UserVO;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserVO> findAll() {
        List<UserVO> users = service.findAll();
        users.forEach(UserVO::removePassword);
        return users;
    }

    @PutMapping("/toggle/{userId}/{status}")
    public void toggleUserActivity(@PathVariable Long userId, @PathVariable Boolean status){
        service.toggleUser(userId,status);
    }

    @GetMapping("/{id}")
    public UserVO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @NoLogging
    @PutMapping("/changePassword")
    public void changeUserPassword(@RequestBody ChangePasswordBean changePasswordBean){
        service.changePassword(changePasswordBean);
    }

    @GetMapping("/email/{email}")
    public UserVO findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/doesEmailExist/{email}")
    public Boolean doesUserWithEmailExist(@PathVariable String email) {
        return service.findByEmail(email) != null;
    }

    @PostMapping
    public UserVO create(@RequestBody UserVO user) {
        return service.create(user);
    }

    @PutMapping
     public UserVO update(@RequestBody UserVO user) {
         return service.update(user);
     }

     @PutMapping("/updateWithRoles")
    public void updateWithRoles(@RequestBody UserPlusRoles userPlusRoles){
        service.updatePlusRoles(userPlusRoles);
     }

}
