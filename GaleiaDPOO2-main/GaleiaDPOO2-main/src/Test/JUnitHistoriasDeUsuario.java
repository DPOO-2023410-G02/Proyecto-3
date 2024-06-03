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

public class JUnitHistoriasDeUsuario {

    private static final Cliente CLIENTE_DUENO = new Cliente("12345678", "Juan2312", "Juan Perez");
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
    
    //Historia de Usuario 1: Como usuario, quiero poder listar todas las piezas de arte disponibles en la subasta.
    //Requerimiento Funcional: La aplicación debe permitir a los usuarios ver una lista de todas las piezas de arte disponibles en la subasta.
    @Test
    public void HistoriaUsuario1() {
        CLIENTE_DUENO.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        CLIENTE_DUENO.registrarPieza("2000", "Pedro", "Roma", "TheBorn2", 10000000);
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());

        operador.CrearSubasta();
        assertEquals(2, galeria.getSubasta().getPiezasSubasta().values().size(), "Las piezas para la subasta no se crearon correctamente");
        String NombrePieza1;
        NombrePieza1 = galeria.getSubasta().getPiezasParaSubastar().get(0).getTitulo();
        assertEquals("TheBorn", NombrePieza1 , "El cliente no pudo ver la primera pieza de las listas que se van a subastar");
        String NombrePieza2;
        NombrePieza2 = galeria.getSubasta().getPiezasParaSubastar().get(1).getTitulo();
        assertEquals("TheBorn2", NombrePieza2 , "El cliente no pudo ver la segunda pieza de las listas que se van a subastar");
    
    }
  
    //Historia de Usuario 2: Como usuario, quiero poder hacer una oferta en una pieza de arte en subasta.
    //Requerimiento Funcional 1: : La aplicación debe permitir a los usuarios ver la mayor puja de una pieza subastada para poder tomar una decisión.
    //Requerimiento Funcional 2: : La aplicación debe permitir a los usuarios hacer ofertas en las piezas de arte en subasta y esta se debe actualizar si es mayor a la puja actual.
    @Test
    public void HistoriaUsuario2() {
    	
        CLIENTE_DUENO.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        CLIENTE_DUENO.registrarPieza("2000", "Pedro", "Roma", "TheBorn2", 10000000);
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
        
        operador.CrearSubasta();
        
        CLIENTE1.agregarSaldo(50000000);
        CLIENTE1.ingresarASubasta();
        
        //El cliente quiere obvservar el valor actual de la pieza subastada TheBorn
        assertEquals(0, GaleriaDeArte.getSubasta().getPiezasSubasta().get("TheBorn").getMayorPuja(), "El Cliente no pudo observar cual era la mayor puja");
        
        //El cliente subasta de acuerdo a la puja maxima que tiene la pieza.
        CLIENTE1.realizarOfertaSubasta("TheBorn", 10000001);
        assertEquals(10000001, GaleriaDeArte.getSubasta().getPiezasSubasta().get("TheBorn").getMayorPuja(), "No se pudo hacer la puja correctamente");
        
    }
   
    //Historia de Usuario 3: Como usuario, quiero poder ver el estado actual de una subasta en particular.
    //Requerimiento Funcional 1: La aplicación debe permitir a los usuarios ver el estado actual (si esta abierta para ingresar piezas o solo para pujar) de una subasta.
    //Requerimiento Funcional 2: La aplicación debe reiniciar una subasta despues de haber finalizado una dejando a lso usuarios meterse a la siguiente subasta.
    @Test
    public void HistoriaUsuario3() {
    	
        CLIENTE_DUENO.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        CLIENTE_DUENO.registrarPieza("2000", "Pedro", "Roma", "TheBorn2", 10000000);
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        CLIENTE_DUENO.RealizarConsignacion(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(0).getCodigoPieza());
        operador.agregarPiezaSubasta(CLIENTE_DUENO.getPasadas().get(1).getCodigoPieza());

        assertEquals(2, operador.getPiezasSubasta().size(), "No se agregaron correctamente todas las piezas");
        
        
        operador.CrearSubasta();
        
        //El cliente puede meterse a la subasta que va a empezar
        assertEquals(false, GaleriaDeArte.getSubasta().isInicializacion(), "No se inicializo correctamente la subasta");
        //El cliente puede pujar a la subasta que comenzo
        operador.iniciarSubasta();
        assertEquals(true, GaleriaDeArte.getSubasta().isInicializacion(), "No se inicializo correctamente la subasta");
        
        operador.CrearSubasta();
        //El cliente puede meterse a la siguiente subasta despues de que se haya finalizado
        operador.finalizarSubastaOperador();
        assertEquals(false, GaleriaDeArte.getSubasta().isInicializacion(), "No se inicializo correctamente la subasta");

    }
    //Historia de Usuario 4: Como usuario, quiero poder realizar una compra directa de una pieza de arte disponible en la subasta.
    //Requerimiento Funcional 1: La aplicación debe permitir a los usuarios ingresar saldo a la aplicación.
    //Requerimiento Funcional 2: La aplicación debe permitir a los usuarios realizar compras directas de piezas de arte disponibles en la subasta.
    @Test
    public void HistoriaUsuario4() {
    	
    	Cliente clienteCompra = (Cliente) galeria.getUsuario("Pedro4354");
    	Cliente clienteDueno = (Cliente) galeria.getUsuario("Juan2312");
    	clienteCompra.agregarSaldo(20000000);
    	cajero.registarPago(clienteCompra, clienteDueno.getPasadas().get(0));
    	
    	assertEquals(clienteCompra.getSaldo(), 10000000, "El saldo no se resto");
    }
}