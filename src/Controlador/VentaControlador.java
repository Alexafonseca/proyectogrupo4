package Controlador;

import DAO.VentaDAO;
import DAO.DetalleVentaDAO;
import Modelo.Venta;
import Modelo.DetalleVenta;
import Util.ConexionBD;
import com.mysql.cj.protocol.Resultset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VentaControlador {

    private final VentaDAO ventaDAO;
    private final DetalleVentaDAO detalleVentaDAO;

    public VentaControlador() {
        this.ventaDAO = new VentaDAO();
        this.detalleVentaDAO = new DetalleVentaDAO();
    }

    // Método para crear una nueva venta con sus detalles
    public void crearVenta(int idClientes, Date feVenta, List<DetalleVenta> detalles) {
        try {
            // Validar que haya detalles
            if (detalles == null || detalles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede registrar una venta sin productos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int totalCantidad = detalles.stream()
                                        .mapToInt(DetalleVenta::getCantidad_Producto)
                                        .sum();

            Venta venta = new Venta();
            venta.setId_Clientes(idClientes);
            venta.setFe_Venta(feVenta);
            venta.setCantidad_Pro(totalCantidad);

            int idVentas = ventaDAO.crearVenta(venta);

            if (idVentas == -1) {
                throw new SQLException("No se pudo obtener el ID de la venta.");
            }

            for (DetalleVenta detalle : detalles) {
                detalle.setId_Venta(idVentas);

                if (detalle.getId_Producto() <= 0) {
                    throw new SQLException("ID del producto no válido en el detalle.");
                }

                detalleVentaDAO.crearDetalleVenta(detalle);
            }

            JOptionPane.showMessageDialog(null, "Venta y detalles creados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener todas las ventas
    public List<Venta> obtenerTodasVentas() {
        try {
            return ventaDAO.leerTodasVentas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    // Método para actualizar una venta existente
    public void actualizarVenta(int idVentas, int idClientes, Date feVenta, int cantidadPro) {
        try {
            Venta venta = new Venta();
            venta.setId_Ventas(idVentas);
            venta.setId_Clientes(idClientes);
            venta.setFe_Venta(feVenta);
            venta.setCantidad_Pro(cantidadPro);

            ventaDAO.actualizarVenta(venta);

            JOptionPane.showMessageDialog(null, "Venta actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar una venta
    public void eliminarVenta(int idVentas) {
        try {
            ventaDAO.eliminarVenta(idVentas);
            JOptionPane.showMessageDialog(null, "Venta eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método de prueba (para desarrollo)
    public static void main(String[] args) {
        VentaControlador controlador = new VentaControlador();

        List<DetalleVenta> detalles = new ArrayList<>();

        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId_Producto(1); // Asegúrate que el producto 1 existe en la BD
        detalle1.setCantidad_Producto(2);
        detalle1.setPrecio(45.50f);
        detalles.add(detalle1);

        // Crear una venta
        controlador.crearVenta(1, new Date(), detalles);

        // Mostrar todas las ventas
        List<Venta> ventas = controlador.obtenerTodasVentas();
        for (Venta v : ventas) {
            System.out.println("ID: " + v.getId_Ventas()
                    + ", Cliente: " + v.getId_Clientes()
                    + ", Fecha: " + v.getFe_Venta()
                    + ", Cantidad: " + v.getCantidad_Pro());
        }

        // Actualizar una venta
        controlador.actualizarVenta(1, 5, new Date(), 20);

        // Eliminar una venta
        controlador.eliminarVenta(1);
    }

    public List<Venta> listarVentas() {
    List<Venta> ventas = new ArrayList<>();
    try {
        ConexionBD.con = ConexionBD.getConnection();
        if (ConexionBD.con == null) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return ventas;
        }
        System.out.println("Conexión establecida: " + ConexionBD.con); // Depuración
        String sql = "SELECT * FROM Venta";
        System.out.println("Ejecutando consulta: " + sql); // Depuración
        PreparedStatement ps = (PreparedStatement) ConexionBD.con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Venta v = new Venta();
            v.setId_Ventas(rs.getInt("id_ventas"));
            v.setId_Clientes(rs.getInt("id_Cliente"));
            v.setCantidad_Pro(rs.getInt("Cantidad_Pro"));
            v.setFe_Venta(rs.getDate("Fe_Venta"));
            ventas.add(v);
        }
        System.out.println("Número de ventas recuperadas: " + ventas.size()); // Depuración
        ConexionBD.con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al listar ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NullPointerException e) {
        JOptionPane.showMessageDialog(null, "Error: Conexión a la base de datos no inicializada: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return ventas;
}
// Método para obtener clientes
public List<Cliente> obtenerClientes() {
    List<Cliente> clientes = new ArrayList<>();
    try {
        ConexionBD.con = ConexionBD.getConnection();
        if (ConexionBD.con == null) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return clientes;
        }
        System.out.println("Conexión establecida: " + ConexionBD.con); // Depuración
        String sql = "SELECT * FROM Cliente"; // Nombre de la tabla correcto
        System.out.println("Ejecutando consulta: " + sql); // Depuración
        PreparedStatement ps = ConexionBD.con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Cliente c = new Cliente();
            c.setIdCliente(rs.getInt("id_Cliente"));
            c.setNombre1(rs.getString("Nombre1"));
            c.setNombre2(rs.getString("Nombre2"));
            c.setApellido1(rs.getString("Apellido1"));
            c.setApellido2(rs.getString("Apellido2"));
            c.setDireccion(rs.getString("Direccion"));
            c.setTelefono(rs.getString("Telefono"));
            clientes.add(c);
        }
        System.out.println("Número de clientes recuperados: " + clientes.size()); // Depuración
        ConexionBD.con.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NullPointerException e) {
        JOptionPane.showMessageDialog(null, "Error: Conexión a la base de datos no inicializada: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return clientes;
}
}
