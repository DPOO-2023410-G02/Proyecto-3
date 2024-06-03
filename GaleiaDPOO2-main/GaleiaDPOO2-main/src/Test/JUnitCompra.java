package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.GaleriaDeArte;
import Pieza.Pieza;
import Usuario.Administrador;
import Usuario.Cajero;
import Usuario.Cliente;

public class JUnitCompra {
    
    private static final Cliente CLIENTE_DUENO = new Cliente("12345678", "Juan2312", "Juan Perez");
    private static final Cliente CLIENTE_COMPRA = new Cliente("12345678", "Pedro4354", "Pedro Ojeda");
    private Administrador admin;
    private GaleriaDeArte galeria;
    private Cajero cajero;
    
    @BeforeEach
    void setUp() {
        crearGaleria();
    }
    
    private void crearGaleria() {
        galeria = new GaleriaDeArte();
        galeria.AgregarAdministrador("12345678", "Fernando23", "Fernando Perez");
        galeria.AgregarCajero("12345678", "Fernando23", "Fernando Perez");
        galeria.AgregarUsuario(CLIENTE_COMPRA);
        galeria.AgregarUsuario(CLIENTE_DUENO);
        admin = galeria.getAdministrador();
        cajero = galeria.getCajero();
    }

    @Test
    public void testRegistrarPieza() {
        Cliente cliente = (Cliente) galeria.getUsuario("Juan2312");
        cliente.registrarPieza("1999", "Pedro", "Roma", "TheBorn", 10000000);
        
        assertEquals(1, cliente.getPasadas().size(), "La pieza no se añadió correctamente al inventario del cliente.");
    }
    
    @Test
    public void testConsignarPieza()
    {
    	Cliente clienteDueno = (Cliente) galeria.getUsuario("Juan2312");
    	clienteDueno.RealizarConsignacion(clienteDueno.getPasadas().get(0).getCodigoPieza());
    	assertEquals(galeria.getInventario().getPiezasTotales().size(), 1);
    }
    
    @Test
    public void testVerificarUsuario() {
        Cliente clienteDueno = (Cliente) galeria.getUsuario("Juan2312");
        Cliente clienteCompra = (Cliente) galeria.getUsuario("Pedro4354");
        boolean llave = admin.verificarUsuario(clienteCompra, clienteDueno.getPasadas().get(0));

        assertEquals(true, llave, "El usuario debería poder comprar la pieza.");
    }
    
    @Test
    public void testRegistrarPago() {
    	Cliente clienteCompra = (Cliente) galeria.getUsuario("Pedro4354");
    	Cliente clienteDueno = (Cliente) galeria.getUsuario("Juan2312");
    	clienteCompra.agregarSaldo(20000000);
    	cajero.registarPago(clienteCompra, clienteDueno.getPasadas().get(0));
    	
    	assertEquals(clienteCompra.getSaldo(), 10000000, "El saldo no se resto");
    }
     
    @Test
    public void registrarCompra() {
    	Cliente clienteDueno = (Cliente) galeria.getUsuario("Juan2312");
        Cliente clienteCompra = (Cliente) galeria.getUsuario("Pedro4354");
        Pieza pieza = clienteDueno.getPasadas().get(0);       
        clienteCompra.realizarOfertaCompra(pieza);
        assertEquals(galeria.getInventario().getPiezasTotales().size(), 0, "El inventario deberia estar vavio");
        assertEquals(galeria.getInventario().getPiezasPasadas().size(), 1, "El inventario deberia llevar el registro de piezas pasadas");
    	assertEquals(clienteDueno.getPasadas().size(), 0, "El cliente ya no deberia tener la pieza en el inventario");
    	assertEquals(clienteDueno.getPiezasPasadas().size(), 1, "El cliente deberia tener la pieza en el historia de piezas pasadas");
    	assertEquals(clienteCompra.getPasadas().size(), 1, "El cliente Comprador ya  deberia tener la pieza en el inventario");
    }
    
}


