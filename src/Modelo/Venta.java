/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.Date;

/**
 *
 * @author Alexa
 */
            public class Venta {
            private int id_Ventas;
            private int id_Clientes;
            private int Cantidad_Pro;
            private Date fe_Venta;

    public Venta(int id_Ventas, int id_Clientes, int id_Producto, int Cantidad_Pro, Date fe_Venta) {
        this.id_Ventas = id_Ventas;
        this.id_Clientes = id_Clientes;
        this.Cantidad_Pro = Cantidad_Pro;
        this.fe_Venta = fe_Venta;
    }

            public Venta() {
            }

    public int getId_Ventas() {
        return id_Ventas;
    }

    public void setId_Ventas(int id_Ventas) {
        this.id_Ventas = id_Ventas;
    }

    public int getId_Clientes() {
        return id_Clientes;
    }

    public void setId_Clientes(int id_Clientes) {
        this.id_Clientes = id_Clientes;
    }

    public int getCantidad_Pro() {
        return Cantidad_Pro;
    }

    public void setCantidad_Pro(int Cantidad_Pro) {
        this.Cantidad_Pro = Cantidad_Pro;
    }

    public Date getFe_Venta() {
        return fe_Venta;
    }

    public void setFe_Venta(Date fe_Venta) {
        this.fe_Venta = fe_Venta;
    }

           


                }
