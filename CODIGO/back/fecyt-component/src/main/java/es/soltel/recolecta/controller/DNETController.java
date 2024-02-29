package es.soltel.recolecta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.soltel.recolecta.response.RepositoryResponse;
import es.soltel.recolecta.response.UserResponse;
import es.soltel.recolecta.service.DnetService;
import es.soltel.recolecta.vo.UserVO;


@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/dnet")
public class DNETController {

    @Autowired
    private DnetService service;

    @GetMapping
    public List<UserResponse> getAll() throws Exception {
        return service.getUsersDNET();
    }

    @GetMapping("/repositoryByEmail")
    public List<RepositoryResponse> getRepositoryByEmail(@RequestParam String email) throws Exception {
        return service.getRepositoriesDNETByEmail(email);
    }

    @GetMapping("/repositoryByIdRepository")
    public List<RepositoryResponse> getRepositoryByIdRepository(@RequestParam String idRepository) throws Exception {
        return service.getRepositoriesDNETByIdRepository(idRepository);
    }

    @GetMapping("/userByEmail")
    public UserResponse getRepositoriesDNETByEmail(String email) throws Exception {
        return service.getUserDNETByEmail(email);
    }

    @GetMapping("/evaluators")
    public List<UserVO> getEvaluators() throws Exception {
        return service.getEvaluators();
    }

}
