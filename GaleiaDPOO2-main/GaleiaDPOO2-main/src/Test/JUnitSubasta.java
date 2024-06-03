package Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.GaleriaDeArte;
import Usuario.Administrador;
import Usuario.Cajero;
import Usuario.Cliente;
import Usuario.Operador;

public class JUnitSubasta {

    private static final Cliente CLIENTE_DUENO = new Cliente("12345678", "Juan2312", "Juan Perez");
    private static final Cliente CLIENTE_DUENO_SUBASTA = new Cliente("12345678", "Juan2312", "Juan Perez");
    private static final Cliente CLIENTE1 = new Cliente("12345678", "Julio4354", "Julio Ojeda");
    private static final Cliente CLIENTE2 = new Cliente("12345678", "Pedro4354", "Pedro Ojeda");
    private static final Cliente CLIENTE3 = new Cliente("12345678", "camilo4354", "Camilo Ojeda");
    private Administrador admin;
    private GaleriaDeArte galeria;
    private Cajero cajero;
    private Operador operador;

    @BeforeEach
    void setUp() {
        crearGaleria();
    }

    private void crearGaleria() {
        galeria = new GaleriaDeArte();
        galeria.AgregarAdministrador("12345678", "Fernando23", "Fernando Perez");
        galeria.AgregarCajero("12345678", "Fernando23", "Fernando Perez");
        galeria.AgregarOperador("12345678", "Fernando23", "Fernando Perez");
        galeria.AgregarUsuario(CLIENTE1);
        galeria.AgregarUsuario(CLIENTE2);
        galeria.AgregarUsuario(CLIENTE3);
        galeria.AgregarUsuario(CLIENTE_DUENO);
        admin = galeria.getAdministrador();
        cajero = galeria.getCajero();
        operador = galeria.getOperador();
    }
    

    @Test
    public void testCreaSubasta() {
        CLIENTE_DUENO.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        CLIENTE_DUENO.registrarPieza("2000", "Pedro", "Roma", "TheBorn2", 10000000);
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());

        assertEquals(2, operador.getPiezasSubasta().size(), "No se agregaron correctamente todas las piezas");
        operador.CrearSubasta();
        assertEquals(2, galeria.getSubasta().getPiezasSubasta().values().size(), "Las piezas para la subasta no se crearon correctamente");
    }

    @Test
    public void testVerificarSubasta() {
    	CLIENTE1.agregarSaldo(50000000);
        CLIENTE2.agregarSaldo(50000000);
        CLIENTE3.agregarSaldo(50000000);
    	assertEquals(true, admin.verificarUsuarioSubasta(CLIENTE1, 5000) ,"No se verifico correctamente cuando el saldo es mayor al precio minimo de entrada.");
    	assertEquals(false, admin.verificarUsuarioSubasta(CLIENTE1, 500000000) ,"No se verifico correctamente cuando el saldo es menor al precio minimo de entrada.");
    	assertEquals(true, admin.verificarUsuarioSubasta(CLIENTE1, 50000000) ,"No se verifico correctamente cuando el saldo es igual al precio minimo de entrada.");

    }

    @Test
    public void testIngresarSubasta() {
    	CLIENTE_DUENO.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        CLIENTE_DUENO.registrarPieza("2000", "Pedro", "Roma", "TheBorn2", 10000000);
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
       
        assertEquals(2, operador.getPiezasSubasta().size(), "No se agregaron correctamente todas las piezas");
        operador.CrearSubasta();
        
        CLIENTE1.agregarSaldo(50000000);
        CLIENTE2.agregarSaldo(50000000);
        CLIENTE3.agregarSaldo(50000000);
        CLIENTE1.ingresarASubasta();
        CLIENTE2.ingresarASubasta();
        CLIENTE3.ingresarASubasta();

        assertEquals(3, galeria.getSubasta().getClientesSubasta().size(), "No se agregaron correctamente todos los clientes a la subasta");
    }
    @Test
    public void testSubasta() {
    	CLIENTE_DUENO_SUBASTA.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
    	CLIENTE_DUENO_SUBASTA.registrarPieza("2000", "Pedro", "Roma", "TheBorn2", 10000000);
    	CLIENTE_DUENO_SUBASTA.RealizarConsignacion(CLIENTE_DUENO_SUBASTA.getPasadas().get(0).getCodigoPieza());
    	CLIENTE_DUENO_SUBASTA.RealizarConsignacion(CLIENTE_DUENO_SUBASTA.getPasadas().get(1).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO_SUBASTA.getPasadas().get(0).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO_SUBASTA.getPasadas().get(1).getCodigoPieza());       
       
        operador.CrearSubasta();
       
        CLIENTE1.agregarSaldo(50000000);
        CLIENTE2.agregarSaldo(50000000);
        CLIENTE3.agregarSaldo(50000000);
        CLIENTE1.ingresarASubasta();
        CLIENTE2.ingresarASubasta();
        CLIENTE3.ingresarASubasta();
        
    	operador.iniciarSubasta();
    	
    	CLIENTE1.realizarOfertaSubasta("TheBorn", 20000000);
    	//Prueba de que la puja se registro correctamente
    	assertEquals(20000000,GaleriaDeArte.getSubasta().getPiezasSubasta().get("TheBorn").getMayorPuja(),"No se registro correctamente la puja mayor.");
    	
    	//Prueba de que el ganador se mantiene cuando debe
    	CLIENTE2.realizarOfertaSubasta("TheBorn", 15000000);
    	assertEquals(CLIENTE1.getNombre(),GaleriaDeArte.getSubasta().getPiezasSubasta().get("TheBorn").getGanador().getNombre(), "No registra el cliente que va ganando de manera correcta.");

    	//Prueba de que el ganador cambia cuando debe
    	CLIENTE2.realizarOfertaSubasta("TheBorn", 30000000);
    	assertEquals(CLIENTE2.getNombre(),GaleriaDeArte.getSubasta().getPiezasSubasta().get("TheBorn").getGanador().getNombre(), "No registra el cliente que va ganando de manera correcta.");
    	
    	CLIENTE3.realizarOfertaSubasta("TheBorn2", 40000000);
    	//Prueba de que la puja se registro correctamente para otra pieza (ofertas multiples)
    	assertEquals(40000000,GaleriaDeArte.getSubasta().getPiezasSubasta().get("TheBorn2").getMayorPuja(),"No se registro correctamente la puja mayor para ofertas multiples.");
    	
    	//Prueba de que la puja se registro correctamente por el operador
    	operador.finalizarSubastaOperador();
    	assertEquals("TheBorn_20000000_"+CLIENTE1.getNombre(),GaleriaDeArte.getRegistrosPorSubasta().get(0).get(0),"No se se realizo correctamente el registro de la puja");
    
    	//Prueba de que la puja se elimininaron correctamente las piezas del inventario de galeria
    	assertEquals(0,GaleriaDeArte.getInventario().getPiezasTotales().size(),"No se eliminaron las piezas del inventario de galeria");

    	//Prueba de que la puja se añadieron correctamente las piezas al registro de piezas pasadas de galeria
    	assertEquals(2,GaleriaDeArte.getInventario().getPiezasPasadas().size(),"No se añadieron las piezas a el registro de la galeria (ppiezas pasadas)");
    	
    	//Prueba de que las piezas se entregaron correctamente
    	assertEquals(1,CLIENTE3.getPasadas().size(),"No se añadieron las piezas al cliente ganador");
    	assertEquals(1,CLIENTE2.getPasadas().size(),"No se añadieron las piezas al cliente ganador");
    	assertEquals(0,CLIENTE1.getPasadas().size(),"Se añadieron las piezas al cliente equivocado");

    	//Prueba que se realizaron los tramites correctos para el dueño original de las piezas
    	assertEquals(2,CLIENTE_DUENO_SUBASTA.getPiezasPasadas().size(),"No se añadieron las piezas al registro de piezas pasadas del dueño original");
    	assertEquals(0,CLIENTE_DUENO_SUBASTA.getPasadas().size(),"No se eliminaron las piezas del inventario de piezas del dueño original");
    	
    	
    }
    
}

