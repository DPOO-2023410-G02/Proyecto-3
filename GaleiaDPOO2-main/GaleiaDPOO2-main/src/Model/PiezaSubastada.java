package Model;

import Pieza.Pieza;
import Usuario.Cliente;

public class PiezaSubastada {
	
	private int valorInicialSubasta;
	private int valorMinimoVenta;
	private int mayorPuja;
	private Cliente ganador;
	private Pieza piezaAsociada;
	
	public PiezaSubastada(int valorInicial, int valorMinimo, Pieza pieza)
	{
		
		valorInicialSubasta = valorInicial;
		valorMinimoVenta = valorMinimo;
		mayorPuja = 0;
		ganador = null;
		piezaAsociada = pieza;
		
	}


	public int getValorInicialSubasta() {
		return valorInicialSubasta;
	}

	public int getValorMinimoVenta() {
		return valorMinimoVenta;
	}

	public int getMayorPuja() {
		return mayorPuja;
	}

	public Cliente getGanador() {
		return ganador;
	}

	public void setMayorPuja(int mayorPuja) {
		this.mayorPuja = mayorPuja;
	}

	public void setGanador(Cliente ganador) {
		this.ganador = ganador;
	}

	public Pieza getPiezaAsociada() {
		return piezaAsociada;
	}


	public void setValorInicialSubasta(int valorInicialSubasta) {
		this.valorInicialSubasta = valorInicialSubasta;
	}


	public void setValorMinimoVenta(int valorMinimoVenta) {
		this.valorMinimoVenta = valorMinimoVenta;
	}


	public void setPiezaAsociada(Pieza piezaAsociada) {
		this.piezaAsociada = piezaAsociada;
	}
	
	
	
}
