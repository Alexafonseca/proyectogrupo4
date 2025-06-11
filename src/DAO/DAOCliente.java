package DAO;

import Modelo.Cliente;
import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edith Cruz
 *
 */
public class DAOCliente {

    public void crearCliente(Cliente cliente) throws SQLException {
        String sql = """
        INSERT INTO Cliente (
            Nombre1,
            Nombre2,
            Apellido1,
            Apellido2,
            Direccion,
            Telefono
        ) VALUES (?, ?, ?, ?, ?, ?)""";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre1());
            stmt.setString(2, cliente.getNombre2());
            stmt.setString(3, cliente.getApellido1());
            stmt.setString(4, cliente.getApellido2());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getTelefono());
            stmt.executeUpdate();
        }
    }

    public List<Cliente> leerTodosClientes() throws SQLException {
        String sql = "SELECT * FROM Cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId_Cliente(rs.getInt("id_Cliente"));
                cliente.setNombre1(rs.getString("Nombre1"));
                cliente.setNombre2(rs.getString("Nombre2"));
                cliente.setApellido1(rs.getString("Apellido1"));
                cliente.setApellido2(rs.getString("Apellido2"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setTelefono(rs.getString("Telefono"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public Cliente obtenerClientePorId(int id_Cliente) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE id_Cliente = ?";
        Cliente cliente = null;
        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_Cliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId_Cliente(rs.getInt("id_Cliente"));
                    cliente.setNombre1(rs.getString("Nombre1"));
                    cliente.setNombre2(rs.getString("Nombre2"));
                    cliente.setApellido1(rs.getString("Apellido1"));
                    cliente.setApellido2(rs.getString("Apellido2"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setTelefono(rs.getString("Telefono"));
                }
            }
        }

        return cliente;
    }

    public void actualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente SET Nombre1 = ?, Nombre2 = ?, Apellido1 = ?, Apellido2 = ?, Direccion = ?, Telefono = ? WHERE id_Cliente = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre1());
            stmt.setString(2, cliente.getNombre2());
            stmt.setString(3, cliente.getApellido1());
            stmt.setString(4, cliente.getApellido2());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getTelefono());
            stmt.setInt(7, cliente.getId_Cliente());
            stmt.executeUpdate();
        }
    }

    public void eliminarCliente(int id_Cliente) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id_Cliente = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_Cliente);
            stmt.executeUpdate();
        }
    }
    
public List<Cliente> obtenerTodosClientes() throws SQLException {
    String sql = "SELECT * FROM Cliente";
    List<Cliente> clientes = new ArrayList<>();
    try (Connection c = ConexionBD.getConnection(); 
         PreparedStatement stmt = c.prepareStatement(sql); 
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Cliente cliente = new Cliente();
            cliente.setId_Cliente(rs.getInt("id_Cliente"));
            cliente.setNombre1(rs.getString("Nombre1"));
            cliente.setNombre2(rs.getString("Nombre2") != null ? rs.getString("Nombre2") : "");
            cliente.setApellido1(rs.getString("Apellido1"));
            cliente.setApellido2(rs.getString("Apellido2") != null ? rs.getString("Apellido2") : "");
            cliente.setDireccion(rs.getString("Direccion") != null ? rs.getString("Direccion") : "");
            cliente.setTelefono(rs.getString("Telefono") != null ? rs.getString("Telefono") : "");
            clientes.add(cliente);
        }
    }
    return clientes;
}
    public static void main(String[] args) {
        try {
            DAOCliente dao = new DAOCliente();

            // Actualizar un cliente
            Cliente cliente = new Cliente();
            cliente.setId_Cliente(1); // ID existente
            cliente.setNombre1("Juan");
            cliente.setNombre2("Carlos");
            cliente.setApellido1("Pérez");
            cliente.setApellido2("Gómez");
            cliente.setDireccion("Calle 123");
            cliente.setTelefono("12345678");
            dao.actualizarCliente(cliente);
            System.out.println("Cliente actualizado.");

            // Eliminar un cliente
            dao.eliminarCliente(1); // ID a eliminar
            System.out.println("Cliente eliminado.");

            // Leer y mostrar todos los clientes para verificar
            List<Cliente> clientes = dao.leerTodosClientes();
            System.out.println("Lista de cliente:");
            for (Cliente cli : clientes) {
                System.out.println("ID: " + cli.getId_Cliente()
                        + ", Nombre1:" + cli.getNombre1()
                        + ", Nombre2:" + cli.getNombre2()
                        + ", Apellido1: " + cli.getApellido1()
                        + " , Apellido2:" + cli.getApellido2()
                        + ", Dirección: " + cli.getDireccion()
                        + ", Telefono: " + cli.getTelefono());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
