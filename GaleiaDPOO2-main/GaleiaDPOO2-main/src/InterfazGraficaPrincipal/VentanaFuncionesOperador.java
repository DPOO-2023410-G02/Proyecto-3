package InterfazGraficaPrincipal;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.GaleriaDeArte;
import Persistencia.PersistenciaPiezas;
import Persistencia.PersistenciaSubastaActual;
import Persistencia.PersistenciaSubastas;
import Persistencia.PersistenciaUsuarios;
import Pieza.Pieza;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class VentanaFuncionesOperador extends JFrame {
    public VentanaFuncionesOperador() {
        setSize(850, 650);
        setTitle("Funciones del Operador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el mensaje de bienvenida
        JLabel lblMensaje = new JLabel("Bienvenido, Operador");

        // Crear el JComboBox con opciones y ponerlo en un JPanel con borde titulado
        String[] opcionesSubasta = {
                "Apartar Pieza para subasta", "Revisar registros subasta", "Revisar Catalogo"
        };
        JComboBox<String> comboBoxOpcionesSubasta = new JComboBox<>(opcionesSubasta);
        JPanel panelComboBoxSubasta = new JPanel();
        panelComboBoxSubasta.setBorder(new TitledBorder("Opciones de Subasta"));
        panelComboBoxSubasta.add(comboBoxOpcionesSubasta);

        // Crear los botones de "Crear Subasta", "Iniciar Subasta" y "Finalizar Subasta"
        JButton btnCrearSubasta = new JButton("Crear Subasta");
        JButton btnIniciarSubasta = new JButton("Iniciar Subasta");
        JButton btnFinalizarSubasta = new JButton("Finalizar Subasta");

        // Panel para el mensaje, el JComboBox y los botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(lblMensaje);
        panelSuperior.add(panelComboBoxSubasta);
        panelSuperior.add(btnCrearSubasta);
        panelSuperior.add(btnIniciarSubasta);
        panelSuperior.add(btnFinalizarSubasta);

        // Agregar el panel superior al norte del BorderLayout
        add(panelSuperior, BorderLayout.NORTH);

        // Acción para el JComboBox
        comboBoxOpcionesSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBoxOpcionesSubasta.getSelectedItem();
                if (seleccion.equals("Revisar Catalogo")) {
                    // Aquí puedes mostrar las piezas disponibles para comprar
                    List<Pieza> piezasEnVenta = GaleriaDeArte.getInventario().getPiezasTotales();
                    if (piezasEnVenta.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay piezas en venta.");
                    } else {
                        // Crear una matriz para los datos de la tabla
                        String[][] datos = new String[piezasEnVenta.size()][4];
                        for (int i = 0; i < piezasEnVenta.size(); i++) {
                            Pieza pieza = piezasEnVenta.get(i);
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
                        JOptionPane.showMessageDialog(null, scrollPane, "Piezas en Venta", JOptionPane.PLAIN_MESSAGE);
                    }
                } else if (seleccion.equals("Apartar Pieza para subasta")) {
                    String codigoPieza = JOptionPane.showInputDialog(null, "Ingrese el código de la pieza a apartar:");
                    if (codigoPieza != null) {
                        Pieza pieza = GaleriaDeArte.getInventario().getPiezaTotal(codigoPieza);
                        if (pieza != null) {
                        	GaleriaDeArte.getOperador().agregarPiezaSubasta(codigoPieza);;
                            JOptionPane.showMessageDialog(null, "Pieza apartada para la subasta.");
                        } else {
                            JOptionPane.showMessageDialog(null, "El código de pieza ingresado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ingresó ningún código de pieza.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (seleccion.equals("Revisar registros subasta")) {
                    List<List<String>> registrosSubasta = GaleriaDeArte.getRegistrosPorSubasta();
                    if (registrosSubasta.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay registros de subasta.");
                    } else {
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("Subasta");
                        model.addColumn("Pujas");

                        for (int i = 0; i < registrosSubasta.size(); i++) {
                            List<String> subasta = registrosSubasta.get(i);
                            StringBuilder pujas = new StringBuilder();
                            for (String puja : subasta) {
                                pujas.append(puja).append("\n");
                            }
                            model.addRow(new Object[]{"Subasta " + (i + 1), pujas.toString()});
                        }

                        JTable tablaRegistros = new JTable(model);
                        JScrollPane scrollPane = new JScrollPane(tablaRegistros);
                        JOptionPane.showMessageDialog(null, scrollPane, "Registros de Subasta", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, seleccion + " seleccionada");
                }
            }
        });

        // Acción para el botón "Crear Subasta"
        btnCrearSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GaleriaDeArte.getOperador().CrearSubasta();
                JOptionPane.showMessageDialog(null, "Subasta Creada");
            }
        });

        btnIniciarSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (GaleriaDeArte.getSubasta() == null){
                    JOptionPane.showMessageDialog(null, "No hay una subasta creada.", "Error", JOptionPane.ERROR_MESSAGE);
            	}else if (GaleriaDeArte.getInventario().getPiezasTotales().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay piezas para subastar.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (GaleriaDeArte.getSubasta().getClientesSubasta().isEmpty()){
                    JOptionPane.showMessageDialog(null, "No hay clientes en la subasta.", "Error", JOptionPane.ERROR_MESSAGE);
                    
                }  
                else {
                	GaleriaDeArte.getOperador().iniciarSubasta();
                    JOptionPane.showMessageDialog(null, "Subasta Iniciada");
                }
            }
        });
        // Acción para el botón "Finalizar Subasta"
        btnFinalizarSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String nombreArchivo = "subastaActual.json";
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) {archivo.delete();}
                
            	if(GaleriaDeArte.getSubasta() == null) {
                    JOptionPane.showMessageDialog(null, "No hay subasta para cerrar.", "Error", JOptionPane.ERROR_MESSAGE);
            	};
                GaleriaDeArte.getOperador().finalizarSubastaOperador();;
                JOptionPane.showMessageDialog(null, "Subasta Finalizada");
            }
        });

        // Botón de salir
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
        VentanaFuncionesOperador ventana = new VentanaFuncionesOperador();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}