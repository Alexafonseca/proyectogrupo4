/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Alexa
 */
            public class DetalleVenta {
            private int id_DetalleVenta;
            private int id_Producto;
            private int id_Venta; 
            private int cantidad_Producto;
            private float precio;

    public DetalleVenta(int id_DetalleVenta, int id_Producto, int id_Venta, int cantidad_Producto, float precio) {
        this.id_DetalleVenta = id_DetalleVenta;
        this.id_Producto = id_Producto;
        this.id_Venta = id_Venta;
        this.cantidad_Producto = cantidad_Producto;
        this.precio = precio;
    }

            public DetalleVenta() {
            }

            public int getId_DetalleVenta() {
                return id_DetalleVenta;
            }

            public void setId_DetalleVenta(int id_DetalleVenta) {
                this.id_DetalleVenta = id_DetalleVenta;
            }

            public int getId_Producto() {
                return id_Producto;
            }

            public void setId_Producto(int id_Producto) {
                this.id_Producto = id_Producto;
            }

            public int getId_Venta() {
                return id_Venta;
            }

            public void setId_Venta(int id_Venta) {
                this.id_Venta = id_Venta;
            }

            public int getCantidad_Producto() {
                return cantidad_Producto;
            }

            public void setCantidad_Producto(int cantidad_Producto) {
                this.cantidad_Producto = cantidad_Producto;
            }

            public float getPrecio() {
                return precio;
            }

            public void setPrecio(float precio) {
                this.precio = precio;
            }

    public int getIdDetalleVenta() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   

                }
