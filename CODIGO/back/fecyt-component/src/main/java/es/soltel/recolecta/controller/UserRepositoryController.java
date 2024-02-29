package es.soltel.recolecta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.UserRepositoryService;
import es.soltel.recolecta.vo.UserRepositoryVO;
import es.soltel.recolecta.vo.UserVO;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/user-repository")
public class UserRepositoryController {

    @Autowired
    private UserRepositoryService service;

    @GetMapping
    public List<UserRepositoryVO> findAll() {
        List<UserRepositoryVO> users = service.findAll();
        users.forEach(UserRepositoryVO::removePassword);
        return users;
    }

    @GetMapping("/{id}")
    public UserRepositoryVO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UserRepositoryVO create(@RequestBody UserRepositoryVO vo) {
        return service.create(vo);
    }

    @PutMapping
    public UserRepositoryVO update(@RequestBody UserRepositoryVO vo) {
        return service.update(vo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/userId/{id}")
    public UserRepositoryVO findByUserId(@PathVariable Long id) {
        return service.findByUserId(id);
    }

    @GetMapping("/repository/{id}")
    public UserRepositoryVO findByRepositoryId(@PathVariable Long id) {
        return service.findByRepositoryId(id);
    }

    @GetMapping("/doesUserOwnRepository/{idUser}/{idRepository}")
    public Boolean doesUserOwnRepository(@PathVariable Long idUser, @PathVariable Long idRepository) {
        return service.doesUserOwnRepository(idUser, idRepository);
    }
}
