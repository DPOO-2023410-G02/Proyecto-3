package InterfazGraficaPrincipal;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.Compra;
import Model.GaleriaDeArte;
import Persistencia.PersistenciaPiezas;
import Persistencia.PersistenciaSubastaActual;
import Persistencia.PersistenciaSubastas;
import Persistencia.PersistenciaUsuarios;
import Pieza.Pieza;
import Usuario.Cliente;
import Usuario.Usuario;

public class VentanaFuncionesAdmin extends JFrame {

    public VentanaFuncionesAdmin() {
        setSize(850, 650);
        setTitle("Funciones del Administrador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Hola Administrador");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        // Crear JComboBox con las opciones
        String[] opciones = {"Ver historial compras", "Ver piezas", "Ver valor total de colección"};
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        JPanel panelOpciones = new JPanel();
        panelOpciones.add(new JLabel("Manejo Clientes"));
        panelOpciones.add(comboBox);
        add(panelOpciones, BorderLayout.CENTER);

        // Botones adicionales
        JButton btnVerificarConsignacion = new JButton("Verificar consignación piezas");
        JButton btnVerCatalogo = new JButton("Ver catálogo galería");
        JButton btnVerTodosClientes = new JButton("Ver todos los clientes");

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(btnVerificarConsignacion);
        panelBotones.add(btnVerCatalogo);
        panelBotones.add(btnVerTodosClientes);
        add(panelBotones, BorderLayout.WEST);

     // Acción para el JComboBox
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBox.getSelectedItem();
                if (seleccion.equals("Ver historial compras")) {
                    // Solicitar login del cliente
                    String loginCliente = JOptionPane.showInputDialog(null, "Ingrese el login del cliente:");
                    if (loginCliente != null && !loginCliente.isEmpty()) {
                        // Mostrar historial de compras
                        mostrarHistorialCompras(loginCliente);
                    } else {
                        JOptionPane.showMessageDialog(null, "Login inválido.");
                    }
                } else if (seleccion.equals("Ver piezas")) {
                    // Solicitar login del cliente
                    String loginClientePieza = JOptionPane.showInputDialog(null, "Ingrese el login del cliente:");
                    if (loginClientePieza != null && !loginClientePieza.isEmpty()) {
                        // Mostrar piezas del cliente
                        mostrarPiezasCliente(loginClientePieza);
                    } else {
                        JOptionPane.showMessageDialog(null, "Login inválido.");
                    }
                } else if (seleccion.equals("Ver valor total de colección")) {
                    // Solicitar login del cliente
                    String loginClienteValor = JOptionPane.showInputDialog(null, "Ingrese el login del cliente:");
                    if (loginClienteValor != null && !loginClienteValor.isEmpty()) {
                        // Mostrar valor total de colección
                        mostrarValorColeccion(loginClienteValor);
                    } else {
                        JOptionPane.showMessageDialog(null, "Login inválido.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Opción seleccionada: " + seleccion);
                }
            }
        });
        
        btnVerificarConsignacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Verificar consignación de piezas
                        GaleriaDeArte.getInventario().verificarConsignacionPiezas();

                        // Mostrar mensaje de verificación exitosa
                        JOptionPane.showMessageDialog(null, "Se verificó la consignación correctamente!");
                    }
                });
            }
        });
        
        btnVerCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener todas las piezas del catálogo de la galería
                List<Pieza> piezasEnCatalogo = GaleriaDeArte.getInventario().getPiezasTotales();
                if (piezasEnCatalogo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El catálogo de la galería está vacío.");
                } else {
                    // Crear una matriz para los datos de la tabla
                    String[][] datos = new String[piezasEnCatalogo.size()][4];
                    for (int i = 0; i < piezasEnCatalogo.size(); i++) {
                        Pieza pieza = piezasEnCatalogo.get(i);
                        datos[i][0] = pieza.getTitulo();
                        datos[i][1] = pieza.getAutor();
                        datos[i][2] = Integer.toString(pieza.getPrecioVenta());
                        datos[i][3] = pieza.getCodigoPieza();
                    }

                    // Crear un array con los nombres de las columnas
                    String[] columnas = {"Título", "Autor", "Precio Venta", "Código"};

                    // Crear la tabla con los datos y columnas
                    JTable tablaPiezas = new JTable(datos, columnas);

                    // Crear un JScrollPane para la tabla
                    JScrollPane scrollPane = new JScrollPane(tablaPiezas);

                    // Mostrar el diálogo con la tabla
                    JOptionPane.showMessageDialog(null, scrollPane, "Catálogo de la Galería", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        
        btnVerTodosClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la lista de clientes
                List<Usuario> clientes = GaleriaDeArte.getUsuarios();

                // Crear los datos para la tabla
                String[] columnNames = {"Nombre", "Login"};
                Object[][] data = new Object[clientes.size()][2];
                for (int i = 0; i < clientes.size(); i++) {
                    Usuario cliente = clientes.get(i);
                    data[i][0] = cliente.getNombre();
                    data[i][1] = cliente.getLogin();
                }

                // Crear el modelo de la tabla
                DefaultTableModel model = new DefaultTableModel(data, columnNames);

                // Crear la tabla con el modelo
                JTable table = new JTable(model);

                // Mostrar la tabla en un diálogo
                JScrollPane scrollPane = new JScrollPane(table);
                JOptionPane.showMessageDialog(null, scrollPane, "Clientes", JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        
        
        // Acción para el botón "SALIR"
        JButton btnSalir = new JButton("SALIR");
        add(btnSalir, BorderLayout.SOUTH);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	guardarDatos(VentanaPrincipal.getModelo());
                VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
                ventanaPrincipal.setLocationRelativeTo(null);
                ventanaPrincipal.setVisible(true);
                dispose();
            }
        });
    }
    
    private void mostrarValorColeccion(String loginClienteValor) {
        // Obtener el valor total de la colección del cliente
        int valorPiezas = GaleriaDeArte.getAdministrador().getValorColeccion(loginClienteValor);

        // Mostrar el valor en un cuadro de diálogo
        JOptionPane.showMessageDialog(null, "El valor total de la colección es: " + valorPiezas, "Valor Total de Colección", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarPiezasCliente(String loginClientePieza) {
        List<Pieza> piezasCliente = GaleriaDeArte.getAdministrador().getPiezasCliente(loginClientePieza);

        // Crear modelo de tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Título");
        model.addColumn("Precio");

        // Llenar la tabla con los datos de las piezas del cliente
        for (Pieza pieza : piezasCliente) {
            model.addRow(new Object[]{pieza.getTitulo(), pieza.getPrecioCompra()});
        }

        // Crear tabla con el modelo
        JTable table = new JTable(model);

        // Mostrar la tabla en un JOptionPane
        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Piezas del Cliente",
                JOptionPane.PLAIN_MESSAGE);
    }
    
    private void mostrarHistorialCompras(String loginCliente) {
        List<Compra> comprasCliente = GaleriaDeArte.getAdministrador().getHistorial(loginCliente);

        // Crear modelo de tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Título");
        model.addColumn("Precio");
        model.addColumn("Fecha");

        // Llenar la tabla con los datos del historial de compras
        for (Compra compra : comprasCliente) {
            model.addRow(new Object[]{compra.getPieza().getTitulo(), compra.getPrecio(), compra.getFecha()});
        }

        // Crear tabla con el modelo
        JTable table = new JTable(model);

        // Mostrar la tabla en un JOptionPane
        JOptionPane.showMessageDialog(null, new JScrollPane(table), "Historial de compras",
                JOptionPane.PLAIN_MESSAGE);
    }

    private static void guardarDatos(GaleriaDeArte galeria) {
        try {
            // Guardar usuarios
            JSONArray jUsuarios = new PersistenciaUsuarios().salvarUsuarios(galeria);
            Files.write(Paths.get("usuarios.json"), jUsuarios.toString().getBytes());

            // Guardar piezas
            JSONObject jPiezas = new PersistenciaPiezas().salvarPiezas(galeria);
            Files.write(Paths.get("piezas.json"), jPiezas.toString().getBytes());

            // Guardar subastas
            JSONArray jSubastas = new PersistenciaSubastas().salvarSubastas(galeria);
            Files.write(Paths.get("subastas.json"), jSubastas.toString().getBytes());

            // Guardar subasta actual
            JSONObject jSubastaActual = new PersistenciaSubastaActual().salvarSubasta(galeria);
            if (jSubastaActual != null) {
                Files.write(Paths.get("subastaActual.json"), jSubastaActual.toString().getBytes());
            }

            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        VentanaFuncionesAdmin ventana = new VentanaFuncionesAdmin();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}