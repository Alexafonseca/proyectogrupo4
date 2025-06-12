    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package DAO;

    import Modelo.Venta;
    import Util.ConexionBD;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.sql.ResultSet;
    import java.util.ArrayList;
    import java.util.List;


            public class VentaDAO {

   public int crearVenta(Venta venta) throws SQLException {
    String sql = """
        INSERT INTO venta (
            id_Cliente, 
            Cantidad_pro, 
            Fe_venta
        ) VALUES (?, ?, ?)""";

    int generatedId = -1;

    try (Connection c = ConexionBD.getConnection(); 
         PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setInt(1, venta.getId_Clientes());
        stmt.setInt(2, venta.getCantidad_Pro());
        stmt.setTimestamp(3, new java.sql.Timestamp(venta.getFe_Venta().getTime()));

        int filasAfectadas = stmt.executeUpdate();
        System.out.println("Filas insertadas en venta: " + filasAfectadas);

        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                generatedId = rs.getInt(1);
                System.out.println("ID de venta generado: " + generatedId);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al crear venta: " + e.getMessage());
        throw e; // Propaga el error para manejarlo externamente
    }

    return generatedId;
}

public Venta obtenerVentaPorId(int idVenta) throws SQLException {
    String sql = "SELECT * FROM Venta WHERE id_Venta = ?";
    try (Connection conn = ConexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idVenta);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Venta venta = new Venta();
            venta.setId_Ventas(rs.getInt("id_Ventas"));
            venta.setId_Clientes(rs.getInt("id_Clientes"));
            venta.setCantidad_Pro(rs.getInt("Cantidad_Pro"));
            venta.setFe_Venta(rs.getDate("Fe_Venta"));
            return venta;
        }
    }
    return null;
}

                public List<Venta> leerTodasVentas() throws SQLException {
                    String sql = "SELECT * FROM Venta";
                    List<Venta> ventas = new ArrayList<>();

                    try (Connection c = ConexionBD.getConnection(); 
                         PreparedStatement stmt = c.prepareStatement(sql); 
                         ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            Venta venta = new Venta();
                            venta.setId_Ventas(rs.getInt("id_ventas"));
                            venta.setId_Clientes(rs.getInt("id_Cliente"));
                            venta.setCantidad_Pro(rs.getInt("Cantidad_pro"));
                            venta.setFe_Venta(rs.getTimestamp("Fe_venta"));
                            ventas.add(venta);
                        }
                    }
                    return ventas;
                }

                public void actualizarVenta(Venta venta) throws SQLException {
                    String sql = "UPDATE Venta SET id_Cliente = ?, Cantidad_pro = ?, Fe_venta = ? WHERE id_ventas = ?";

                    try (Connection c = ConexionBD.getConnection();
                         PreparedStatement stmt = c.prepareStatement(sql)) {
                        stmt.setInt(1, venta.getId_Clientes());
                        stmt.setInt(2, venta.getCantidad_Pro());
                        stmt.setTimestamp(3, new java.sql.Timestamp(venta.getFe_Venta().getTime()));
                        stmt.setInt(4, venta.getId_Ventas());
                        stmt.executeUpdate();
                    }
                }

                public void eliminarVenta(int idVentas) throws SQLException {
                    String sql = "DELETE FROM Venta WHERE id_ventas = ?";

                    try (Connection c = ConexionBD.getConnection();
                         PreparedStatement stmt = c.prepareStatement(sql)) {
                        stmt.setInt(1, idVentas);
                        stmt.executeUpdate();
                    }
                }

                public static void main(String[] args) {
                    try {
                        VentaDAO dao = new VentaDAO();

                        // Actualizar una venta
                        Venta venta = new Venta();
                        venta.setId_Ventas(1); // ID existente
                        venta.setId_Clientes(1);
                        venta.setCantidad_Pro(10);
                        venta.setFe_Venta(new java.util.Date());
                        dao.actualizarVenta(venta);
                        System.out.println("Venta actualizada.");

                        // Eliminar una venta
                        dao.eliminarVenta(2); // ID a eliminar
                        System.out.println("Venta eliminada.");

                        // Leer y mostrar todas las ventas para verificar
                        List<Venta> ventas = dao.leerTodasVentas();
                        System.out.println("Lista de ventas:");
                        for (Venta ven : ventas) {
                            System.out.println("ID Venta: " + ven.getId_Ventas()
                                    + ", Cliente ID: " + ven.getId_Clientes()
                                    + ", Cantidad: " + ven.getCantidad_Pro()
                                    + ", Fecha: " + ven.getFe_Venta());
                        }
                    } catch (SQLException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            }