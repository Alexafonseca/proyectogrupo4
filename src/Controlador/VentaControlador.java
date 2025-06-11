package Controlador;

import DAO.VentaDAO;
import DAO.DetalleVentaDAO;
import Modelo.Cliente;
import Modelo.Venta;
import Modelo.DetalleVenta;
import Util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class VentaControlador {

    private final VentaDAO ventaDAO;
    private final DetalleVentaDAO detalleVentaDAO;

    public VentaControlador() {
        this.ventaDAO = new VentaDAO();
        this.detalleVentaDAO = new DetalleVentaDAO();
    }

    public void crearVenta(int idClientes, Date feVenta, List<DetalleVenta> detalles) {
    if (detalles == null || detalles.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No se puede registrar una venta sin productos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // Validar existencia del cliente
        if (!clienteExiste(idClientes)) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sumar cantidades de todos los productos
        int totalCantidad = detalles.stream().mapToInt(DetalleVenta::getCantidad_Producto).sum();

        // Crear objeto Venta
        Venta venta = new Venta();
        venta.setId_Clientes(idClientes);
        venta.setCantidad_Pro(totalCantidad);
        venta.setFe_Venta(new java.sql.Date(feVenta.getTime()));

        // Guardar la venta y obtener el ID generado
        int idVenta = ventaDAO.crearVenta(venta);

        if (idVenta == -1) {
            throw new SQLException("No se pudo obtener el ID de la venta.");
        }

        // Guardar los detalles de la venta
        for (DetalleVenta detalle : detalles) {
            if (detalle.getId_Producto() <= 0) {
                throw new SQLException("ID del producto no válido en el detalle.");
            }
            detalle.setId_Venta(idVenta);
            detalleVentaDAO.crearDetalleVenta(detalle);
        }

        JOptionPane.showMessageDialog(null, "Venta y detalles creados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al crear la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

public Venta obtenerVentaPorId(int idVenta) {
    try {
        return new VentaDAO().obtenerVentaPorId(idVenta);
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
    public List<Venta> obtenerTodasVentas() {
        try {
            return ventaDAO.leerTodasVentas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

 public void actualizarVenta(int idVentas, int idClientes, Date feVenta, int cantidadPro) {
    try {
        Venta venta = new Venta();
        venta.setId_Ventas(idVentas);
        venta.setId_Clientes(idClientes);
        venta.setFe_Venta(new java.sql.Date(feVenta.getTime()));
        venta.setCantidad_Pro(cantidadPro);  // Esto parece estar mal

        ventaDAO.actualizarVenta(venta);
        JOptionPane.showMessageDialog(null, "Venta actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public void eliminarVenta(int idVentas) {
        try {
            ventaDAO.eliminarVenta(idVentas);
            JOptionPane.showMessageDialog(null, "Venta eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Venta> listarVentas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT * FROM Venta";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Venta v = new Venta();
                v.setId_Ventas(rs.getInt("id_ventas"));
                v.setId_Clientes(rs.getInt("id_Cliente"));
                v.setCantidad_Pro(rs.getInt("Cantidad_Pro"));
                v.setFe_Venta(rs.getDate("Fe_Venta"));
                ventas.add(v);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return ventas;
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId_Cliente(rs.getInt("id_Cliente"));
                c.setNombre1(rs.getString("Nombre1"));
                c.setNombre2(rs.getString("Nombre2"));
                c.setApellido1(rs.getString("Apellido1"));
                c.setApellido2(rs.getString("Apellido2"));
                c.setDireccion(rs.getString("Direccion"));
                c.setTelefono(rs.getString("Telefono"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return clientes;
    }

    private boolean clienteExiste(int idClientes) {
        List<Cliente> clientes = obtenerClientes();
        return clientes.stream().anyMatch(cliente -> cliente.getId_Cliente() == idClientes);
    }

    public static void main(String[] args) {
        VentaControlador controlador = new VentaControlador();
        List<DetalleVenta> detalles = new ArrayList<>();

        // Simulación de detalle de venta
        DetalleVenta detalle1 = new DetalleVenta();
        detalle1.setId_Producto(1); // Asegúrate que este producto exista
        detalle1.setCantidad_Producto(2);
        detalle1.setPrecio(45.50f);
        detalles.add(detalle1);

        // Crear venta
        controlador.crearVenta(1, new Date(), detalles); // Asegúrate que el cliente 1 exista

        // Listar ventas
        List<Venta> ventas = controlador.listarVentas();
        for (Venta v : ventas) {
            System.out.println("ID: " + v.getId_Ventas()
                    + ", Cliente: " + v.getId_Clientes()
                    + ", Fecha: " + v.getFe_Venta()
                    + ", Cantidad: " + v.getCantidad_Pro());
        }

        // Puedes descomentar estas líneas si necesitas probar otras funciones:
        // controlador.actualizarVenta(1, 2, new Date(), 10);
        // controlador.eliminarVenta(1);
    }

    

}
