package InterfazGraficaPrincipal;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelRegistroOperador extends JPanel
{
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnRegistrar;
    private ActionListener listener;

    public PanelRegistroOperador()
    {
        setLayout(new GridLayout(3, 2));
        setBorder(new TitledBorder("Inicio de sesion de Operador"));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtContrasena = new JPasswordField();
        btnRegistrar = new JButton("Ingresar");

        add(lblUsuario);
        add(txtUsuario);
        add(lblContrasena);
        add(txtContrasena);
        add(new JLabel());  // Empty label for alignment
        add(btnRegistrar);

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

    public JButton getBtnRegistrar()
    {
        return btnRegistrar;
    }

    public String getUsuario()
    {
        return txtUsuario.getText();
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