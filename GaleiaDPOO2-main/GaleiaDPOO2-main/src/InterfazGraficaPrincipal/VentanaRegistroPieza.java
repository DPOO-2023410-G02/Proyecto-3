package InterfazGraficaPrincipal;

import javax.swing.*;

import Model.GaleriaDeArte;
import Usuario.Cliente;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaRegistroPieza extends JFrame {
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAño;
    private JTextField txtOrigen;
    private JTextField txtPrecio;
    private JTextField txtDescripcion;
    private JButton btnRegistrar;
//    private PanelRegistroCliente panelRegistroCliente;
    
    public VentanaRegistroPieza() {
        setSize(400, 400);
        setTitle("Registrar Pieza Nueva");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        JLabel lblTitulo = new JLabel("Titulo:");
        txtTitulo = new JTextField();

        JLabel lblAutor = new JLabel("Autor:");
        txtAutor = new JTextField();

        JLabel lblAño = new JLabel("Año:");
        txtAño = new JTextField();

        JLabel lblOrigen = new JLabel("Lugar Creacion:");
        txtOrigen = new JTextField();

        JLabel lblPrecio = new JLabel("Precio:");
        txtPrecio = new JTextField();

        JLabel lblDescripcion = new JLabel("Descripción:");
        txtDescripcion = new JTextField();
        
//        panelRegistroCliente = new PanelRegistroCliente();
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para guardar la pieza
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                String año = txtAño.getText();
                String lugarCreacion = txtOrigen.getText();
                int precio = Integer.parseInt(txtPrecio.getText());
                String descripcion = txtDescripcion.getText();
                String usuario = VentanaPrincipal.getPanelRegistroCliente().getUsuario();
                Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(usuario);
                cliente.registrarPieza(String.valueOf(año), autor, lugarCreacion, titulo, precio);
                
                
                // Aquí puedes agregar la lógica para almacenar esta información
                JOptionPane.showMessageDialog(null, "Pieza registrada con éxito!");

                // Cierra la ventana de registro de piezas
                dispose();
            }
        });

        add(lblTitulo);
        add(txtTitulo);
        add(lblAutor);
        add(txtAutor);
        add(lblAño);
        add(txtAño);
        add(lblOrigen);
        add(txtOrigen);
        add(lblPrecio);
        add(txtPrecio);
        add(lblDescripcion);
        add(txtDescripcion);
        add(new JLabel()); // Espacio vacío para alineación
        add(btnRegistrar);
    }
}