package InterfazGraficaPrincipal;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Usuario.Cliente;

public class PanelRegistroCliente  extends JPanel
{
    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JPasswordField txtContrasena;
    private JButton btnInicio;
    private JButton btnRegistrar;
    private ActionListener listener;
    
    public PanelRegistroCliente()
    {
        setLayout(new GridLayout(5, 2));
        setBorder(new TitledBorder("Inicio de sesion de Cliente (si va a iniciar sesion no es necesario llenar la casilla de nombre)"));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();
        btnInicio = new JButton("Ingresar");
        btnRegistrar = new JButton("Registrarse");

        add(lblUsuario);
        add(txtUsuario);
        add(lblNombre);
        add(txtNombre);
        add(lblContrasena);
        add(txtContrasena);
        add(new JLabel());  // Empty label for alignment
        add(btnInicio);
        add(new JLabel());  // Empty label for alignment
        add(btnRegistrar);

        btnInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (listener != null)
                {
                    listener.actionPerformed(e);
                }
            }
        });
        
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (listener != null)
                {
                    listener.actionPerformed(e);
                }
            }
        });
    }

    public JButton getBtnInicio()
    {
        return btnInicio;
    }
    public JButton getBtnRegistrar()
    {
        return btnRegistrar;
    }

    public String getUsuario()
    {
        return txtUsuario.getText();
    }
    
    public String getNombre()
    {
        return txtNombre.getText();
    }

    public String getContrasena()
    {
        return new String(txtContrasena.getPassword());
    }

    public void setActionListener(ActionListener listener)
    {
        this.listener = listener;
    }
}