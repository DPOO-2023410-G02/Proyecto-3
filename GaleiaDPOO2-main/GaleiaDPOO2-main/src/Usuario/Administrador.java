package Usuario;

import java.util.List;

import Model.Compra;
import Model.GaleriaDeArte;
import Model.Inventario;
import Pieza.Pieza;

public class Administrador extends Usuario {
	
	public static final String ADMINISTRADOR = "Administrador";
	
    public Administrador(String password, String login, String nombre) {
        super(password, login, nombre);
    }
    
    @Override
    public String getTipoUsuario() {
        return ADMINISTRADOR;
    }
    
    public boolean verificarUsuario(Cliente cliente, Pieza piezaOfertada) {
    	
    	int precioOferta = piezaOfertada.getPrecioCompra();
    	if(precioOferta > cliente.getValorMaximo()) {
    		
    		int saldo = cliente.getSaldo();	
    		if (precioOferta > saldo)
    			
    			return false;
    		else {
    			cliente.setValorMaximo(saldo);
    			return true;
    		}
    	}
    	else {
    		
    		return true;
    	}
    	
    }
    
    public void  eliminarPiezaInventario(String lugar, Pieza piezaOfertada) {
    	Inventario inventario = GaleriaDeArte.getInventario();
    	inventario.eliminarPieza(piezaOfertada.getLugar(), piezaOfertada);
    }
    public void eliminarPiezaPropietario(Pieza piezaOfertada) {
    	Cliente propietario = piezaOfertada.getPropietario();
    	propietario.eliminarPieza(piezaOfertada);
    }
    public void hacerNoDisponible(Pieza piezaOfertada) {
    	piezaOfertada.hacerNoDisponible();
    }

    
    // SUBASTA
    
	public boolean verificarUsuarioSubasta(Cliente cliente, int valorMinimo) {
		
		
    	if(valorMinimo > cliente.getValorMaximo()) {
    		
    		int saldo = cliente.getSaldo();	
    		if (valorMinimo > saldo)
    			
    			return false;
    		else {
    			cliente.setValorMaximo(saldo);
    			return true;
    		}
    	}
    	else {
    		
    		return true;
    	}
	}
	
	
	
	// HISTORIA DE UN CLIENTE
	
	public List<Compra> getHistorial(String login){
		
		Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(login);
		List<Compra> compras = cliente.getCompras();
		
		return compras;
		
	}
	
	public List<Pieza> getPiezasCliente(String login){
		Cliente cliente = (Cliente) GaleriaDeArte.getUsuario(login);
		List<Pieza> piezasCliente = cliente.getPasadas();
		return piezasCliente;
	}
	
	public int getValorColeccion(String login) {
		
		List<Pieza> piezasCliente = getPiezasCliente(login);
		int suma = 0;
		for (Pieza pieza: piezasCliente) {
			suma += pieza.getPrecioCompra();
		}
		return suma;
	}
	
}
