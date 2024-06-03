package Pieza;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Usuario.Cliente;

public class Pieza {
	
	private String codigoPieza;
	
	private String anoCreacion;
	
	private String autor;
	
	private String lugarCreacion;
	
	private String titulo;
	
	private String fechaConsignacion;
	
	private int precioCompra;
	
	private boolean disponible;
	
	private String lugar;
	
	private Cliente propietario;
	
	private List<Cliente> duenos;
	
	private int precioVenta;
	
	private String fechaVenta;
	
    public Pieza(String codigoPieza, String anoCreacion, String autor, String lugarCreacion,
            String titulo, String fechaConsignacion, int precioCompra, Cliente propietario, String lugar) {
    	
   this.codigoPieza = codigoPieza;
   this.anoCreacion = anoCreacion;
   this.autor = autor;
   this.lugarCreacion = lugarCreacion;
   this.titulo = titulo;
   this.fechaConsignacion = fechaConsignacion;
   this.precioCompra = precioCompra;
   this.disponible = true;
   this.propietario = propietario;
   this.lugar = lugar;
   duenos = new ArrayList<Cliente>();
   duenos.add(propietario);
   precioVenta = 0;
   fechaVenta = null;
}
	
	public String getCodigoPieza() {
		return codigoPieza;
	}

	public String getAnoCreacion() {
		return anoCreacion;
	}

	public String getAutor() {
		return autor;
	}

	public String getLugarCreacion() {
		return lugarCreacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getFechaConsignacion() {
		return fechaConsignacion;
	}

	public int getPrecioCompra() {
		return precioCompra;
	}

	public boolean isDisponible() {
		return disponible;
	}
	
	public Cliente getPropietario() {
		return propietario;
	}
	public void hacerDisponible() {
		this.disponible = true;
	}
	public void hacerNoDisponible() {
		this.disponible = false;
	}
	public String getLugar() {
		return lugar;
	}
	
	
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public boolean verificarConsignacionPieza() {
	    // Obtener la fecha actual
	    LocalDate fechaActual = LocalDate.now();
	    
	    // Convertir la fecha de consignación a LocalDate (suponiendo que fechaConsignacion es una cadena en formato "yyyy-MM-dd")
	    LocalDate fechaConsignacionLocalDate = LocalDate.parse(fechaConsignacion);
	    
	    // Devolver true si la fecha actual es anterior o igual a la fecha de consignación
	    // Devolver false si la fecha actual es posterior a la fecha de consignación
	    return !fechaActual.isAfter(fechaConsignacionLocalDate);
	}
	
	
	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}

	public List<Cliente> getDuenos() {
		return duenos;
	}

	public void setPrecioVenta(int precioVenta) {
		this.precioVenta = precioVenta;
	}

	public void setFechaVenta(String fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public int getPrecioVenta() {
		return precioVenta;
	}

	public String getFechaVenta() {
		return fechaVenta;
	}
	
	
}
