package Test;

import Model.GaleriaDeArte;
import Usuario.Administrador;
import Usuario.Cliente;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import Model.GaleriaDeArte;
import Model.Inventario;
import Model.PiezaSubastada;
import Persistencia.PersistenciaUsuarios;
import Pieza.Pieza;
import Usuario.Cliente;
import Usuario.Operador;
import Usuario.Usuario;
import Model.Subasta;
public class TestSubasta {

	public static void main(String[] args) {
		
	   	//Creacion Galeria 
        GaleriaDeArte galeria = new GaleriaDeArte();
        
        //Creacion Empleados
        galeria.AgregarAdministrador("Qwer1234", "Camilo2816", "Camilo Sanchez");
        galeria.AgregarCajero("Qwer1234", "Camilo2816", "Camilo Sanchez");
        galeria.AgregarOperador("Qwer1234", "Camilo2816", "Camilo Sanchez");
        
        
        //Cliente
        Cliente usuarioTest = new Cliente("Zaq*Mko123" , "Andres21" , "Andres Chaparro" );
        Cliente usuarioTest2 = new Cliente("Zaq*Mko123" , "Juan12" , "Juan Rojas" );
        Cliente usuarioTest3 = new Cliente("Zaq*Mko123" , "Juan56" , "Juana Rojas" );
	
        galeria.AgregarUsuario(usuarioTest);
        usuarioTest.agregarSaldo(20000000);
        
        galeria.AgregarUsuario(usuarioTest2);
        usuarioTest2.agregarSaldo(20000000);
        
        galeria.AgregarUsuario(usuarioTest3);
        usuarioTest3.agregarSaldo(20000000);
        
        usuarioTest.registrarPieza("1924", "Pedro1", "Roma", "TheBorn", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(0).getCodigoPieza());
        usuarioTest.agregarSaldo(20000000);
        
        usuarioTest.registrarPieza("1999", "Pedro", "Roma", "TheBorn2", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(1).getCodigoPieza() );
        
        
        usuarioTest.registrarPieza("1998", "Pedro", "Roma", "TheBorn3", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(2).getCodigoPieza() );
        
        usuarioTest.registrarPieza("1959", "Pedro", "Roma", "TheBorn4", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(3).getCodigoPieza() );
        
        usuarioTest.registrarPieza("1129", "Pedro", "Roma", "TheBorn5", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(4).getCodigoPieza() );
        
        usuarioTest.registrarPieza("1569", "Pedro", "Roma", "TheBorn6", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(5).getCodigoPieza() );
        
        System.out.println(galeria.getInventario().getCapacidad());
        
        for(Pieza pieza: galeria.getInventario().getPiezasTotales()) {
        	System.out.println(pieza.getAnoCreacion());
        }
        
        Operador operador = galeria.getOperador();
        for(Pieza pieza: galeria.getInventario().getPiezasTotales()) 
        {
        	operador.agregarPiezaSubasta(pieza.getCodigoPieza());
        }
        System.out.println(operador.getPiezasSubasta());
        
        
        operador.CrearSubasta();
        Subasta subasta = galeria.getSubasta();
        System.out.println(galeria.getSubasta());
        
        usuarioTest.ingresarASubasta();
        usuarioTest2.ingresarASubasta();
        usuarioTest3.ingresarASubasta();
        
        System.out.println(subasta.getClientesSubasta());
        System.out.println(subasta.getPiezasParaSubastar());
        System.out.println(subasta.getPiezasSubasta().values());
        
        operador.iniciarSubasta();
        
        usuarioTest3.realizarOfertaSubasta("TheBorn", 11500000);
        usuarioTest2.realizarOfertaSubasta("TheBorn", 10100000);
        
        
        usuarioTest2.realizarOfertaSubasta("TheBorn2", 11100000);
        usuarioTest2.realizarOfertaSubasta("TheBorn3", 11100000);
        usuarioTest2.realizarOfertaSubasta("TheBorn4", 11100000);
        usuarioTest2.realizarOfertaSubasta("TheBorn5", 11100000);
        usuarioTest2.realizarOfertaSubasta("TheBorn6", 11100000);
        
        System.out.println(galeria.getRegistrosPorSubasta());
        
        HashMap <String, PiezaSubastada> piezasSubasta= subasta.getPiezasSubasta();
        PiezaSubastada piezaSubasta = piezasSubasta.get("TheBorn");
        System.out.println(piezaSubasta.getMayorPuja());
        
        operador.finalizarSubastaOperador();
        
        
        System.out.println(usuarioTest3.getPasadas());
        System.out.println(usuarioTest.getPasadas());
        System.out.println(galeria.getInventario().getPiezasTotales());
        System.out.println(usuarioTest3.getSaldo());
        
        System.out.println(galeria.getSubasta());
        
        operador.CrearSubasta();
        
        System.out.println(galeria.getSubasta().isInicializacion());
        operador.iniciarSubasta();
        System.out.println(galeria.getSubasta().isInicializacion());
        
        System.out.println(usuarioTest3.getPasadas().get(0).getPrecioVenta());
        System.out.println(usuarioTest3.getPasadas().get(0).getFechaVenta());
        
        
        Inventario inventario  = galeria.getInventario();
        System.out.println(inventario.getArtistas().get(1).getPiezas());
        
        Administrador admin = galeria.getAdministrador();
        
        System.out.println(admin.getHistorial("Juan12"));
        
        
//        List<Cliente> clientes = usuarioTest3.getPasadas().get(0).getDuenos();
//        
//        for (Cliente cliente: clientes) {
//        	System.out.println(cliente.getNombre());
//        }
	}
	
	
	
}
