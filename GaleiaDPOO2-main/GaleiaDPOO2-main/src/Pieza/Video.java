package Pieza;

import java.util.List;

import Usuario.Cliente;

public class Video extends Pieza {

	public static final String VIDEO = "Video";
	private List<String> caracteristicas;
	
	public Video(String codigoPieza, String anoCreacion, String autor, String lugarCreacion, String titulo,
			String fechaConsignacion, int precioCompra, Cliente propietario, String lugar, List<String> caracteristicasPieza) {
		super(codigoPieza, anoCreacion, autor, lugarCreacion, titulo, fechaConsignacion, precioCompra, propietario, lugar);
		caracteristicas = caracteristicasPieza;
		
	}

	
	

}
