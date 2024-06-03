package Usuario;
import java.util.ArrayList;
import java.util.List;

import Model.GaleriaDeArte;
import Model.Inventario;
import Model.Subasta;
import Pieza.Pieza;


public class Operador extends Usuario {
	
	public static final String OPERADOR = "Operador";
	private List<Pieza> piezasSubasta;
	
	
    public Operador(String password, String login, String nombre) {
        super(password, login, nombre);
        piezasSubasta = new ArrayList<Pieza>();   
    }
	
	
    @Override
    public String getTipoUsuario() {
        return OPERADOR;
    }
    
    public void CrearSubasta() 
    {
    	Subasta subasta = new Subasta(piezasSubasta);
    	GaleriaDeArte.setSubasta(subasta);
    }
    
    public void iniciarSubasta() 
    {
    	GaleriaDeArte.getSubasta().inicializarSubasta();
    }
    
    public String registrarPujaCliente(String titulo, int puja, Cliente cliente) {
        String resultado = titulo + "_" + puja + "_" + cliente.getNombre();
        return resultado;
    }

    
    public void finalizarSubastaOperador() {
    	Subasta subasta = GaleriaDeArte.getSubasta();
    	subasta.finalizarSubasta();
    	piezasSubasta.clear();
    	
    }
    
    public void agregarPiezaSubasta(String codigoPieza) {
    	Inventario piezas = GaleriaDeArte.getInventario();
    	Pieza pieza = piezas.getPiezaTotal(codigoPieza);
    	piezasSubasta.add(pieza);
    	
    }


	public List<Pieza> getPiezasSubasta() {
		return piezasSubasta;
	}
    
    
}
