package PasarelasPago;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayPal implements PasarelaPago {

    private Map<String, String> transacciones = new HashMap<>();
    private static final String NOMBRE_ARCHIVO = "paypal_transacciones.txt";

    @Override
    public void procesarPago(double monto, String moneda, String numeroTarjeta, String csv, String fechaExpiracion) throws IOException {
        String idTransaccion = generarIdTransaccion();
        String detalleTransaccion = "Procesado: ID=" + idTransaccion + ", Monto=" + monto + ", Moneda=" + moneda +
                                    ", Número de Tarjeta=" + numeroTarjeta + ", CSV=" + csv +
                                    ", Fecha de Expiración=" + fechaExpiracion + ", Fecha Actual=" + obtenerFechaActual();
        transacciones.put(idTransaccion, detalleTransaccion);
        escribirEnArchivo(detalleTransaccion);
        
    }

    @Override
    public boolean validarPago(String idTransaccion) throws IOException {
        String transaccion = transacciones.get(idTransaccion);
        if (transaccion != null) {
            String detalleTransaccion = "Validado: ID=" + idTransaccion;
            escribirEnArchivo(detalleTransaccion);
            System.out.println(detalleTransaccion);
            return true;
        }
        return false;
    }

    @Override
    public void cancelarPago(String idTransaccion) throws IOException {
        String transaccion = transacciones.get(idTransaccion);
        if (transaccion != null) {
            String detalleTransaccion = "Cancelado: ID=" + idTransaccion;
            escribirEnArchivo(detalleTransaccion);
            System.out.println(detalleTransaccion);
        }
    }

    private String generarIdTransaccion() {
        return "PP-" + System.currentTimeMillis();
    }

    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    private void escribirEnArchivo(String texto) throws IOException {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(texto);
        }
    }
}
