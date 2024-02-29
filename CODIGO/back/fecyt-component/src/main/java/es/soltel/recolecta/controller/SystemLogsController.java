package es.soltel.recolecta.controller;

import org.springframework.web.bind.annotation.*;

import es.soltel.recolecta.service.SystemLogsService;
import es.soltel.recolecta.vo.SystemLogsVO;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/systemlogs")
public class SystemLogsController {

    private final SystemLogsService systemLogsService;

    public SystemLogsController(SystemLogsService systemLogsService) {
        this.systemLogsService = systemLogsService;
    }

    @PostMapping
    public SystemLogsVO create(@RequestBody SystemLogsVO systemLogsVO) {
        return systemLogsService.create(systemLogsVO);
    }

    @GetMapping("/{id}")
    public SystemLogsVO getById(@PathVariable Long id) {
        return systemLogsService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        systemLogsService.delete(id);
    }

    @GetMapping
    public List<SystemLogsVO> getAll() {
        return systemLogsService.getAll();
    }
}
