package Usuario;

import Pieza.Pieza;

public class Cajero extends Usuario {
	
	public static final String CAJERO = "Cajero";
	
    public Cajero(String password, String login, String nombre) {
        super(password, login, nombre);
    }
    @Override
    public String getTipoUsuario() {
        return CAJERO;
    }
    
    public void registarPago(Cliente comprador, Pieza piezaOfertada){
    	
    	comprador.setSaldo(comprador.getSaldo()-piezaOfertada.getPrecioCompra());
    }
    
    public void registarPagoSubasta(Cliente comprador, int valorPago){
    	
    	comprador.setSaldo(comprador.getSaldo()- valorPago);
    }
    
}
