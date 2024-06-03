package InterfazGraficaPrincipal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.GaleriaDeArte;
import Model.PiezaSubastada;
import Model.Subasta;
import Persistencia.PersistenciaPiezas;
import Persistencia.PersistenciaSubastaActual;
import Persistencia.PersistenciaSubastas;
import Persistencia.PersistenciaUsuarios;
import Pieza.Pieza;
import Usuario.Administrador;
import Usuario.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaFuncionesCliente extends JFrame {
    public VentanaFuncionesCliente() {
        setSize(750, 650);
        setTitle("Funciones del Cliente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el mensaje de bienvenida
        JLabel lblMensaje = new JLabel("Bienvenido, Cliente");

        // Crear el JComboBox con opciones y ponerlo en un JPanel con borde titulado
        String[] opcionesCuenta = {
            "Ver Piezas Propias", "Ver Saldo Actual", "Agregar Saldo", 
            "Registrar Pieza Nueva", "Consignar pieza Galeria"
        };

        JComboBox<String> comboBoxOpcionesCuenta = new JComboBox<>(opcionesCuenta);
        JPanel panelComboBoxCuenta = new JPanel();
        panelComboBoxCuenta.setBorder(new TitledBorder("Opciones Cuenta"));
        panelComboBoxCuenta.add(comboBoxOpcionesCuenta);

        // Crear los botones de "Compra" y "Subasta"
        JButton btnCompra = new JButton("Compra");
        JButton btnSubasta = new JButton("Subasta");

        // Panel para el mensaje, el JComboBox y los botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(lblMensaje);
        panelSuperior.add(panelComboBoxCuenta);
        panelSuperior.add(btnCompra);
        panelSuperior.add(btnSubasta);

        // Agregar el panel superior al norte del BorderLayout
        add(panelSuperior, BorderLayout.NORTH);

        // Acción para el JComboBox
        comboBoxOpcionesCuenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBoxOpcionesCuenta.getSelectedItem();
                if (seleccion.equals("Registrar Pieza Nueva")) {
                    VentanaRegistroPieza ventanaRegistroPieza = new VentanaRegistroPieza();
                    ventanaRegistroPieza.setLocationRelativeTo(null);
                    ventanaRegistroPieza.setVisible(true);
                } else if (seleccion.equals("Ver Saldo Actual")) {
                    // Aquí obtenemos el saldo actual del cliente y lo mostramos en un mensaje
                	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                    Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);
                    double saldo = cliente.getSaldo();
                    JOptionPane.showMessageDialog(null, "Su saldo actual es: " + saldo);}
                
                    else if (seleccion.equals("Ver Piezas Propias")) {
                        // Aquí obtenemos la lista de piezas del cliente
                    	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                        List<Pieza> piezasPropias = GaleriaDeArte.getAdministrador().getPiezasCliente(usuario);

                        if (piezasPropias.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No posee piezas.");
                        } else {
                            // Crear una matriz para los datos de la tabla
                            String[][] datos = new String[piezasPropias.size()][4];
                            for (int i = 0; i < piezasPropias.size(); i++) {
                                Pieza pieza = piezasPropias.get(i);
                                datos[i][0] = pieza.getTitulo();
                                datos[i][1] = pieza.getAutor();
                                datos[i][2] = Integer.toString(pieza.getPrecioCompra());
                                datos[i][3] = pieza.getCodigoPieza();
                            }

                            // Crear un array con los nombres de las columnas
                            String[] columnas = {"Título", "Autor", "Precio Compra", "Código"};

                            // Crear la tabla con los datos y columnas
                            JTable tablaPiezas = new JTable(datos, columnas);

                            // Crear un JScrollPane para la tabla
                            JScrollPane scrollPane = new JScrollPane(tablaPiezas);

                            // Mostrar el diálogo con la tabla
                            JOptionPane.showMessageDialog(null, scrollPane, "Piezas Propias", JOptionPane.PLAIN_MESSAGE);
                        }
                    } else if (seleccion.equals("Consignar pieza Galeria")) {
                        String codigoPieza = JOptionPane.showInputDialog(null, "Ingrese el código de la pieza a consignar:");
                        if (codigoPieza != null) { 
                        	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                            Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);   
                            
                            if (cliente.getPasadas().contains(cliente.buscarPieza(codigoPieza))) {

                                cliente.RealizarConsignacion(codigoPieza);                                
                                JOptionPane.showMessageDialog(null, "Pieza consignada correctamente");
                            } else {
                                JOptionPane.showMessageDialog(null, "El código de pieza ingresado no existe", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se ingresó ningún código de pieza", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (seleccion.equals("Agregar Saldo")) {
                        JFrame frameSaldo = new JFrame("Compra de Pieza");
                        frameSaldo.setLayout(new BorderLayout());

                        // Crear JLabels y JComboBox para los datos de la compra
                        JLabel lblSaldo = new JLabel("Saldo a recargar:");
                        JTextField txtSaldo = new JTextField(20);
                        JLabel lblMetodoPago = new JLabel("Método de Pago:");
                        String[] opcionesMetodoPago = {"PayPal", "PayU"};
                        JComboBox<String> comboBoxMetodoPago = new JComboBox<>(opcionesMetodoPago);
                        JLabel lblNumeroTarjeta = new JLabel("Número de Tarjeta:");
                        JTextField txtNumeroTarjeta = new JTextField(20);
                        JLabel lblCodigoTarjeta = new JLabel("Código de Seguridad:");
                        JTextField txtCodigoTarjeta = new JTextField(20);
                        JLabel lblFechaExpiracion = new JLabel("Fecha de Expiración (MM/YY):");
                        JTextField txtFechaExpiracion = new JTextField(20);

                        // Panel para los campos de la compra
                        JPanel panelCompraPieza = new JPanel();
                        panelCompraPieza.setLayout(new GridLayout(6, 2));
                        panelCompraPieza.add(lblSaldo);
                        panelCompraPieza.add(txtSaldo);
                        panelCompraPieza.add(lblMetodoPago);
                        panelCompraPieza.add(comboBoxMetodoPago);
                        panelCompraPieza.add(lblNumeroTarjeta);
                        panelCompraPieza.add(txtNumeroTarjeta);
                        panelCompraPieza.add(lblCodigoTarjeta);
                        panelCompraPieza.add(txtCodigoTarjeta);
                        panelCompraPieza.add(lblFechaExpiracion);
                        panelCompraPieza.add(txtFechaExpiracion);

                        // Botón para confirmar la compra
                        JButton btnConfirmarCompra = new JButton("Confirmar Compra");
                        frameSaldo.add(panelCompraPieza, BorderLayout.CENTER);
                        frameSaldo.add(btnConfirmarCompra, BorderLayout.SOUTH);

                        // Acción para el botón "Confirmar Compra"
                        btnConfirmarCompra.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Obtener los datos ingresados por el usuario
                                int saldo = Integer.parseInt(txtSaldo.getText());
                                String metodoPago = (String) comboBoxMetodoPago.getSelectedItem();
                                String numeroTarjeta = txtNumeroTarjeta.getText();
                                String codigoTarjeta = txtCodigoTarjeta.getText();
                                String fechaExpiracion = txtFechaExpiracion.getText();

                                // Validar los datos de la tarjeta (solo como ejemplo, debes implementar la validación adecuada)
                                if (numeroTarjeta.isEmpty() || codigoTarjeta.isEmpty() || fechaExpiracion.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos de la tarjeta.");
                                } else {
                                	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                                    Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);
                                    cliente.agregarSaldo(saldo);
                                	
                                    JOptionPane.showMessageDialog(null, "Recarga exitosa.");
                                }
                            }
                        });

                        frameSaldo.setSize(400, 300);
                        frameSaldo.setLocationRelativeTo(null);
                        frameSaldo.setVisible(true);
                    	
                    }
                    else {
                        JOptionPane.showMessageDialog(null, seleccion + " seleccionada");
                    }
                }
            });
        
        btnSubasta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un nuevo JFrame para las opciones de subasta
                JFrame frameSubasta = new JFrame("Opciones de Subasta");
                frameSubasta.setLayout(new BorderLayout());

                // Crear un JComboBox con las opciones y un JPanel para contenerlo
                String[] opcionesSubasta = {"Ingresar a subasta", "Realizar puja"};
                JComboBox<String> comboBoxSubasta = new JComboBox<>(opcionesSubasta);
                JPanel panelOpcionesSubasta = new JPanel();
                panelOpcionesSubasta.add(comboBoxSubasta);

                // Agregar el JPanel al centro del JFrame
                frameSubasta.add(panelOpcionesSubasta, BorderLayout.CENTER);

                // Acción para el JComboBox
                comboBoxSubasta.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String seleccion = (String) comboBoxSubasta.getSelectedItem();
                        if (seleccion.equals("Ingresar a subasta")) {
                        	
                        	if (GaleriaDeArte.getSubasta()==null) {
                                JOptionPane.showMessageDialog(null, "No hay una subasta activa", "Error", JOptionPane.ERROR_MESSAGE);
                        	} else {

                        	
                        	
                        	Administrador administrador =GaleriaDeArte.getAdministrador();
                        	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                            Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);
                            Subasta subasta = GaleriaDeArte.getSubasta();
                        	boolean confirmacion = administrador.verificarUsuarioSubasta(cliente, subasta.obtenerValorMinimoPrecioActual());
                        	if (confirmacion!= false) {
                        		cliente.ingresarASubasta();
                        		JOptionPane.showMessageDialog(null, "Ingreso exitoso a subasta");
                        	} else {
                                JOptionPane.showMessageDialog(null, "No es apto para la subasta", "Error", JOptionPane.ERROR_MESSAGE);
                        	}
                        	
                        	}}
                        
                        
                        else if (seleccion.equals("Realizar puja")) {
                        	
                        	if (GaleriaDeArte.getSubasta()==null) {
                                JOptionPane.showMessageDialog(null, "No hay subasta para pujar.", "Error", JOptionPane.ERROR_MESSAGE);

                        	}else {
                        	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                        	Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);
                        	Subasta subasta = GaleriaDeArte.getSubasta();
                        	Collection<PiezaSubastada> piezasSubasta = subasta.getPiezasSubasta().values();
                        	List<String> titulosPiezasList = new ArrayList<>();
                        	for (PiezaSubastada pieza : piezasSubasta) {
                        	    titulosPiezasList.add(pieza.getPiezaAsociada().getTitulo());
//                        	    + " - Precio actual: " + pieza.getMayorPuja()
                        	}
                        	JComboBox<String> comboBoxPiezas = new JComboBox<>(titulosPiezasList.toArray(new String[0]));

                        	// Mostrar un JOptionPane para seleccionar la pieza
                        	int result = JOptionPane.showConfirmDialog(null, comboBoxPiezas, "Selecciona la pieza para pujar", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        	if (result == JOptionPane.OK_OPTION) {
                        	    // Obtener el título de la pieza seleccionada
                        	    String tituloPiezaSeleccionada = (String) comboBoxPiezas.getSelectedItem();
                        	    String pujaMayor = "" + subasta.getPiezasSubasta().get(tituloPiezaSeleccionada).getMayorPuja();

                        	    // Mostrar un JOptionPane para ingresar la nueva puja
                        	    String valorPujaStr = JOptionPane.showInputDialog(null, "Ingrese el valor de su puja para " + tituloPiezaSeleccionada +", precio minimo: " + pujaMayor, "Nueva Puja", JOptionPane.PLAIN_MESSAGE);
                        	    if (valorPujaStr != null && !valorPujaStr.isEmpty()) {
                        	        int valorPuja = Integer.parseInt(valorPujaStr);

                        	        // Realizar la oferta de subasta
                        	        cliente.realizarOfertaSubasta(tituloPiezaSeleccionada, valorPuja);

                        	    } else {
                        	        JOptionPane.showMessageDialog(null, "Debe ingresar un valor para la puja.", "Error", JOptionPane.ERROR_MESSAGE);
                        	    }
                        	}}}
                    
                }});

                // Botón de salir
                JButton btnSalirSubasta = new JButton("Cerrar");
                frameSubasta.add(btnSalirSubasta, BorderLayout.SOUTH);

                // Acción para el botón "Cerrar"
                btnSalirSubasta.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frameSubasta.dispose(); // Cerrar la ventana de opciones de subasta
                    }
                });

                frameSubasta.setSize(400, 200);
                frameSubasta.setLocationRelativeTo(null);
                frameSubasta.setVisible(true);
            }
        });

        btnCompra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un nuevo JFrame para las opciones de compra
                JFrame frameCompra = new JFrame("Opciones de Compra");
                frameCompra.setLayout(new BorderLayout());

                // Crear un JComboBox con las opciones y un JPanel para contenerlo
                String[] opcionesCompra = {"Ver Piezas en Venta", "Comprar Pieza"};
                JComboBox<String> comboBoxCompra = new JComboBox<>(opcionesCompra);
                JPanel panelOpcionesCompra = new JPanel();
                panelOpcionesCompra.add(comboBoxCompra);

                // Agregar el JPanel al centro del JFrame
                frameCompra.add(panelOpcionesCompra, BorderLayout.CENTER);

                // Acción para el JComboBox
                comboBoxCompra.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String seleccion = (String) comboBoxCompra.getSelectedItem();
                        if (seleccion.equals("Ver Piezas en Venta")) {
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
                        } else if (seleccion.equals("Comprar Pieza")) {
                            // Crear un nuevo JFrame para la compra de la pieza
                            JFrame frameCompraPieza = new JFrame("Compra de Pieza");
                            frameCompraPieza.setLayout(new BorderLayout());

                            // Crear JLabels y JComboBox para los datos de la compra
                            JLabel lblCodigoPieza = new JLabel("Código de la Pieza:");
                            JTextField txtCodigoPieza = new JTextField(20);
                            JLabel lblMetodoPago = new JLabel("Método de Pago:");
                            String[] opcionesMetodoPago = {"PayPal", "PayU"};
                            JComboBox<String> comboBoxMetodoPago = new JComboBox<>(opcionesMetodoPago);
                            JLabel lblNumeroTarjeta = new JLabel("Número de Tarjeta:");
                            JTextField txtNumeroTarjeta = new JTextField(20);
                            JLabel lblCodigoTarjeta = new JLabel("Código de Seguridad:");
                            JTextField txtCodigoTarjeta = new JTextField(20);
                            JLabel lblFechaExpiracion = new JLabel("Fecha de Expiración (MM/YY):");
                            JTextField txtFechaExpiracion = new JTextField(20);

                            // Panel para los campos de la compra
                            JPanel panelCompraPieza = new JPanel();
                            panelCompraPieza.setLayout(new GridLayout(6, 2));
                            panelCompraPieza.add(lblCodigoPieza);
                            panelCompraPieza.add(txtCodigoPieza);
                            panelCompraPieza.add(lblMetodoPago);
                            panelCompraPieza.add(comboBoxMetodoPago);
                            panelCompraPieza.add(lblNumeroTarjeta);
                            panelCompraPieza.add(txtNumeroTarjeta);
                            panelCompraPieza.add(lblCodigoTarjeta);
                            panelCompraPieza.add(txtCodigoTarjeta);
                            panelCompraPieza.add(lblFechaExpiracion);
                            panelCompraPieza.add(txtFechaExpiracion);

                            // Botón para confirmar la compra
                            JButton btnConfirmarCompra = new JButton("Confirmar Compra");
                            frameCompraPieza.add(panelCompraPieza, BorderLayout.CENTER);
                            frameCompraPieza.add(btnConfirmarCompra, BorderLayout.SOUTH);

                            // Acción para el botón "Confirmar Compra"
                            btnConfirmarCompra.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // Obtener los datos ingresados por el usuario
                                    String codigoPieza = txtCodigoPieza.getText();
                                    String metodoPago = (String) comboBoxMetodoPago.getSelectedItem();
                                    String numeroTarjeta = txtNumeroTarjeta.getText();
                                    String codigoTarjeta = txtCodigoTarjeta.getText();
                                    String fechaExpiracion = txtFechaExpiracion.getText();

                                    // Validar los datos de la tarjeta (solo como ejemplo, debes implementar la validación adecuada)
                                    if (numeroTarjeta.isEmpty() || codigoTarjeta.isEmpty() || fechaExpiracion.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos de la tarjeta.");
                                    } else {
                                    	String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                                        Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);
                                        	cliente.realizarOfertaCompraTarjeta(GaleriaDeArte.getInventario().getPiezaTotal(codigoPieza), numeroTarjeta, codigoTarjeta, fechaExpiracion, seleccion);
                                    	
                                    	
                                        JOptionPane.showMessageDialog(null, "Compra realizada con éxito.");
                                    }
                                }
                            });

                            frameCompraPieza.setSize(400, 300);
                            frameCompraPieza.setLocationRelativeTo(null);
                            frameCompraPieza.setVisible(true);
                        }
                    
                    }});

                // Botón de salir
                JButton btnSalirCompra = new JButton("Cerrar");
                frameCompra.add(btnSalirCompra, BorderLayout.SOUTH);

                // Acción para el botón "Cerrar"
                btnSalirCompra.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frameCompra.dispose(); // Cerrar la ventana de opciones de compra
                    }
                });

                frameCompra.setSize(400, 200);
                frameCompra.setLocationRelativeTo(null);
                frameCompra.setVisible(true);
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
        VentanaFuncionesCliente ventana = new VentanaFuncionesCliente();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}