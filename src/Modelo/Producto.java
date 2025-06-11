/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Date;

/**
 * Clase que representa un producto.
 *
 * @author XX3
 */
public class Producto {

    private int id_producto;
    private String nombre_prod;
    private String Tipo_Prod;
    private double Existencia_Prod;
    private double Precio_Costo;
    private double precio_Venta;
    private Date Fe_caducidad;

    public Producto() {
    }

    public Producto(int id_producto, String nombre_prod, String Tipo_Prod, double Existencia_Prod, double Precio_Costo, double precio_Venta, Date Fe_caducidad) {
        this.id_producto = id_producto;
        this.nombre_prod = nombre_prod;
        this.Tipo_Prod = Tipo_Prod;
        this.Existencia_Prod = Existencia_Prod;
        this.Precio_Costo = Precio_Costo;
        this.precio_Venta = precio_Venta;
        this.Fe_caducidad = Fe_caducidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_prod() {
        return nombre_prod;
    }

    public void setNombre_prod(String nombre_prod) {
        this.nombre_prod = nombre_prod;
    }

    public String getTipo_Prod() {
        return Tipo_Prod;
    }

    public void setTipo_Prod(String Tipo_Prod) {
        this.Tipo_Prod = Tipo_Prod;
    }

    public double getExistencia_Prod() {
        return Existencia_Prod;
    }

    public void setExistencia_Prod(double Existencia_Prod) {
        this.Existencia_Prod = Existencia_Prod;
    }

    public double getPrecio_Costo() {
        return Precio_Costo;
    }

    public void setPrecio_Costo(double Precio_Costo) {
        this.Precio_Costo = Precio_Costo;
    }

    public double getPrecio_Venta() {
        return precio_Venta;
    }

    public void setPrecio_Venta(double precio_Venta) {
        this.precio_Venta = precio_Venta;
    }

    public Date getFe_caducidad() {
        return Fe_caducidad;
    }

    public void setFe_caducidad(Date Fe_caducidad) {
        this.Fe_caducidad = Fe_caducidad;
    }

}
