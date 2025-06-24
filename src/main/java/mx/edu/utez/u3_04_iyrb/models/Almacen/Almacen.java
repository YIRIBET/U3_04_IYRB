package mx.edu.utez.u3_04_iyrb.models.Almacen;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import mx.edu.utez.u3_04_iyrb.models.Cede.Cede;
import mx.edu.utez.u3_04_iyrb.models.Cliente.Cliente;
import mx.edu.utez.u3_04_iyrb.models.Tamaño;

@Entity
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    private LocalDate fechaRegistro;

    @NotNull
    @Positive(message = "El precio de venta debe ser positivo")
    private BigDecimal precioVenta;

    @NotNull
    @Positive(message = "El precio de renta debe ser positivo")
    private BigDecimal precioRenta;

    @NotNull
    private Boolean comprado = false;
    @NotNull
    private Boolean rentado = false;

    private LocalDate fechaInicioRenta;
    private LocalDate fechaFinRenta;


    @NotNull
    @Enumerated(EnumType.STRING)
    private Tamaño tamaño;


    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "cede_id")
    @JsonBackReference("cede-almacen")
    private Cede cede;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonBackReference("cliente-almacen")
    private Cliente cliente;




    @PrePersist
    private void inicializarFecha() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
    }
    @PostPersist
    private void generarClave() {
        if (clave == null && cede != null) {
            this.clave = cede.getClave() + "-A" + id;
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

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getPrecioRenta() {
        return precioRenta;
    }

    public void setPrecioRenta(BigDecimal precioRenta) {
        this.precioRenta = precioRenta;
    }

    public Tamaño getTamaño() {
        return tamaño;
    }

    public void setTamaño(Tamaño tamaño) {
        this.tamaño = tamaño;
    }

    public Cede getCede() {
        return cede;
    }

    public void setCede(Cede cede) {
        this.cede = cede;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public boolean getComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public boolean getRentado() {
        return rentado;
    }

    public void setRentado(boolean rentado) {
        this.rentado = rentado;
    }

    public LocalDate getFechaInicioRenta() {
        return fechaInicioRenta;
    }
    public void setFechaInicioRenta(LocalDate fechaInicioRenta) {
        this.fechaInicioRenta = fechaInicioRenta;
    }
    public LocalDate getFechaFinRenta() {
        return fechaFinRenta;
    }
    public void setFechaFinRenta(LocalDate fechaFinRenta) {
        this.fechaFinRenta = fechaFinRenta;
    }


}