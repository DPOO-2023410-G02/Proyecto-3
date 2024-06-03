package Model;

import Pieza.Pieza;
import Usuario.Administrador;
import Usuario.Cajero;
import Usuario.Cliente;

import java.io.IOException;
import java.time.LocalDate;

import Model.GaleriaDeArte;
import PasarelasPago.GestorPasarelas;
import PasarelasPago.PasarelaPago;

public class Compra {
	
	private Administrador administrador;
	
	private Cajero cajero;
	
	private int precio;
	
	private Pieza pieza;

	private String fecha;
	
	
	public Compra(Pieza piezaOfertada) {
		
		administrador = GaleriaDeArte.getAdministrador();
		cajero = GaleriaDeArte.getCajero();
		precio = piezaOfertada.getPrecioCompra();
		pieza = piezaOfertada;
		LocalDate fechaActual = LocalDate.now();
		fecha = fechaActual.toString();
	}
	
	
	public void registrarCompra(Pieza piezaOfertada, Cliente comprador) {		
		
		administrador.hacerNoDisponible(piezaOfertada);
        
        boolean llave1 = administrador.verificarUsuario(comprador, piezaOfertada); 

        if(llave1) {
        	
        	administrador.eliminarPiezaInventario(piezaOfertada.getLugar(), piezaOfertada);
        	
        	cajero.registarPago(comprador, piezaOfertada); 
        	
        	comprador.anadirCompras(this);
         	
        	administrador.eliminarPiezaPropietario(piezaOfertada);
        	
        	comprador.añadirPiezas(piezaOfertada);
        	
        	piezaOfertada.getDuenos().add(comprador);
        	
        	piezaOfertada.setPrecioVenta(precio);
        	
        	LocalDate fechaActual = LocalDate.now();
        	piezaOfertada.setFechaVenta(fechaActual.toString());
        	
        }
        else {
        	piezaOfertada.hacerDisponible();
        }
	}


	public int getPrecio() {
		return precio;
	}


	public Pieza getPieza() {
		return pieza;
	}


	public String getFecha() {
		return fecha;
	}


	public void registrarCompraTarjeta(Pieza piezaOfertada, Cliente comprador, String numero, String csv, String fecha2, String pasarela) {
	administrador.hacerNoDisponible(piezaOfertada);
        
        boolean llave1 = administrador.verificarUsuario(comprador, piezaOfertada); 

        if(true) {
        	
        	administrador.eliminarPiezaInventario(piezaOfertada.getLugar(), piezaOfertada);
        	
        	//cajero.registarPago(comprador, piezaOfertada); 
        	GestorPasarelas gestor = GaleriaDeArte.getGestorPasarelasPago();
        	
        	if (pasarela.equals("PayPal")) {
        		
        		PasarelaPago paypal = gestor.getPasarelaPayPal();
        		try {
					paypal.procesarPago(piezaOfertada.getPrecioCompra(), "USD", numero, csv, fecha2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}else if(pasarela.equals("PayU")){
        		PasarelaPago payu = gestor.getPasarelaStripe();  
        		try {
					payu.procesarPago(piezaOfertada.getPrecioCompra(), "USD", numero, csv, fecha2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        	comprador.anadirCompras(this);
         	
        	administrador.eliminarPiezaPropietario(piezaOfertada);
        	
        	comprador.añadirPiezas(piezaOfertada);
        	
        	piezaOfertada.getDuenos().add(comprador);
        	
        	piezaOfertada.setPrecioVenta(precio);
        	
        	LocalDate fechaActual = LocalDate.now();
        	piezaOfertada.setFechaVenta(fechaActual.toString());
        	
        }
        else {
        	piezaOfertada.hacerDisponible();
        }
		
	}
	
}
