package PasarelasPago;



import java.io.IOException;

public interface PasarelaPago {
	
    
    boolean validarPago(String idTransaccion) throws IOException;
    
    void cancelarPago(String idTransaccion) throws IOException;

	void procesarPago(double monto, String moneda, String numeroTarjeta, String csv, String fechaExpiracion)
			throws IOException;
}