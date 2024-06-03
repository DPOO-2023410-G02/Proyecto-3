package Test;

import java.io.FileWriter;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;

import Model.GaleriaDeArte;
import Model.Inventario;
import Persistencia.PersistenciaUsuarios;
import Pieza.Pieza;
import Usuario.Administrador;
import Usuario.Cliente;
import Usuario.Operador;
import Usuario.Usuario;

public class MainCompra {
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
        galeria.AgregarUsuario(usuarioTest2);
        Collection<Usuario> usuarios = galeria.getUsuarios();
        for (Usuario usuario : usuarios) {
        	 System.out.println("Nombre: " + usuario.getNombre());
        	 System.out.println("Login: " + usuario.getLogin());
        	 System.out.println("Password: " + usuario.getPassword());	   
        }
        System.out.println("");
        System.out.println("Test Consignacion - Inventario");
        usuarioTest.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        usuarioTest.RealizarConsignacion(usuarioTest.getPasadas().get(0).getCodigoPieza() );
        
        List<Pieza> piezasCliente = usuarioTest.getPasadas();
        System.out.println(piezasCliente);
       
        Inventario inventario = GaleriaDeArte.getInventario();
        List<Pieza> piezasBodega = inventario.getPiezasBodega();
        System.out.println(piezasBodega);
        for(Pieza pieza : piezasBodega) {
        	System.out.println("año: " + pieza.getAnoCreacion());
        	System.out.println("autor: " + pieza.getAutor());
        	System.out.println("codigo: " + pieza.getCodigoPieza());
        	System.out.println("FechaLimiteVenta: " + pieza.getFechaConsignacion());
        	System.out.println("lugarGaleria: " + pieza.getLugar());
        	System.out.println("lugar: " + pieza.getLugarCreacion());
        	System.out.println("precio: " + pieza.getPrecioCompra());
        	System.out.println("titulo: " + pieza.getTitulo());
        	System.out.println("propietario: " + pieza.getPropietario().getNombre());
        }
        List<Pieza> piezasColeccion = inventario.getPiezasColeccioon();
        System.out.println(piezasColeccion);
        
       
        System.out.println("");
        System.out.println("Test Compra");
        
        Pieza piezaCompra = inventario.getPiezaBodega(piezasBodega.get(0).getCodigoPieza());
        System.out.println(piezaCompra.isDisponible());
        usuarioTest2.agregarSaldo(50000000);
        usuarioTest2.realizarOfertaCompra(piezaCompra);      
        System.out.println(usuarioTest2.getSaldo());
        System.out.println(usuarioTest2.getValorMaximo());
        System.out.println(usuarioTest.getPasadas());
        System.out.println(usuarioTest.getPiezasPasadas());
        System.out.println(piezasBodega);
        System.out.println(usuarioTest2.getPasadas());
        System.out.println(inventario.getPiezasPasadas());
        System.out.println(usuarioTest2.getCompras().get(0).getPieza());
        
        Administrador admin = galeria.getAdministrador();
        System.out.println(admin.getHistorial("Juan12").get(0).getPieza());
        
        System.out.println(galeria.contarVentasPorDia());
    }
}