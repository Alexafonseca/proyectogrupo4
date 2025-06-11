/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.DAOProducto;
import Modelo.Producto;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Controlador para gestionar productos.
 *
 * @author Azter Baez
 */
public class ProductoControlador {

    private final DAOProducto DAOproducto;

    public ProductoControlador() {
        this.DAOproducto = new DAOProducto();
    }

    public static Date convertirFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            return formatoFecha.parse(fecha);
        } catch (ParseException e) {
            System.err.println("❌ Error al convertir la fecha: " + fecha);
            return null;
        }
    }
// Método para crear un nuevo producto

    public void crearProducto(String Nombre_prod, String Tipo_Prod, double Existencia_Prod,
            double Precio_Costo, double precio_venta, Date Fe_caducidad) {
        try {
            Producto producto = new Producto();
            producto.setNombre_prod(Nombre_prod);
            producto.setTipo_Prod(Tipo_Prod);
            producto.setExistencia_Prod(Existencia_Prod);
            producto.setPrecio_Costo(Precio_Costo);
            producto.setPrecio_Venta(precio_venta);
            producto.setFe_caducidad(Fe_caducidad);
            DAOproducto.crearProducto(producto);
            JOptionPane.showMessageDialog(null, "Producto creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método para obtener todos los productos

public List<Producto> obtenerTodosProductos() {
    try {
        List<Producto> productos = DAOproducto.leerTodosProductos();
        System.out.println("Productos obtenidos: " + (productos != null ? productos.size() : 0)); // Depuración
        return productos != null ? productos : new ArrayList<>(); // Devuelve lista vacía si es null
    } catch (SQLException e) {
        System.out.println("Error al leer los productos: " + e.getMessage()); // Depuración en consola
        JOptionPane.showMessageDialog(null, "Error al leer los productos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        return new ArrayList<>(); // Devuelve lista vacía en caso de error
    }
}

public Producto obtenerProductoPorId(int idProducto) {
    try {
        return new DAOProducto().obtenerProductoPorId(idProducto);
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}


    // Método para actualizar un producto existente
    public void actualizarProducto(int id_producto, String Nombre_prod, String Tipo_Prod, double Existencia_Prod,
            double Precio_Costo, double precio_venta, Date fecha_caducidad) {
        try {
            Producto producto = new Producto();
            producto.setId_producto(id_producto);
            producto.setNombre_prod(Nombre_prod);
            producto.setTipo_Prod(Tipo_Prod);
            producto.setExistencia_Prod(Existencia_Prod);
            producto.setPrecio_Costo(Precio_Costo);
            producto.setPrecio_Venta(precio_venta);
            producto.setFe_caducidad(fecha_caducidad);
            DAOproducto.actualizarProducto(producto);
            JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un producto
    public void eliminarProducto(int idProducto) {
        try {
            DAOproducto.eliminarProducto(idProducto);
            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        ProductoControlador controlador = new ProductoControlador();

        try {
            Date fechaCaducidad = convertirFecha("01-10-2025");
            Producto nuevoProducto = new Producto(1, "Collar Azul", "Accesorio", 30, 61, 65, fechaCaducidad);
            controlador.DAOproducto.crearProducto(nuevoProducto);

            List<Producto> producto = controlador.DAOproducto.leerTodosProductos();
            producto.forEach(p -> System.out.println("Producto: " + p.getNombre_prod()));

            controlador.DAOproducto.eliminarProducto(1);
        } catch (SQLException e) {
            System.err.println("❌ Error en ejecución: " + e.getMessage());
        }
        // Leer todos los productos
        List<Producto> producto = controlador.obtenerTodosProductos();
        if (producto != null) {
            System.out.println("Lista de productos:");
            for (Producto p : producto) {
                System.out.println("id_producto: " + p.getId_producto()
                        + ", Nombre_Prod: " + p.getNombre_prod()
                        + ", Tipo_Prod: " + p.getTipo_Prod()
                        + ", Existencia_Prod: " + p.getExistencia_Prod()
                        + ", Precio_Costo: " + p.getPrecio_Costo()
                        + ", Precio_Venta: " + p.getPrecio_Venta()
                        + ", Fecha_Caducidad: " + p.getFe_caducidad());
            }
        }

    }
}
