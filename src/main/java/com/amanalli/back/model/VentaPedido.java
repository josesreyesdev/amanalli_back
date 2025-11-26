package com.amanalli.back.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "venta_pedidos")
public class VentaPedido {
    //Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;
    @Column(name = "fecha_pedido", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime fechaPedido;
    @Column(name = "total_venta", nullable = false, columnDefinition = "DECIMAL(8,2)")
    private Double totalVenta;
    @Column (name = "estatus_pedido", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean estatusPedido;

    // Cardinalidad Productos -> DetallePedido 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ventaPedido")
    private List<DetallePedido> detallePedidos = new ArrayList<>();

    // Cardinalidad de VentaPedidos -> Usuarios N:1
    @ManyToOne
    @JoinColumn(name = "id_usuario")//Especifica una columna para hacer JOIN de SQL
    private Usuario usuario;

    //Constructores
    public VentaPedido(Long idVenta, LocalDateTime fechaPedido, Double totalVenta, Boolean estatusPedido) {
        this.idVenta = idVenta;
        this.fechaPedido = fechaPedido;
        this.totalVenta = totalVenta;
        this.estatusPedido = estatusPedido;
    }
    public VentaPedido(){

    }

    //Getters y Setters
    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Boolean getEstatusPedido() {
        return estatusPedido;
    }

    public void setEstatusPedido(Boolean estatusPedido) {
        this.estatusPedido = estatusPedido;
    }

    //ToString
    @Override
    public String toString() {
        return "VentaPedidos{" +
                "idVenta=" + idVenta +
                ", fechaPedido=" + fechaPedido +
                ", totalVenta=" + totalVenta +
                ", estatusPedido=" + estatusPedido +
                '}';
    }

    //Equals y Hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VentaPedido that)) return false;
        return Objects.equals(idVenta, that.idVenta) && Objects.equals(fechaPedido, that.fechaPedido) && Objects.equals(totalVenta, that.totalVenta) && Objects.equals(estatusPedido, that.estatusPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenta, fechaPedido, totalVenta, estatusPedido);
    }
}
