package Persistencia;

import Model.GaleriaDeArte;
import Model.PiezaSubastada;
import Model.Subasta;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import Pieza.Pieza;
import Usuario.Cliente;
import Usuario.Operador;


public class PersistenciaSubastaActual {

	public JSONObject salvarSubasta(GaleriaDeArte galeria) {
	    if (galeria.getSubasta() == null) {
	        return null;
	    }

	    JSONObject jsubasta = new JSONObject();
	    try {
	        JSONArray jpiezas = new JSONArray();
	        for (Pieza pieza : galeria.getSubasta().getPiezasParaSubastar()) {
	            jpiezas.put(pieza.getCodigoPieza());
	        }
	        jsubasta.put("piezas", jpiezas);
	        jsubasta.put("inicializacion", galeria.getSubasta().isInicializacion());

	        JSONObject jpiezasSubasta = new JSONObject();
	        for (PiezaSubastada piezasubasta : galeria.getSubasta().getPiezasSubasta().values()) {
	            JSONObject jPiezaSubastada = new JSONObject();
	            jPiezaSubastada.put("puja", piezasubasta.getMayorPuja());
	            jPiezaSubastada.put("valorInicialSubasta", piezasubasta.getValorInicialSubasta());
	            jPiezaSubastada.put("ganador", piezasubasta.getGanador() != null ? piezasubasta.getGanador().getLogin() : "");
	            jPiezaSubastada.put("valorMinimoVenta", piezasubasta.getValorMinimoVenta());
	            jpiezasSubasta.put(piezasubasta.getPiezaAsociada().getCodigoPieza(), jPiezaSubastada);
	        }
	        jsubasta.put("piezasSubastadas", jpiezasSubasta);

	        JSONArray jclientes = new JSONArray();
	        for (Cliente cliente : galeria.getSubasta().getClientesSubasta()) {
	            jclientes.put(cliente.getLogin());
	        }
	        jsubasta.put("clientes", jclientes);

	        JSONArray jRegistros = new JSONArray();
	        for (String registro : galeria.getSubasta().getRegistrosPujas()) {
	            jRegistros.put(registro);
	        }
	        jsubasta.put("registros", jRegistros);

	        return jsubasta;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public void cargarSubasta(GaleriaDeArte galeria, JSONObject jsubasta) {
	    if (jsubasta == null) {
	        return;
	    }

	    
	    try {
	    	
	        JSONArray jpiezas = jsubasta.getJSONArray("piezas");
	        
	        Operador operador = GaleriaDeArte.getOperador();
	        
	        for (int i = 0; i < jpiezas.length(); i++) {
	            String codigoPieza = jpiezas.getString(i);
	            operador.agregarPiezaSubasta(codigoPieza);
	        }
	        operador.CrearSubasta();

	        Subasta subasta = galeria.getSubasta();

	        List<Cliente> clientes = new ArrayList<>();
	        JSONArray jclientes = jsubasta.getJSONArray("clientes");
	        for (int i = 0; i < jclientes.length(); i++) {
	            String login = jclientes.getString(i);
	            Cliente cliente = (Cliente) galeria.getUsuario(login);
	            if (cliente != null) {
	                clientes.add(cliente);
	            }
	        }
	        subasta.setClientesSubasta(clientes);

	        JSONObject jpiezasSubasta = jsubasta.getJSONObject("piezasSubastadas");
	        for (int i = 0; i < jpiezas.length(); i++) {
	            String codigoPieza = jpiezas.getString(i);
	            JSONObject jpiezaSubasta = jpiezasSubasta.getJSONObject(codigoPieza);

	            int puja = jpiezaSubasta.getInt("puja");
	            int valorInicialSubasta = jpiezaSubasta.getInt("valorInicialSubasta");
	            String login = jpiezaSubasta.getString("ganador");

	            Cliente ganador = login.isEmpty() ? null : (Cliente) galeria.getUsuario(login);
	            int valorMinimoVenta = jpiezaSubasta.getInt("valorMinimoVenta");

	            Pieza pieza = galeria.getInventario().getPiezaTotal(codigoPieza);
	            if (pieza != null) {
	                PiezaSubastada piezasubasta = subasta.getPiezasSubasta().get(pieza.getTitulo());
	                if (piezasubasta != null) {
	                    piezasubasta.setGanador(ganador);
	                    piezasubasta.setMayorPuja(puja);
	                    piezasubasta.setValorInicialSubasta(valorInicialSubasta);
	                    piezasubasta.setValorMinimoVenta(valorMinimoVenta);
	                }
	            }
	        }

	        List<String> registros = new ArrayList<>();
	        JSONArray jRegistros = jsubasta.getJSONArray("registros");
	        for (int i = 0; i < jRegistros.length(); i++) {
	            registros.add(jRegistros.getString(i));
	        }
	        subasta.setRegistrosPujas(registros);

	        boolean inicializacion = jsubasta.getBoolean("inicializacion");
	        subasta.setInicializacion(inicializacion);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}