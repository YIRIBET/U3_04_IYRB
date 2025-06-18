package mx.edu.utez.u3_04_iyrb.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.u3_04_iyrb.config.ApiResponse;
import mx.edu.utez.u3_04_iyrb.models.Cliente.Cliente;
import mx.edu.utez.u3_04_iyrb.models.Cliente.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(repository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Cliente> found = repository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cliente no encontrado", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Cliente encontrado", true));
    }

    public ResponseEntity<ApiResponse> save(Cliente cliente) {
        if (cliente.getNombreCompleto() == null || cliente.getCorreo() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Nombre y correo son obligatorios", false));
        return ResponseEntity.ok(new ApiResponse(repository.save(cliente), "Cliente registrado correctamente", true));
    }

    public ResponseEntity<ApiResponse> update(Long id, Cliente cliente) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cliente no encontrado", false));
        cliente.setId(id);
        return ResponseEntity.ok(new ApiResponse(repository.save(cliente), "Cliente actualizado correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!repository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Cliente no encontrado", false));
        repository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Cliente eliminado correctamente", true));
    }
}