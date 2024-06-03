package Model;

import java.util.ArrayList;
import java.util.List;

import Pieza.Pieza;

public class Artista {

	private List<Pieza> piezas;
	private String nombre;
	
	public Artista(String nombre) {
		this.nombre = nombre;
		piezas  = new ArrayList<Pieza>();
	}
	
	public void agregarPieza(Pieza pieza) {
		
		piezas.add(pieza);
	}

	public List<Pieza> getPiezas() {
		return piezas;
	}

	public String getNombre() {
		return nombre;
	}
	
	
}
