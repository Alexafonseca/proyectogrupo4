/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.DetalleVentaDAO;
import Modelo.DetalleVenta;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author eliab
 */
        public class DetalleVentaControlador {

            private final DetalleVentaDAO detalleVentaDAO;

            public DetalleVentaControlador() {
                this.detalleVentaDAO = new DetalleVentaDAO();
            }

            // Método para crear un nuevo detalle de venta
            public void crearDetalleVenta(int idDetalleVenta, int idProducto, int cantidadProducto, float precio) {
                try {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setId_DetalleVenta(idDetalleVenta);
                    detalle.setId_Producto(idProducto);
                    detalle.setCantidad_Producto(cantidadProducto);
                    detalle.setPrecio(precio);
                    detalleVentaDAO.crearDetalleVenta(detalle);
                    JOptionPane.showMessageDialog(null, "Detalle de venta creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al crear el detalle de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Método para obtener todos los detalles de venta
            public List<DetalleVenta> obtenerTodosDetalleVenta() {
                try {
                    return detalleVentaDAO.leerTodosDetallesVenta();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al leer los detalles de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }

            // Método para actualizar un detalle de venta existente
            public void actualizarDetalleVenta(int idDetalleVenta, int idProducto, int idVenta, int cantidadProducto, float precio) {
                try {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setId_DetalleVenta(idDetalleVenta);
                    detalle.setId_Producto(idProducto);
                    detalle.setId_Venta(idVenta);
                    detalle.setCantidad_Producto(cantidadProducto);
                    detalle.setPrecio(precio);
                    detalleVentaDAO.actualizarDetalleVenta(detalle);
                    JOptionPane.showMessageDialog(null, "Detalle de venta actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el detalle de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Método para eliminar un detalle de venta
            public void eliminarDetalleVenta(int idDetalleVenta) {
                try {
                    detalleVentaDAO.eliminarDetalleVenta(idDetalleVenta);
                    JOptionPane.showMessageDialog(null, "Detalle de venta eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el detalle de venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Método main para pruebas
            public static void main(String[] args) {
                DetalleVentaControlador controlador = new DetalleVentaControlador();

                // Crear un detalle de venta
                controlador.crearDetalleVenta(2, 5, 20, 25.99f);

                // Leer todos los detalles de venta
                List<DetalleVenta> detalles = controlador.obtenerTodosDetalleVenta();
                if (detalles != null) {
                    System.out.println("Lista de detalles de venta:");
                    for (DetalleVenta d : detalles) {
                        System.out.println("ID: " + d.getId_DetalleVenta()
                                + ", Venta: " + d.getId_Producto()
                                + ", Producto: " + d.getId_Venta()
                                + ", Cantidad: " + d.getCantidad_Producto());
                    }
                }

                // Actualizar un detalle de venta (suponiendo que ID 1 existe)
                controlador.actualizarDetalleVenta(1, 2, 5, 30, 27.50f);

                // Eliminar un detalle de venta
                controlador.eliminarDetalleVenta(1);
            }
        }