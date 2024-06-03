package InterfazGraficaPrincipal;
import javax.swing.*;

import Model.GaleriaDeArte;
import Model.Inventario;
import Pieza.Pieza;
import Usuario.Cliente;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VentasHeatmap extends JPanel {

    private Map<LocalDate, Integer> ventasPorDia;
    private int maxVentasEnUnDia;

    public VentasHeatmap(Map<LocalDate, Integer> ventasPorDia) {
        this.ventasPorDia = ventasPorDia;
        this.maxVentasEnUnDia = ventasPorDia.values().stream().mapToInt(Integer::intValue).max().orElse(1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 20;
        int padding = 5;
        int rows = 7;
        int cols = 53; // Aproximadamente 52 semanas en un año más unos días extra

        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31);

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate firstDayOfYear = startDate.with(weekFields.dayOfWeek(), 1);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int weekOfYear = date.get(weekFields.weekOfYear());
            // Obtener el día de la semana (0 = Lunes, 1 = Martes, ..., 6 = Domingo)
            int dayOfWeek = date.getDayOfWeek().getValue() - 1;

            // Ajustar los índices para que se inicien en 0
            int col = weekOfYear - 1;
            int row = dayOfWeek - 1;
            row = (dayOfWeek == -1) ? rows - 1 : dayOfWeek;
            
            int ventasEnEsteDia = ventasPorDia.getOrDefault(date, 0);
            double intensity = (double) ventasEnEsteDia / 10;
            int colorValue = (int) (255 * (1.0 - intensity));
            g.setColor(new Color(colorValue, colorValue, colorValue));
            g.fillRect(col * (cellSize + padding), row * (cellSize + padding), cellSize, cellSize);
        }
    }
    

}