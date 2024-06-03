package InterfazGraficaPrincipal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.GaleriaDeArte;
import Persistencia.PersistenciaPiezas;
import Persistencia.PersistenciaSubastaActual;
import Persistencia.PersistenciaSubastas;
import Persistencia.PersistenciaUsuarios;
import Usuario.Cliente;

public class VentanaPrincipal extends JFrame {
    private PanelOpciones panelOpc;
    private PanelImagen panelImg;
    private PanelRegistroAdmin panelRegistroAdmin;
    private static PanelRegistroCliente panelRegistroCliente;

    private PanelRegistroOperador panelRegistroOperador;
    private PanelInicioSesion panelInicioSesion; // Panel con los tres botones
    private JPanel panelContenedor; // Contenedor para los paneles de registro
    private CardLayout cardLayout; // CardLayout para alternar entre paneles
    private static GaleriaDeArte modelo;

    public VentanaPrincipal() {
        setSize(750, 650);
        setTitle("Galeria De Arte");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            modelo = new GaleriaDeArte();
            modelo.AgregarAdministrador("1234", "admin23", "Camilo");
            modelo.AgregarCajero("1234", "cajero23", "Ernesto");
            modelo.AgregarOperador("1234", "operador23", "Arturo");
            cargarDatos(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el museo", "Error", JOptionPane.ERROR_MESSAGE);
        }

        panelImg = new PanelImagen();
        add(panelImg, BorderLayout.NORTH);

        panelOpc = new PanelOpciones(this);
        add(panelOpc, BorderLayout.SOUTH);

        // Crear el contenedor con CardLayout
        panelContenedor = new JPanel();
        cardLayout = new CardLayout();
        panelContenedor.setLayout(cardLayout);

        // Crear los paneles de registro
        panelRegistroAdmin = new PanelRegistroAdmin();
        panelRegistroAdmin.setActionListener(e -> registrarAdministrador());

        panelRegistroOperador = new PanelRegistroOperador();
        panelRegistroOperador.setActionListener(e -> registrarOperador());
        
        setPanelRegistroCliente(new PanelRegistroCliente());
        getPanelRegistroCliente().getBtnInicio().addActionListener(e -> ingresarCliente());
        getPanelRegistroCliente().getBtnRegistrar().addActionListener(e -> registrarCliente());
        

        panelInicioSesion = new PanelInicioSesion(this); // Inicializar el nuevo panel

        // Añadir los paneles al contenedor
        panelContenedor.add(panelInicioSesion, "PanelRegistro");
        panelContenedor.add(panelRegistroAdmin, "PanelRegistroAdmin");
        panelContenedor.add(panelRegistroOperador, "PanelRegistroOperador");
        panelContenedor.add(getPanelRegistroCliente(), "PanelRegistroCliente");


        // Añadir el contenedor al centro del BorderLayout
        add(panelContenedor, BorderLayout.CENTER);

        // Mostrar el panel de registro por defecto
        cardLayout.show(panelContenedor, "PanelRegistro");
    }

    public void mostrarPanelRegistroAdmin() {
        cardLayout.show(panelContenedor, "PanelRegistroAdmin");
    }

    public void mostrarPanelRegistro() {
        cardLayout.show(panelContenedor, "PanelRegistro");
    }

    public void mostrarPanelRegistroOperador() {
        cardLayout.show(panelContenedor, "PanelRegistroOperador");
    }
    
    public void mostrarPanelRegistroCliente() {
        cardLayout.show(panelContenedor, "PanelRegistroCliente");
    }

    private void registrarOperador() {
        String usuario = panelRegistroOperador.getUsuario();
        String contrasena = panelRegistroOperador.getContrasena();

        if (modelo.iniciarSesionOperador(usuario, contrasena)) {
            JOptionPane.showMessageDialog(this, "Ingreso exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            VentanaFuncionesOperador ventanaFuncionesOperador = new VentanaFuncionesOperador();
            ventanaFuncionesOperador.setLocationRelativeTo(null);
            ventanaFuncionesOperador.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarAdministrador() {
        String usuario = panelRegistroAdmin.getUsuario();
        String contrasena = panelRegistroAdmin.getContrasena();

        if (modelo.iniciarSesionAdmin(usuario, contrasena)) {
            JOptionPane.showMessageDialog(this, "Ingreso exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            VentanaFuncionesAdmin ventanaFuncionesAdmin = new VentanaFuncionesAdmin();
            ventanaFuncionesAdmin.setLocationRelativeTo(null);
            ventanaFuncionesAdmin.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ingresarCliente() {
        String usuario = getPanelRegistroCliente().getUsuario();
        String contrasena = getPanelRegistroCliente().getContrasena();

        if (modelo.iniciarSesionCliente(usuario, contrasena)) {
            JOptionPane.showMessageDialog(this, "Ingreso exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            VentanaFuncionesCliente ventanaFuncionesCliente = new VentanaFuncionesCliente();
            ventanaFuncionesCliente.setLocationRelativeTo(null);
            ventanaFuncionesCliente.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarCliente() {
    	String usuario = getPanelRegistroCliente().getUsuario();
        String contrasena = getPanelRegistroCliente().getContrasena();
        String nombre = getPanelRegistroCliente().getNombre();
        modelo.AgregarUsuario(new Cliente(contrasena, usuario, nombre));
        JOptionPane.showMessageDialog(this, "Usuario registrado", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        }

   

    public static void main(String[] args) {
        VentanaPrincipal principal = new VentanaPrincipal();
        principal.setLocationRelativeTo(null);
        principal.setVisible(true);
    }

	public static PanelRegistroCliente getPanelRegistroCliente() {
		return panelRegistroCliente;
	}

	public void setPanelRegistroCliente(PanelRegistroCliente panelRegistroCliente) {
		this.panelRegistroCliente = panelRegistroCliente;
	}
	
    private static void cargarDatos(GaleriaDeArte galeria) {
        try {
            // Cargar usuarios
            String usuariosContent = new String(Files.readAllBytes(Paths.get("usuarios.json")));
            JSONArray jUsuarios = new JSONArray(usuariosContent);
            new PersistenciaUsuarios().CargarUsuarios(galeria, jUsuarios);

            // Cargar piezas
            String piezasContent = new String(Files.readAllBytes(Paths.get("piezas.json")));
            JSONObject jPiezas = new JSONObject(piezasContent);
            new PersistenciaPiezas().cargarPiezas(jPiezas, galeria);

            // Cargar subastas
            String subastasContent = new String(Files.readAllBytes(Paths.get("subastas.json")));
            JSONArray jSubastas = new JSONArray(subastasContent);
            List<List<String>> subastas = new PersistenciaSubastas().cargarSubastas(jSubastas);
            galeria.setRegistrosPorSubasta(subastas);

            // Cargar subasta actual
            String subastaActualContent = new String(Files.readAllBytes(Paths.get("subastaActual.json")));
            if (!subastaActualContent.isEmpty()) {
                JSONObject jSubastaActual = new JSONObject(subastaActualContent);
                new PersistenciaSubastaActual().cargarSubasta(galeria, jSubastaActual);
            }

            System.out.println("Datos cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
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

            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

	public static GaleriaDeArte getModelo() {
		// TODO Auto-generated method stub
		return modelo;
	}
}