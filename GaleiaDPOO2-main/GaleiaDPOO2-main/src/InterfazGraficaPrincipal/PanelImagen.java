package InterfazGraficaPrincipal;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelImagen extends JPanel
{
    private JLabel lblImagen;
    
    public PanelImagen()
    {
        lblImagen= new JLabel("" );
        add(lblImagen);
        
        ImageIcon fotico = new ImageIcon("./data/imagenes/titulo.jpg");
        lblImagen.setIcon( fotico );
        
    }
}
