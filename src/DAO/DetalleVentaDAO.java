/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.DetalleVenta;
import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

        public class DetalleVentaDAO {

            public void crearDetalleVenta(DetalleVenta detalle) throws SQLException {
                String sql = """
                        INSERT INTO Detalles_Ventas (
                            id_venta, 
                            id_producto, 
                            cantidad_producto, 
                            precio
                        ) VALUES (?, ?, ?, ?)""";

                try (Connection c = ConexionBD.getConnection(); 
                     PreparedStatement stmt = c.prepareStatement(sql)) {
                    stmt.setInt(1, detalle.getId_Venta());
                    stmt.setInt(2, detalle.getId_Producto());
                    stmt.setInt(3, detalle.getCantidad_Producto());
                    stmt.setFloat(4, detalle.getPrecio());
                    stmt.executeUpdate();
                }
            }

            public List<DetalleVenta> leerTodosDetallesVenta() throws SQLException {
                String sql = "SELECT * FROM Detalles_Ventas";
                List<DetalleVenta> detalles = new ArrayList<>();

                try (Connection c = ConexionBD.getConnection(); 
                     PreparedStatement stmt = c.prepareStatement(sql); 
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        DetalleVenta detalle = new DetalleVenta();
                        detalle.setId_DetalleVenta(rs.getInt("id_detalle_venta"));
                        detalle.setId_Venta(rs.getInt("id_venta"));
                        detalle.setId_Producto(rs.getInt("id_producto"));
                        detalle.setCantidad_Producto(rs.getInt("cantidad_producto"));
                        detalle.setPrecio(rs.getFloat("precio"));
                        detalles.add(detalle);
                    }
                }
                return detalles;
            }

            public void actualizarDetalleVenta(DetalleVenta detalle) throws SQLException {
                String sql = "UPDATE Detalles_Ventas SET id_venta = ?, id_producto = ?, cantidad_producto = ?, precio = ? WHERE id_detalle_venta = ?";

                try (Connection c = ConexionBD.getConnection();
                     PreparedStatement stmt = c.prepareStatement(sql)) {
                    stmt.setInt(1, detalle.getId_Venta());
                    stmt.setInt(2, detalle.getId_Producto());
                    stmt.setInt(3, detalle.getCantidad_Producto());
                    stmt.setFloat(4, detalle.getPrecio());
                    stmt.setInt(5, detalle.getId_DetalleVenta());
                    stmt.executeUpdate();
                }
            }

            public void eliminarDetalleVenta(int idDetalleVenta) throws SQLException {
                String sql = "DELETE FROM Detalles_Ventas WHERE id_detalle_venta = ?";

                try (Connection c = ConexionBD.getConnection();
                     PreparedStatement stmt = c.prepareStatement(sql)) {
                    stmt.setInt(1, idDetalleVenta);
                    stmt.executeUpdate();
                }
            }

            public static void main(String[] args) {
                try {
                    DetalleVentaDAO dao = new DetalleVentaDAO();

                    // Actualizar un detalle de venta
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setId_DetalleVenta(1); // ID existente
                    detalle.setId_Venta(1);
                    detalle.setId_Producto(3);
                    detalle.setCantidad_Producto(2);
                    detalle.setPrecio(200.0f);
                    dao.actualizarDetalleVenta(detalle);
                    System.out.println("Detalle de venta actualizado.");

                    // Eliminar un detalle de venta
                    dao.eliminarDetalleVenta(2); // ID a eliminar
                    System.out.println("Detalle de venta eliminado.");

                    // Leer y mostrar todos los detalles de venta para verificar
                    List<DetalleVenta> detalles = dao.leerTodosDetallesVenta();
                    System.out.println("Lista de detalles de venta:");
                    for (DetalleVenta det : detalles) {
                        System.out.println("ID: " + det.getId_DetalleVenta()
                                + ", Venta ID: " + det.getId_Venta()
                                + ", Producto ID: " + det.getId_Producto()
                                + ", Cantidad: " + det.getCantidad_Producto()
                                + ", Precio: " + det.getPrecio());
                    }
                } catch (SQLException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }