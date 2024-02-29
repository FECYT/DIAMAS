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

import es.soltel.recolecta.service.InstitucionService;
import es.soltel.recolecta.vo.InstitucionVO;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/instituciones")
public class InstitucionController {

    @Autowired
    private InstitucionService service;

    @GetMapping("/getAll")
    public List<InstitucionVO> getAll() {
        return service.getAll();
    }

    @GetMapping("/getById/{id}")
    public InstitucionVO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/findByUserId/{id}")
    public InstitucionVO findByUserId(@PathVariable Long id) {
        return service.findByUserId(id);
    }

    @PostMapping("/create")
    public InstitucionVO create(@RequestBody InstitucionVO vo) {
        return service.create(vo);
    }

    @PutMapping("/update")
    public InstitucionVO update(@RequestBody InstitucionVO vo) {
        return service.update(vo);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
