package mx.edu.utez.u3_04_iyrb.controller;

import mx.edu.utez.u3_04_iyrb.models.Almacen.Almacen;
import mx.edu.utez.u3_04_iyrb.models.AlmacenOperacion;
import mx.edu.utez.u3_04_iyrb.service.AlmacenService;
import mx.edu.utez.u3_04_iyrb.config.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    private final AlmacenService service;

    public AlmacenController(AlmacenService service) {
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
    public ResponseEntity<ApiResponse> save(@RequestBody Almacen almacen) {
        return service.save(almacen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody Almacen almacen) {
        return service.update(id, almacen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/comprar")
    public ResponseEntity<ApiResponse> comprar(@RequestBody AlmacenOperacion dto) {
        String result = service.comprarAlmacen(dto.getAlmacenId(), dto.getClienteId());
        return ResponseEntity.ok(new ApiResponse(null, result, !result.contains("ocupado")));
    }

    @PostMapping("/rentar")
    public ResponseEntity<ApiResponse> rentar(@RequestBody AlmacenOperacion dto) {
        String result = service.rentarAlmacen(dto.getAlmacenId(), dto.getClienteId(), dto.getInicio(), dto.getFin());
        return ResponseEntity.ok(new ApiResponse(null, result, !result.contains("ocupado")));

    }

}
