package Persistencia;

import Model.Compra;
import Model.GaleriaDeArte;
import Model.Inventario;
import Pieza.Pieza;
import Usuario.Administrador;
import Usuario.Cajero;
import Usuario.Cliente;
import Usuario.Operador;
import Usuario.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class PersistenciaPiezas {

	public JSONObject salvarPiezas(GaleriaDeArte galeria) 
	{
		
		Inventario inventario = galeria.getInventario();
		JSONObject jInventario = new JSONObject();
		
		List<Pieza> piezasBodega = inventario.getPiezasBodega();
		JSONArray jPiezasBodega = crearJSONArrayPiezasDesdeListaPiezas(piezasBodega);
		
		List<Pieza> piezasColeccion = inventario.getPiezasColeccioon();
		JSONArray jPiezasColeccion = crearJSONArrayPiezasDesdeListaPiezas(piezasColeccion);
		
		List<Pieza> piezasPasadas = inventario.getPiezasPasadas();
		JSONArray jPiezasPasadas = crearJSONArrayPiezasDesdeListaPiezas(piezasPasadas);
		
		jInventario.put("piezasBodega", jPiezasBodega);
		jInventario.put("piezasColeccion", jPiezasColeccion);
		jInventario.put("piezasPasadas", jPiezasPasadas);
		
		return jInventario;
		
	}
	
	public JSONArray crearJSONArrayPiezasDesdeListaPiezas(List<Pieza> lista ) 
	{
        JSONArray jPiezas = new JSONArray();
        
        for (Pieza pieza : lista) {
        	JSONObject jPieza = new JSONObject();
        	
        	String anoCreacion = pieza.getAnoCreacion();
        	String autor = pieza.getAutor();
        	String codigo = pieza.getCodigoPieza();
        	String consignacion = pieza.getFechaConsignacion();
        	String lugar = pieza.getLugar();
        	String lugarCreacion = pieza.getLugarCreacion();
        	String titulo = pieza.getTitulo();
        	int precioCompra = pieza.getPrecioCompra();
        	String propietario = pieza.getPropietario().getLogin();
        	
        	jPieza.put("anoCreacion", anoCreacion);
        	jPieza.put("autor", autor);
        	jPieza.put("codigo", codigo);
        	jPieza.put("consignacion", consignacion);
        	jPieza.put("lugar", lugar);
        	jPieza.put("lugarCreacion", lugarCreacion);
        	jPieza.put("titulo", titulo);
        	jPieza.put("precioCompra", precioCompra);
        	jPieza.put("propietario", propietario);
        	

        	jPiezas.put(jPieza);
        }
        
        return jPiezas;
    }
	
	
	public void cargarPiezas(JSONObject jInventario, GaleriaDeArte galeria) {
		
		JSONArray jPiezasColeccion = jInventario.getJSONArray("piezasColeccion");
		List <Pieza> piezasColeccion = new ArrayList<Pieza>();  
		for (int i = 0; i < jPiezasColeccion.length(); i++) {
			JSONObject jPieza = jPiezasColeccion.getJSONObject(i);
			
        	String titulo = jPieza.getString("titulo");
       	 	
        	Pieza pieza = PersistenciaUsuarios.getPieza(titulo);
			piezasColeccion.add(pieza);
        	
		}
		
		
		JSONArray jPiezasBodega = jInventario.getJSONArray("piezasBodega");
		List <Pieza> piezasBodega = new ArrayList<Pieza>();  
		for (int i = 0; i < jPiezasBodega.length(); i++) 
		{
			JSONObject jPieza = jPiezasBodega.getJSONObject(i);
			
        	String titulo = jPieza.getString("titulo");

        	Pieza pieza = PersistenciaUsuarios.getPieza(titulo);
			piezasBodega.add(pieza);
					
		}
		
		JSONArray jPiezasPasado = jInventario.getJSONArray("piezasPasadas");
		List <Pieza> piezasPasado = new ArrayList<Pieza>();  
		for (int i = 0; i < jPiezasPasado.length(); i++) 
		{
			JSONObject jPieza = jPiezasPasado.getJSONObject(i);
			

        	String titulo = jPieza.getString("titulo");

        	       	
        	Pieza pieza = PersistenciaUsuarios.getPieza(titulo);
			piezasPasado.add(pieza);
		}
		
		Inventario inventario = new Inventario();
		inventario.setPiezasBodega(piezasBodega);
		inventario.setPiezasColeccioon(piezasColeccion);
		inventario.setPiezasPasadas(piezasPasado);
		
		galeria.setInventario(inventario);
  
	}
}


