package Controlador;

import DAO.DAOCliente;
import Modelo.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteControlador {

    private final DAOCliente daoCliente;

    public ClienteControlador() {
        this.daoCliente = new DAOCliente();
    }

    // Método para crear un nuevo cliente
    public void crearCliente(Cliente cliente) {
        try {
            daoCliente.crearCliente(cliente);
            JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuración
        }
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosClientes() {
        try {
            List<Cliente> clientes = daoCliente.leerTodosClientes();
            System.out.println("Clientes obtenidos: " + (clientes != null ? clientes.size() : 0)); // Depuración
            return clientes != null ? clientes : new ArrayList<>(); // Devuelve lista vacía si es null
        } catch (SQLException e) {
            System.out.println("Error al leer los clientes: " + e.getMessage()); // Depuración en consola
            JOptionPane.showMessageDialog(null, "Error al leer los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return new ArrayList<>(); // Devuelve lista vacía en caso de error
        }
    }

    public Cliente obtenerClientePorId(int idCliente) {
        try {
            Cliente cliente = daoCliente.obtenerClientePorId(idCliente);
            System.out.println("Cliente obtenido por ID " + idCliente + ": " + (cliente != null ? cliente.toString() : "null")); // Depuración
            return cliente;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuración
            return null;
        }
    }

    // Método para actualizar un cliente existente
    public void actualizarCliente(Cliente cliente) {
        try {
            daoCliente.actualizarCliente(cliente);
            JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuración
        }
    }

    // Método para eliminar un cliente
    public void eliminarCliente(int idCliente) {
        try {
            daoCliente.eliminarCliente(idCliente);
            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuración
        }
    }

    // Método main para pruebas
    public static void main(String[] args) {
        ClienteControlador controlador = new ClienteControlador();

        // Crear un cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre1("Ana");
        nuevoCliente.setNombre2("María");
        nuevoCliente.setApellido1("Fernández");
        nuevoCliente.setApellido2("López");
        nuevoCliente.setDireccion("Calle 456");
        nuevoCliente.setTelefono("98765432");
        controlador.crearCliente(nuevoCliente);

        // Leer todos los clientes
        List<Cliente> clientes = controlador.obtenerTodosClientes();
        if (clientes != null && !clientes.isEmpty()) {
            System.out.println("Lista de clientes:");
            for (Cliente cli : clientes) {
                System.out.println("ID: " + cli.getId_Cliente()
                        + ", Nombre1: " + cli.getNombre1()
                        + ", Nombre2: " + cli.getNombre2()
                        + ", Apellido1: " + cli.getApellido1()
                        + ", Apellido2: " + cli.getApellido2()
                        + ", Dirección: " + cli.getDireccion()
                        + ", Teléfono: " + cli.getTelefono());
            }
        } else {
            System.out.println("No se encontraron clientes.");
        }

        // Actualizar un cliente (suponiendo que ID 1 existe)
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setId_Cliente(1);
        clienteActualizado.setNombre1("Ana");
        clienteActualizado.setNombre2("María");
        clienteActualizado.setApellido1("Fernández");
        clienteActualizado.setApellido2("Gómez");
        clienteActualizado.setDireccion("Calle 789");
        clienteActualizado.setTelefono("55555555");
        controlador.actualizarCliente(clienteActualizado);

        // Eliminar un cliente (opcional, coméntalo si quieres conservar datos)
        // controlador.eliminarCliente(1);
    }
}