package mx.edu.utez.u3_04_iyrb.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.u3_04_iyrb.config.ApiResponse;
import mx.edu.utez.u3_04_iyrb.models.Cede.Cede;
import mx.edu.utez.u3_04_iyrb.models.Cede.CedeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CedeService {

    private final CedeRepository repository;

    public CedeService(CedeRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(repository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Cede> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Cede encontrada", true));
    }

    public ResponseEntity<ApiResponse> save(Cede cede) {
        if (cede.getEstado() == null || cede.getMunicipio() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Estado y municipio son obligatorios", false));
        Cede saved = repository.save(cede);
        // se genera clave después de guardar
        saved = repository.save(saved);
        return ResponseEntity.ok(new ApiResponse(saved, "Cede registrada correctamente", true));
    }

    public ResponseEntity<ApiResponse> update(Long id, Cede cede) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        cede.setId(id);
        return ResponseEntity.ok(new ApiResponse(repository.save(cede), "Cede actualizada correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cede no encontrada", false));
        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Cede eliminada correctamente", true));
    }
}