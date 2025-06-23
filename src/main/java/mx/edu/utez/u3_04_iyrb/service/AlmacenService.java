package mx.edu.utez.u3_04_iyrb.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.u3_04_iyrb.config.ApiResponse;
import mx.edu.utez.u3_04_iyrb.models.Almacen.Almacen;
import mx.edu.utez.u3_04_iyrb.models.Almacen.AlmacenRepository;
import mx.edu.utez.u3_04_iyrb.models.Cede.CedeRepository;
import mx.edu.utez.u3_04_iyrb.models.Cliente.Cliente;
import mx.edu.utez.u3_04_iyrb.models.Cliente.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service

public class AlmacenService {

    private final AlmacenRepository almacenRepository;
    private final CedeRepository cedeRepository;
    private final ClienteRepository clienteRepository;
    public AlmacenService(AlmacenRepository almacenRepository,
                          CedeRepository cedeRepository,
                          ClienteRepository clienteRepository) {
        this.almacenRepository = almacenRepository;
        this.cedeRepository = cedeRepository;
        this.clienteRepository = clienteRepository;
    }


    public ResponseEntity<ApiResponse> findAll() {
        return ResponseEntity.ok(new ApiResponse(almacenRepository.findAll(), "Consulta exitosa", true));
    }

    public ResponseEntity<ApiResponse> findById(Long id) {
        Optional<Almacen> found = almacenRepository.findById(id);
        if (found.isEmpty())
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        return ResponseEntity.ok(new ApiResponse(found.get(), "Almacén encontrado", true));
    }

    public ResponseEntity<ApiResponse> save(Almacen almacen) {
        if (almacen.getPrecioVenta() == null || almacen.getTamaño() == null)
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Datos incompletos del almacén", false));

        if (almacen.getCede() == null || !cedeRepository.existsById(almacen.getCede().getId()))
            return ResponseEntity.badRequest().body(new ApiResponse(null, "Cede inválida", false));
        var cede = cedeRepository.findById(almacen.getCede().getId()).get();
        almacen.setCede(cede);
        Almacen saved = almacenRepository.save(almacen);
        saved.setClave(cede.getClave() + "-A" + saved.getId());
        saved = almacenRepository.save(saved);

        return ResponseEntity.ok(new ApiResponse(saved, "Almacén registrado correctamente", true));
    }


    public ResponseEntity<ApiResponse> update(Long id, Almacen almacen) {
        if (!almacenRepository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        almacen.setId(id);
        return ResponseEntity.ok(new ApiResponse(almacenRepository.save(almacen), "Almacén actualizado correctamente", true));
    }

    public ResponseEntity<ApiResponse> delete(Long id) {
        if (!almacenRepository.existsById(id))
            return ResponseEntity.status(404).body(new ApiResponse(null, "Almacén no encontrado", false));
        almacenRepository.deleteById(id);
        return ResponseEntity.ok(new ApiResponse(null, "Almacén eliminado correctamente", true));
    }


    public String comprarAlmacen(Long almacenId, Long clienteId) {
        Optional<Almacen> opt = almacenRepository.findById(almacenId);
        if (opt.isEmpty()) return "Almacén no encontrado.";

        Almacen almacen = opt.get();
        if (Boolean.TRUE.equals(almacen.getComprado()) || Boolean.TRUE.equals(almacen.getRentado()))
            return "Ya está ocupado.";

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        almacen.setCliente(cliente);
        almacen.setComprado(true);
        almacenRepository.save(almacen);
        return "Almacén comprado con éxito.";
    }

    public String rentarAlmacen(Long almacenId, Long clienteId, LocalDate inicio, LocalDate fin) {
        Optional<Almacen> opt = almacenRepository.findById(almacenId);
        if (opt.isEmpty()) return "Almacén no encontrado.";

        Almacen almacen = opt.get();
        if (almacen.getComprado() || almacen.getRentado()) return "Ya está ocupado.";

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        almacen.setCliente(cliente);
        almacen.setRentado(true);
        almacen.setFechaInicioRenta(inicio);
        almacen.setFechaFinRenta(fin);
        almacenRepository.save(almacen);

        return "Almacén rentado con éxito.";
    }

}
