package Interfaz;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import Model.GaleriaDeArte;
import Model.PiezaSubastada;
import Model.Subasta;
import Pieza.Pieza;
import Usuario.Cliente;

public class ClienteMain {
	

    public static void main(GaleriaDeArte galeria) {
        Scanner scanner = new Scanner(System.in);
        boolean usuarioAutenticado = false;

        // Menú de inicio de sesión y registro
        String nombreUsuarioAutenticado = "";
       
        
        while (!usuarioAutenticado) {
            System.out.println("Menú Principal:");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer después de nextInt()

            switch (opcion) {
                case 1:
                    // Lógica para iniciar sesión
                    System.out.println("Iniciar sesión...");
                    System.out.print("Nombre de usuario: ");
                    String nombreUsuario = scanner.nextLine();

                    System.out.print("Contraseña: ");
                    String password = scanner.nextLine();
                    if (galeria.iniciarSesionCliente(nombreUsuario, password)) {
                        System.out.println("Inicio de sesión exitoso como cliente.");
                        usuarioAutenticado = true;
                        nombreUsuarioAutenticado = nombreUsuario;
                    } else {
                        System.out.println("Nombre de usuario o contraseña incorrectos.");
                    }
                    break;
                case 2:
                    System.out.println("Registrarse...");
                    System.out.print("Ingrese el nombre de Usuario: ");
                    String nombreUsuarioRegistro = scanner.nextLine();
                            
                    System.out.print("Ingrese la contraseña: ");
                    String passwordRegistro = scanner.nextLine();
                            
                    System.out.print("Ingrese su nombre: ");
                    String nombreRegistro = scanner.nextLine();
                            
                    Cliente cliente = new Cliente(passwordRegistro, nombreUsuarioRegistro, nombreRegistro);
                    galeria.AgregarUsuario(cliente);
                    nombreUsuarioAutenticado = nombreUsuarioRegistro;
                    usuarioAutenticado = true;
                    break;
                case 3:
                    return; 
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }


       
        // Menú de acciones para el usuario autenticado
        while (usuarioAutenticado) {
            System.out.println("\nMenú de Acciones:");
            System.out.println("1. Ver Catálogo de Piezas");
            System.out.println("2. Realizar Compra");
            System.out.println("3. Ver Piezas propias");
            System.out.println("4. Ver saldo actual");
            System.out.println("5. Agregar saldo");
            System.out.println("6. Registrar pieza nueva");
            System.out.println("7. Consignar pieza en la galeria.");
            System.out.println("8. Ingresar a la subasta actual");
            System.out.println("9. Realizar Oferta subasta.");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    
                	
                	
                	List<Pieza> piezasGaleria = galeria.getInventario().getPiezasTotales();
                	System.out.println("Piezas en la Galería:");

                	for (Pieza pieza : piezasGaleria) {
                		System.out.println("----------------------");
                	    System.out.println("Título: " + pieza.getTitulo());
                	    System.out.println("Autor: " + pieza.getAutor());
                	    System.out.println("Precio: " + pieza.getPrecioCompra());
                	    System.out.println("Codigo: " + pieza.getCodigoPieza());
                	    System.out.println("----------------------");
                	}
 
                	
                    break;
                    
                case 2:
                    Cliente clienteCompra = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);

                    System.out.print("Ingrese el código de la pieza que desea comprar: ");
                    String codigoPiezaCompra = scanner.nextLine();

                    if (codigoPiezaCompra.isEmpty()) {
                        System.out.println("No se ha ingresado ningún código.");
                        break;
                    }

                    Pieza piezaCompra = galeria.getInventario().getPiezaTotal(codigoPiezaCompra);

                    System.out.println("Ingrese los datos de su tarjeta de crédito:");
                    
                    System.out.print("Número de tarjeta: ");
                    String numeroTarjeta = scanner.nextLine(); // Usar nextLine()

                    System.out.print("CSV (Código de seguridad): ");
                    String csv = scanner.nextLine(); // Usar nextLine()

                    System.out.print("Fecha de expiración (MM/YY): ");
                    String fechaExpiracion = scanner.nextLine(); // Usar nextLine()

                    // Llamada a la función para registrar la compra con tarjeta
                    clienteCompra.realizarOfertaCompraTarjeta(piezaCompra, numeroTarjeta, csv, fechaExpiracion, "PayPal");
                    break;






                    
                    
                case 3:
                
                    
                    List<Pieza> piezasCliente = galeria.getAdministrador().getPiezasCliente(nombreUsuarioAutenticado);
                    for (Pieza pieza : piezasCliente) {
                        System.out.println("----------------------");
                        System.out.println("Título: " + pieza.getTitulo());
                        System.out.println("Autor: " + pieza.getAutor());
                        System.out.println("Precio: " + pieza.getPrecioCompra());
                        System.out.println("Codigo: " + pieza.getCodigoPieza());
                        System.out.println("----------------------");
                    }
                    break;
                case 4:    
                    Cliente clienteSaldo = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);
                    System.out.println("Su saldo es: " + clienteSaldo.getSaldo());
                    break;
                case 5:

                    Cliente clienteAgregarSaldo = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);

                    System.out.print("Ingrese la cantidad que desea agregar: ");
                    scanner.nextLine();
                    int valorAgregar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer después de nextInt()

                    clienteAgregarSaldo.agregarSaldo(valorAgregar);
                    System.out.println("Su nuevo saldo es: " + clienteAgregarSaldo.getSaldo());
                    break;
                case 6:
                    Cliente clienteRegistrar = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);

                    if (clienteRegistrar != null) {
                        System.out.print("Ingrese el año de creación de la pieza: ");
                        int anoCreacion = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea pendiente

                        System.out.print("Ingrese el autor de la pieza: ");
                        String autor = scanner.nextLine();

                        System.out.print("Ingrese el lugar de creación de la pieza: ");
                        String lugarCreacion = scanner.nextLine();

                        System.out.print("Ingrese el título de la pieza: ");
                        String titulo = scanner.nextLine();

                        System.out.print("Ingrese el precio de la pieza: ");
                        int precio = scanner.nextInt();
                        scanner.nextLine(); // Consumir el salto de línea pendiente

                        clienteRegistrar.registrarPieza(String.valueOf(anoCreacion), autor, lugarCreacion, titulo, precio);
                        System.out.println("La consignación se ha realizado correctamente.");
                    } else {
                        System.out.println("No se encontró el usuario. Verifique el nombre de usuario e intente nuevamente.");
                    }
                    break;

                case 7:
   
                    Cliente clienteConsignar = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);

                    System.out.print("Ingrese el codigo de la pieza que desea consignar: ");
                    scanner.nextLine();
                    String codigoPiezaConsignacion = scanner.nextLine();
                    clienteConsignar.RealizarConsignacion(codigoPiezaConsignacion);
                    break;
                case 8:

                    Cliente clienteSubasta = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);
                    clienteSubasta.ingresarASubasta();
                    break;
                case 9:
                    Cliente clientePuja = (Cliente) galeria.getUsuario(nombreUsuarioAutenticado);
                    Subasta subasta = galeria.getSubasta();

                    if (subasta != null) {
                        Collection<PiezaSubastada> piezasSubasta = subasta.getPiezasSubasta().values();

                        System.out.println("Piezas disponibles para puja:");
                        int i = 1;
                        for (PiezaSubastada pieza : piezasSubasta) {
                            System.out.println(i + ". " + pieza.getPiezaAsociada().getTitulo() + " - Precio actual: " + pieza.getMayorPuja());
                            i++;
                        }
                        System.out.println(galeria.getSubasta().getPiezasSubasta().size());
                        System.out.println(galeria.getSubasta().getPiezasSubasta());
                        
                        System.out.print("Ingrese el título de la pieza: ");
                        scanner.nextLine(); // Consumir el salto de línea pendiente
                        String tituloPieza = scanner.nextLine().trim(); // Eliminar espacios adicionales

                        // Verificar si la pieza existe en la subasta
                        PiezaSubastada piezaSubastada = galeria.getSubasta().getPiezasSubasta().get(tituloPieza);

                        if (piezaSubastada != null) {
                            System.out.print("Ingrese el valor de su puja: ");
                            int valorPuja = scanner.nextInt();
                            scanner.nextLine(); // Limpiar el buffer después de nextInt()

                            // Realizar la oferta de subasta
                            clientePuja.realizarOfertaSubasta(tituloPieza, valorPuja);
                        } else {
                            System.out.println("La pieza no fue encontrada en la subasta. Verifique el título e intente nuevamente.");
                        }
                    } else {
                        System.out.println("No hay ninguna subasta en curso.");
                    }
                    break;




                case 10:
                    usuarioAutenticado = false; // Salir del bucle y volver al menú principal
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
    }
}
