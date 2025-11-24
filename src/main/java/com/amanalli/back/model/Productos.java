package com.amanalli.back.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "productos")
public class Productos {
    //Variables de instancia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;
    @Column(name = "nombre_producto", nullable = false, unique = true, length = 100, columnDefinition = "VARCHAR(100)")
    private String nombreProducto;
    @Column (name = "descripcion_producto", nullable = false, columnDefinition = "TEXT")
    private String descripcionProducto;
    @Column(name = "precio", nullable = false, columnDefinition = "DECIMAL(8,2)")
    private Double precio;
    @Column(name = "imagen", nullable = false, length = 300, columnDefinition = "VARCHAR(300)")
    private String imagen;
    @Column(name = "stock", nullable = false, columnDefinition = "INT")
    private Long stock;
    @Column (name = "estatus_producto", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean estatusProducto = true;


    // Cardinalidad de Productos -> Categorias N:1
    @ManyToOne
    @JoinColumn(name = "id_categoria")//Especifica una columna para hacer JOIN de SQL
    private Categorias categorias;

    // Cardinalidad de Productos -> Regiones N:1
    @ManyToOne
    @JoinColumn(name = "id_region")//Especifica una columna para hacer JOIN de SQL
    private Regiones regiones;

    // Cardinalidad Productos -> DetallePedido 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productos")
    private List<DetallePedido> detallePedidos = new ArrayList<>();

    //Contructores
    public Productos(Long idProducto, String nombreProducto, String descripcionProducto, Double precio, String imagen, Long stock, Boolean estatusProducto, Categorias categorias, Regiones regiones) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
        this.estatusProducto = estatusProducto;
        this.categorias = categorias;
        this.regiones = regiones;
    }

    public Productos(){

    }

    //Getters y Setters
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Boolean getEstatusProducto() {
        return estatusProducto;
    }

    public void setEstatusProducto(Boolean estatusProducto) {
        this.estatusProducto = estatusProducto;
    }

    public Categorias getCategorias() {
        return categorias;
    }

    public void setCategorias(Categorias categorias) {
        this.categorias = categorias;
    }

    public Regiones getRegiones() {
        return regiones;
    }

    public void setRegiones(Regiones regiones) {
        this.regiones = regiones;
    }

    public List<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(List<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    //ToString
    @Override
    public String toString() {
        return "Productos{" +
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", descripcionProducto='" + descripcionProducto + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                ", stock=" + stock +
                ", estatusProducto=" + estatusProducto +
                '}';
    }

    //Equals y Hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Productos productos)) return false;
        return Objects.equals(idProducto, productos.idProducto) && Objects.equals(nombreProducto, productos.nombreProducto) && Objects.equals(descripcionProducto, productos.descripcionProducto) && Objects.equals(precio, productos.precio) && Objects.equals(imagen, productos.imagen) && Objects.equals(stock, productos.stock) && Objects.equals(estatusProducto, productos.estatusProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, nombreProducto, descripcionProducto, precio, imagen, stock, estatusProducto);
    }

    // === Activar un producto Estatus = True (PUT) ===
    public Productos activar() {
        this.estatusProducto = true;
        return this;
    }

    // Eliminar/desactivar un producto Estatus = False (DELETE)
    public void desactivarById() {
        this.estatusProducto = false;
    }
}
