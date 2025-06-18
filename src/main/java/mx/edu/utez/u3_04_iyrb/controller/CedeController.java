package mx.edu.utez.u3_04_iyrb.controller;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.u3_04_iyrb.config.ApiResponse;
import mx.edu.utez.u3_04_iyrb.models.Cede.Cede;
import mx.edu.utez.u3_04_iyrb.service.CedeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cedes")
public class CedeController {

    private final CedeService service;

    public CedeController(CedeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody Cede cede) {
        return service.save(cede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody Cede cede) {
        return service.update(id, cede);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}