/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;
import Controlador.VentaControlador;
import Controlador.ClienteControlador ;
import Controlador.DetalleVentaControlador;
import Controlador.ProductoControlador;
import DAO.DAOCliente;
import Modelo.Venta;
import Modelo.Cliente;
import Modelo.DetalleVenta;
import Modelo.Producto;
import Util.ConexionBD;
import com.sun.jdi.connect.spi.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;





/**
 *
 * @author Alexa
 */
public class VistaVenta extends javax.swing.JPanel {
    private final VentaControlador controlador = new VentaControlador();
    private final VentaControlador ventaControlador;
    private final ClienteControlador clienteControlador;
    private final ProductoControlador productoControlador;
    private final DetalleVentaControlador detalleVentaControlador;
    private Integer idClienteSeleccionado = null;
     private Integer idProductoSeleccionado = null;
    private Timer timer;
    private boolean fechaCargada = false;
    private boolean horabd = false;
  

    /**
     * Creates new form VistaDetalleVenta
     */
    public VistaVenta() {
        initComponents();
        this.ventaControlador = new VentaControlador();
         this.clienteControlador = new ClienteControlador();
         this.productoControlador = new ProductoControlador();
         this.detalleVentaControlador = new DetalleVentaControlador();
         cargarDatosTablaVentas();
         eventoComboProductos();
         cargarClientes();
            
            
               
       selectorfechaVenta.setDate(new Date());
    ((JTextField) selectorfechaVenta.getDateEditor().getUiComponent()).setEditable(false); 
        
    // Limpiar las filas vacías de tablaVentas
    DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
    modelDetalles.setRowCount(0);
    
    listarVentas();
    cargarClientes();
    actualizarHora();
    cargarProductos();
        
    }
    
    private void limpiarCampos() {
    textCantidad.setText("");
    selectorfechaVenta.setDate(null);
    comboClientes.setSelectedIndex(0);
    
     // Limpiar la tabla de Ventas
    tablaDetalles.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID Producto", "Producto", "Precio Unitario", "Cantidad", "Subtotal"}));
    
    listarVentas();
    cargarClientes();
    cargarProductos();
    
    
    horabd = false; // Restablece para mostrar hora actual
    actualizarHora(); // Vuelve a iniciar el timer
    
    
    btnEliminar.setEnabled(true);
    btnGuardar.setEnabled(true);
    
    horabd = false; // Restablece para mostrar hora actual
    actualizarHora(); // Vuelve a iniciar el timer
}
    
    private void actualizarHora() {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("America/Managua"));

    // Detener el timer anterior si existe
    if (timer != null) {
        timer.stop();
    }

    if (horabd) {
        return; // No actualiza la hora si está cargada desde la base de datos
    }

    timer = new Timer(1000, e -> {
        Date now = new Date();
        Hora.setText(sdf.format(now));
    });
    timer.start();
}

   private void listarVentas() {
    DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
    modelo.setRowCount(0); // limpiar tabla

    List<Venta> ventas = controlador.listarVentas();
    
     if (ventas != null) {
        DefaultTableModel model = (DefaultTableModel) tablaVentas.getModel();
        model.setRowCount(0);

  for (Venta v : ventas) {
            Cliente cliente = clienteControlador.obtenerClientePorId(v.getId_Clientes());
            String nombreCliente = cliente.getNombre1()+ " " + cliente.getApellido1();

        modelo.addRow(new Object[]{
            v.getId_Ventas(),
            nombreCliente,
            v.getCantidad_Pro(),
            v.getFe_Venta()
        });
    }}
}
    
    private void cargarClientes() {
     try {
        // Obtener las categorías desde el controlador
        List<Cliente> clientes = clienteControlador.obtenerTodosClientes();

        // Limpiar el combo box por si tiene datos
        comboClientes.removeAllItems();

        // Agregar cada categoría al combo box
        for (Cliente c : clientes) {
            comboClientes.addItem(c.getNombre1()+ " " +c.getApellido1()); // Mostrar el nombre
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
                "Error al cargar los clientes: " + e.getMessage());
    }
}
    
    private void cargarProductos() {
    try {
        // Obtener los productos desde el controlador
        List<Producto> productos = productoControlador.obtenerTodosProductos();

        // Limpiar el combo box por si tiene datos
        comboProductos.removeAllItems();

        // Agregar cada nombre de producto al combo box
        for (Producto p : productos) {
            comboProductos.addItem(p.getNombre_prod()); // Mostrar solo el nombre del producto
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
                "Error al cargar los productos: " + e.getMessage());
    }
}
    
  private void eventoComboProductos() {
    comboProductos.addActionListener(e -> {
        // Obtener el índice seleccionado
        int indiceSeleccionado = comboProductos.getSelectedIndex();

        if (indiceSeleccionado >= 0) { // Verificar que se haya seleccionado algo
            try {
                // Obtener la lista de categorías desde el controlador o memoria
                List<Producto> productos = productoControlador.obtenerTodosProductos();

                // Obtener el objeto de categoría correspondiente al índice seleccionado
                Producto productoSeleccionado = productos.get(indiceSeleccionado);

                // Actualizar la variable global con el ID de la categoría seleccionada
                idProductoSeleccionado = productoSeleccionado.getId_producto();
                
                // Actualizar el campo textPrecio con el precio unitario del producto
                textPrecio.setText(String.valueOf(productoSeleccionado.getPrecio_Costo()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al seleccionar categoría: " + ex.getMessage());
            }
        }
    });
}
  
 private void cargarDatosTablaVentas() {
    try {
        List<Venta> ventas = ventaControlador.obtenerTodasVentas();

        if (ventas != null) {
            DefaultTableModel model = (DefaultTableModel) tablaVentas.getModel();
            model.setRowCount(0); // Limpiar la tabla

            for (Venta v : ventas) {
                // Obtener el cliente
                Cliente cliente = clienteControlador.obtenerClientePorId(v.getId_Clientes());
                String nombreCliente = (cliente != null) ? 
                    cliente.getNombre1() + " " + cliente.getApellido1() : "";

                // Asegurar que el orden de los datos coincida con las columnas: ID Venta, Cliente, Cantidad, Fecha
                Object[] row = {
                    v.getId_Ventas(),      // ID Venta (columna 1)
                    nombreCliente,         // Cliente (columna 2)
                    v.getCantidad_Pro(),   // Cantidad (columna 3)
                    v.getFe_Venta()        // Fecha (columna 4)
                };

                // Depuración: Imprimir los datos para verificar el orden
                System.out.println("Cargando fila: ID=" + row[0] + ", Cliente=" + row[1] + 
                                   ", Cantidad=" + row[2] + ", Fecha=" + row[3]);

                model.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudieron obtener las ventas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar las ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }  
}

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgregar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        textCantidad = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        textBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Hora = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        selectorfechaVenta = new com.toedter.calendar.JDateChooser();
        btnAgregar1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        textPrecio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        comboProductos = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnQuitarDetalle = new javax.swing.JButton();
        comboClientes = new javax.swing.JComboBox<>();

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                btnAgregaraccionBotonGuardar(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        setBackground(new java.awt.Color(255, 153, 255));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, " Ventas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Venta", "Cliente", "Cantidad", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaVentas);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Cantidad ");

        textCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCantidadActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardar.setText("Guardar Venta");
        btnGuardar.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                btnGuardaraccionBotonGuardar(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setText("Limpiar ");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminar.setText("Eliminar Venta");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar Venta");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        textBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Fecha Venta");

        Hora.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Hora.setText("00:00:00");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Buscar");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Cliente");

        btnAgregar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar1.setText("Agregar");
        btnAgregar1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                btnAgregar1accionBotonGuardar(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        btnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar1ActionPerformed(evt);
            }
        });

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID producto", "Producto", "Cantidad Producto", "Precio", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDetalles);

        textPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrecioActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Precio");

        comboProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProductosActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Producto");

        btnQuitarDetalle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuitarDetalle.setText("Quitar Detalle");
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        comboClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClientesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 557, Short.MAX_VALUE)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addGap(75, 75, 75))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectorfechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(Hora)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel6)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel7)
                                .addGap(88, 88, 88)
                                .addComponent(jLabel4))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(btnAgregar1)
                .addGap(146, 146, 146))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQuitarDetalle)
                .addGap(45, 45, 45))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Hora)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(btnLimpiar)
                                    .addComponent(btnAgregar1))))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectorfechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(textPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnQuitarDetalle))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnActualizar))
                    .addComponent(btnGuardar))
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCantidadActionPerformed

    private void btnGuardaraccionBotonGuardar(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_btnGuardaraccionBotonGuardar
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardaraccionBotonGuardar

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

try {
    // Obtener el nombre seleccionado del combo (como String)
    String nombreSeleccionado = (String) comboClientes.getSelectedItem();
    if (nombreSeleccionado == null || nombreSeleccionado.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Recargar todos los clientes para buscar el correcto
    List<Cliente> clientes = clienteControlador.obtenerTodosClientes();
    Cliente clienteSeleccionado = null;
    for (Cliente c : clientes) {
        String nombreCliente = c.getNombre1() + " " + c.getApellido1();
        if (nombreCliente.equals(nombreSeleccionado)) {
            clienteSeleccionado = c;
            break;
        }
    }
    if (clienteSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Cliente no encontrado: " + nombreSeleccionado, "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int id_Cliente = clienteSeleccionado.getId_Cliente(); // Accede al ID directamente

    // Validar fecha de venta
    Date Fe_Venta = selectorfechaVenta.getDate();
    if (Fe_Venta == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validar tabla de detalles
    DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
    int rowCount = modelDetalles.getRowCount();
    if (rowCount == 0) {
        JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Procesar detalles y calcular total
    List<DetalleVenta> detalles = new ArrayList<>();
    float total_Venta = 0;

    for (int i = 0; i < rowCount; i++) {
        Object idProductoObj = modelDetalles.getValueAt(i, 0);
        Object cantidadObj = modelDetalles.getValueAt(i, 2);
        Object precioObj = modelDetalles.getValueAt(i, 3);
        Object subtotalObj = modelDetalles.getValueAt(i, 4);

        // Validar y convertir valores
        int idProducto = ((Number) idProductoObj).intValue();
        int cantidad = ((Number) cantidadObj).intValue();
        float precio_venta = ((Number) precioObj).floatValue();
        float subtotal = ((Number) subtotalObj).floatValue();

        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad en la fila " + (i + 1) + " debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DetalleVenta detalle = new DetalleVenta();
        detalle.setId_Producto(idProducto);
        detalle.setCantidad_Producto(cantidad);
        detalle.setPrecio(precio_venta);
        detalles.add(detalle);

        total_Venta += subtotal;
    }

    // Guardar la venta
    ventaControlador.crearVenta(id_Cliente, Fe_Venta, detalles);

    // Limpiar y recargar
    limpiarCampos();
    cargarDatosTablaVentas();

    JOptionPane.showMessageDialog(this, "Venta guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Error inesperado al guardar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
}
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
                                            
   try {
        int filaSeleccionada = tablaVentas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tablaVentas.getModel();
        int idVenta = (int) model.getValueAt(filaSeleccionada, 0);

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar la venta con ID " + idVenta + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            ventaControlador.eliminarVenta(idVenta);
            cargarDatosTablaVentas();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

                                    

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
try {
    
        
        int filaSeleccionada = tablaVentas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener idVenta de la fila seleccionada
        int idVenta = (int) ((DefaultTableModel) tablaVentas.getModel()).getValueAt(filaSeleccionada, 0);

        int indiceCliente = comboClientes.getSelectedIndex();
        if (indiceCliente < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Cliente> clientes = clienteControlador.obtenerTodosClientes();
        int idCliente = clientes.get(indiceCliente).getId_Cliente();

        Date fechaVenta = selectorfechaVenta.getDate();
        if (fechaVenta == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
        int rowCount = modelDetalles.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular total de la venta sumando subtotales
        int totalVenta = 0;
        for (int i = 0; i < rowCount; i++) {
            totalVenta += ((Number) modelDetalles.getValueAt(i, 4)).floatValue();
        }

        // Actualizar la venta principal (si tu método espera float, no hagas cast a int)
        ventaControlador.actualizarVenta(idVenta, idCliente, fechaVenta, totalVenta);

        // Eliminar detalles antiguos de la venta para luego insertar los nuevos
        List<DetalleVenta> detallesAntiguos = detalleVentaControlador.obtenerDetallesPorIdVenta(idVenta);
        for (DetalleVenta detalle : detallesAntiguos) {
            detalleVentaControlador.eliminarDetalleVenta(detalle.getId_DetalleVenta());
        }

        // Insertar los nuevos detalles con los valores correctos
        for (int i = 0; i < rowCount; i++) {
            int idProducto = ((Number) modelDetalles.getValueAt(i, 0)).intValue();
            float precioUnitario = ((Number) modelDetalles.getValueAt(i, 2)).floatValue();
            int cantidad = ((Number) modelDetalles.getValueAt(i, 3)).intValue();
          
            System.out.println(idProducto);
            System.out.println(idVenta);
            System.out.println(cantidad);
            
            
            detalleVentaControlador.crearDetalleVenta(idVenta, idProducto, cantidad, precioUnitario);
        }

        // Limpiar tabla de detalles y recargar datos
        tablaDetalles.setModel(new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"ID Producto", "Producto", "Precio Unitario", "Cantidad", "Subtotal"}
        ));
        limpiarCampos();
        cargarDatosTablaVentas();

        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(true);

        JOptionPane.showMessageDialog(this, "Venta actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al actualizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
       limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnAgregaraccionBotonGuardar(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_btnAgregaraccionBotonGuardar
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregaraccionBotonGuardar

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnAgregar1accionBotonGuardar(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_btnAgregar1accionBotonGuardar
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregar1accionBotonGuardar

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
try {
        // Obtener el índice seleccionado del comboProductos
        int indiceSeleccionado = comboProductos.getSelectedIndex();
        if (indiceSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener la lista de productos
        List<Producto> productos = productoControlador.obtenerTodosProductos();
        Producto productoSeleccionado = productos.get(indiceSeleccionado);

        // Obtener el precio del producto
        float Precio_Costo = (float) productoSeleccionado.getPrecio_Costo();

        // Obtener la cantidad ingresada
        String cantidadStr = textCantidad.getText().trim();
        if (cantidadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular el subtotal
        float subtotal = Precio_Costo * cantidad;

        // Agregar los datos a la tabla tablaDetalles
        DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
        Object[] row = {
            productoSeleccionado.getId_producto(),
            productoSeleccionado.getNombre_prod(),
            cantidad,
            Precio_Costo,
            subtotal
        };
        model.addRow(row);

        // Limpiar los campos después de agregar
        textCantidad.setText("");
        textPrecio.setText("");
        cargarProductos();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al agregar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
       
    }//GEN-LAST:event_btnAgregar1ActionPerformed

    private void textPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPrecioActionPerformed

    private void comboProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProductosActionPerformed

        comboProductos.addActionListener(e -> {
            // Obtener el índice seleccionado
            int indiceSeleccionado = comboProductos.getSelectedIndex();

            if (indiceSeleccionado >= 0) { // Verificar que se haya seleccionado algo
                try {
                    // Obtener la lista de categorías desde el controlador o memoria
                    List<Producto> productos = productoControlador.obtenerTodosProductos();

                    // Obtener el objeto de categoría correspondiente al índice seleccionado
                    Producto productoSeleccionado = productos.get(indiceSeleccionado);

                    // Actualizar la variable global con el ID de la categoría seleccionada
                    idProductoSeleccionado = productoSeleccionado.getId_producto();

                    // Actualizar el campo textPrecio con el precio unitario del producto
                    textPrecio.setText(String.valueOf(productoSeleccionado.getPrecio_Costo()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al seleccionar categoría: " + ex.getMessage());
                }
            }
        });
    }//GEN-LAST:event_comboProductosActionPerformed

    private void textBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscarActionPerformed
     
   
                                       
    String textoBusqueda = textBuscar.getText().trim();
    List<Venta> ventas = ventaControlador.obtenerTodasVentas();

    DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
    modelo.setRowCount(0); // Limpiar tabla

    if (ventas != null) {
        for (Venta v : ventas) {
            Cliente cliente = clienteControlador.obtenerClientePorId(v.getId_Clientes());
            String nombreCliente = cliente.getNombre1() + " " + cliente.getApellido1();

            // Comparación exacta solo por ID_Ventas
            if (textoBusqueda.isEmpty() || String.valueOf(v.getId_Ventas()).equals(textoBusqueda)) {
                Object[] fila = {
                            v.getId_Ventas(),        // ID Venta
                           nombreCliente,           // Nombre del Cliente
                           v.getCantidad_Pro(),     // Cantidad
                           v.getFe_Venta()          // Fecha
                       };
                modelo.addRow(fila);
            }
        }
    }


    }//GEN-LAST:event_textBuscarActionPerformed

    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed

        try {
            // Obtener el índice de la fila seleccionada en tablaDetalles
            int filaSeleccionada = tablaDetalles.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un detalle para quitar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar la fila seleccionada del modelo de la tabla
            DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
            model.removeRow(filaSeleccionada);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al quitar el detalle: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnQuitarDetalleActionPerformed

    private void comboClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboClientesActionPerformed

    private void tablaVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentasMouseClicked
        // Verificar si es un doble clic
    if (evt.getClickCount() == 2) {
        try {
            btnEliminar.setEnabled(false);
            btnGuardar.setEnabled(false);
            
            // Obtener el índice de la fila seleccionada en tablaVentas
            int filaSeleccionada = tablaVentas.getSelectedRow();
            if (filaSeleccionada == -1) {
                return; // No hacer nada si no hay fila seleccionada
            }

            // Obtener el idVenta de la fila seleccionada
            DefaultTableModel modelVentas = (DefaultTableModel) tablaVentas.getModel();
            int idVenta = (int) modelVentas.getValueAt(filaSeleccionada, 0);

            // Obtener la venta seleccionada para acceder a sus datos
            List<Venta> ventas = ventaControlador.obtenerTodasVentas();
            Venta ventaSeleccionada = null;
            for (Venta v : ventas) {
                if (v.getId_Ventas() == idVenta) {
                    ventaSeleccionada = v;
                    break;
                }
            }
            if (ventaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Venta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cargar cliente en comboClientes
            List<Cliente> clientes = clienteControlador.obtenerTodosClientes();
            int indiceCliente = -1;
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId_Cliente()== ventaSeleccionada.getId_Clientes()) {
                    indiceCliente = i;
                    break;
                }
            }
            if (indiceCliente != -1) {
                idClienteSeleccionado = ventaSeleccionada.getId_Clientes();
                comboClientes.setSelectedIndex(indiceCliente);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

             
            // Detener el timer actual
            if (timer != null) {
                timer.stop();
            }
            
            // Asignar la hora al label
            horabd = true;
            java.text.SimpleDateFormat horaFormato = new java.text.SimpleDateFormat("HH:mm:ss");
            String horaVenta = horaFormato.format(ventaSeleccionada.getFe_Venta());
            Hora.setText(horaVenta); // Ajusta 'horaLabel' al nombre real de tu JLabel

            // Cargar la fecha en selectorfechaContratacion
            selectorfechaVenta.setDate(ventaSeleccionada.getFe_Venta());

            // Limpiar y cargar los detalles en tablaDetalles
            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            modelDetalles.setRowCount(0);

            List<DetalleVenta> detalles = detalleVentaControlador.obtenerTodosDetalleVenta();
            if (detalles != null) {
                for (DetalleVenta detalle : detalles) {
                    if (detalle.getId_Venta()== idVenta) {
                        Producto producto = productoControlador.obtenerProductoPorId(detalle.getId_Producto());
                        String nombreProducto = (producto != null) ? producto.getNombre_prod(): "Desconocido";

                        Object[] row = {
                            detalle.getId_Producto(),
                            nombreProducto,
                            detalle.getPrecio(),
                            detalle.getCantidad_Producto(),
                            detalle.getPrecio()* detalle.getCantidad_Producto() // Subtotal
                        };
                        modelDetalles.addRow(row);
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_tablaVentasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Hora;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JComboBox<String> comboProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser selectorfechaVenta;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField textBuscar;
    private javax.swing.JTextField textCantidad;
    private javax.swing.JTextField textPrecio;
    // End of variables declaration//GEN-END:variables
}