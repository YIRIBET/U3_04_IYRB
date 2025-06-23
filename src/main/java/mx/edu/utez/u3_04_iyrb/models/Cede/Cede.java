package mx.edu.utez.u3_04_iyrb.models.Cede;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.u3_04_iyrb.models.Almacen.Almacen;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Entity
@Data
public class Cede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    private String estado;

    private String municipio;

    @OneToMany(mappedBy = "cede")
    @JsonManagedReference("cede-almacen")
    private List<Almacen> almacenes;


    @PostPersist
    public void generarClave() {
        if (this.clave == null) {
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            int random = new Random().nextInt(9000) + 1000;
            this.clave = "C" + this.id + "-" + fecha + "-" + random;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public List<Almacen> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<Almacen> almacenes) {
        this.almacenes = almacenes;
    }
}