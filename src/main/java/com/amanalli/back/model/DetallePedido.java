package com.amanalli.back.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
    //Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;
    @Column(name = "cantidad", nullable = false, columnDefinition = "INT")
    private Long cantidad;
    @Column(name = "importe", nullable = false, columnDefinition = "DECIMAL(8,2)")
    private Double importe;
    /*
    // Cardinalidad de DetallePedido -> Productos N:1
    @ManyToOne
    @JoinColumn(name = "id_detalle_producto")//Especifica una columna para hacer JOIN de SQL
    private Productos productos;

    // Cardinalidad de DetallePedido -> VentaPedidos N:1
    @ManyToOne
    @JoinColumn(name = "id_detalle_venta")//Especifica una columna para hacer JOIN de SQL
    private VentaPedidos ventaPedidos;
    */
    //Constructores
    public DetallePedido(Long idDetalle, Long cantidad, Double importe) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.importe = importe;
    }
    public DetallePedido(){

    }

    //Getters y Setters
    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    //ToString
    @Override
    public String toString() {
        return "DetallePedido{" +
                "idDetalle=" + idDetalle +
                ", cantidad=" + cantidad +
                ", importe=" + importe +
                '}';
    }

    //Equals y Hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetallePedido that)) return false;
        return Objects.equals(idDetalle, that.idDetalle) && Objects.equals(cantidad, that.cantidad) && Objects.equals(importe, that.importe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDetalle, cantidad, importe);
    }
}
