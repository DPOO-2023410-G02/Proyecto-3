package InterfazGraficaPrincipal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Model.GaleriaDeArte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Map;

public class PanelOpciones extends JPanel implements ActionListener {
    private JButton optReg; // Nuevo botón para mostrar el panel de tres botones
    private JButton btnVentasHeatmap	; // Nuevo botón para mostrar el panel de tres botones

    private VentanaPrincipal ventanaPrincipal;

    public PanelOpciones(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;

        setLayout(new GridLayout(1, 2)); // GridLayout con una columna para el botón
        setBorder(new TitledBorder("Opciones"));

        optReg = new JButton("Iniciar Sesion"); // Texto del botón para mostrar el panel de tres botones
        optReg.setBackground(new Color(255, 255, 255));
        optReg.setForeground(Color.RED);
        optReg.addActionListener(this);
        add(optReg);
        
        btnVentasHeatmap = new JButton("Ventas Del Año"); // Texto del botón para mostrar el panel de tres botones
        optReg.setBackground(new Color(255, 255, 255));
        optReg.setForeground(Color.RED);
        btnVentasHeatmap.addActionListener(new ActionListener() {
            
            // Crear ventana y mostrar heatmap
            public void actionPerformed(ActionEvent e) {
            	if (GaleriaDeArte.getInventario() == null) {
            	    JOptionPane.showMessageDialog(null, "La galería no posee piezas en inventario.", "Error", JOptionPane.ERROR_MESSAGE);
            	} else {
            	Map<LocalDate, Integer> ventasPorDia = GaleriaDeArte.contarVentasPorDia();
                // Crear la ventana VentasHeatmap y mostrarla
                JFrame frame = new JFrame("Ventas Anuales");
                VentasHeatmap heatmapPanel = new VentasHeatmap(ventasPorDia);
                heatmapPanel.setPreferredSize(new Dimension(1200, 600));
                frame.add(heatmapPanel);
                frame.pack();
                frame.setLocationRelativeTo(null); // Centrar la ventana en pantalla
                frame.setVisible(true);
            }}
        });        
        add(btnVentasHeatmap);
    }{
    // Acción para el botón VentasHeatmap
    	
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == optReg) {
            ventanaPrincipal.mostrarPanelRegistro(); // Llama al método para mostrar el panel de tres botones
        }
    }
}