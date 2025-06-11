package Modelo;

/**
 *
 * @author Edith Cruz
 * 
 */

public class Cliente {
    private int id_Cliente;
    private String Nombre1;
    private String Nombre2;
    private String Apellido1;
    private String Apellido2;
    private String Direccion;
    private String Telefono;

    public Cliente() {
    }

    public Cliente(int id_Cliente, String Nombre1, String Nombre2, String Apellido1, String Apellido2, String Direccion, String Telefono) {
        this.id_Cliente = id_Cliente;
        this.Nombre1 = Nombre1;
        this.Nombre2 = Nombre2;
        this.Apellido1 = Apellido1;
        this.Apellido2 = Apellido2;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
    }

    public int getId_Cliente() {
        return id_Cliente;
    }

    public void setId_Cliente(int id_Cliente) {
        this.id_Cliente = id_Cliente;
    }

    public String getNombre1() {
        return Nombre1;
    }

    public void setNombre1(String Nombre1) {
        this.Nombre1 = Nombre1;
    }

    public String getNombre2() {
        return Nombre2;
    }

    public void setNombre2(String Nombre2) {
        this.Nombre2 = Nombre2;
    }

    public String getApellido1() {
        return Apellido1;
    }

    public void setApellido1(String Apellido1) {
        this.Apellido1 = Apellido1;
    }

    public String getApellido2() {
        return Apellido2;
    }

    public void setApellido2(String Apellido2) {
        this.Apellido2 = Apellido2;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

     @Override
    public String toString() {
        // Combinar nombre y apellido, manejando casos donde Nombre2 o Apellido2 puedan ser null
        String nombreCompleto = Nombre1 + (Nombre2 != null && !Nombre2.isEmpty() ? " " + Nombre2 : "") + " " +
                               Apellido1 + (Apellido2 != null && !Apellido2.isEmpty() ? " " + Apellido2 : "");
        return nombreCompleto.trim();
    }
}