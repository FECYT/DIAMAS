package es.soltel.recolecta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.soltel.recolecta.service.RepositoryService;
import es.soltel.recolecta.vo.RepositoryVO;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/repositories")
public class RepositoryController {

    @Autowired
    private RepositoryService service;

    @GetMapping("/getAll")
    public List<RepositoryVO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public RepositoryVO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public RepositoryVO create(@RequestBody RepositoryVO vo) {
        return service.create(vo);
    }

    @PutMapping("/update")
    public RepositoryVO update(@RequestBody RepositoryVO vo) {
        return service.update(vo);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("userId/{id}")
    public RepositoryVO findByUserId(@PathVariable Long id) {
        return service.findByUserId(id);
    }

    @GetMapping("repository/userId/{id}")
    public RepositoryVO findRepositoryByUserId(@PathVariable Long id) {
        return service.findByUserId(id);
    }

}
