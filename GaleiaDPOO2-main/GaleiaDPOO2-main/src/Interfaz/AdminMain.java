package Interfaz;

import java.util.List;
import java.util.Scanner;

import Model.Compra;
import Model.GaleriaDeArte;
import Pieza.Pieza;
import Usuario.Administrador;

public class AdminMain {

    public static void main(GaleriaDeArte galeria) {
    	
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inicio de sesión como administrador:");
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (galeria.iniciarSesionAdmin(nombreUsuario, password)) {
            System.out.println("Inicio de sesión exitoso como administrador.");

            boolean salir = false;
            while (!salir) {
                System.out.println("\nMenú de Administrador:");
                System.out.println("1. Ver Historial de Compras de un Cliente");
                System.out.println("2. Ver Piezas de un Cliente");
                System.out.println("3. Ver Valor Total de la Colección de un Cliente");
                System.out.println("4. Verificar consignacion de piezas. ");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                    	System.out.println("Ingrese el login del cliente:");
                    	scanner.nextLine();
                    	String loginCliente = scanner.nextLine();
                        List<Compra> comprasCliente = galeria.getAdministrador().getHistorial(loginCliente);
                        int contador = 1;
                        for (Compra compra : comprasCliente) {
                            System.out.printf("%d. Título: %s, Precio: %d, Fecha: %s%n", contador,
                                              compra.getPieza().getTitulo(), compra.getPrecio(), compra.getFecha());
                            contador++;
                        }
                        
                        
                        break;
                    case 2:
                    	System.out.println(galeria.getUsuarios().size());
                        System.out.println("Ingrese el login del cliente:");
                        scanner.nextLine(); // Consumir el salto de línea pendiente
                        String loginClientePieza = scanner.nextLine();
                        List<Pieza> piezasCliente = galeria.getAdministrador().getPiezasCliente(loginClientePieza);
                        int contador2 = 1;
                        for (Pieza pieza : piezasCliente) {
                            System.out.printf("%d. Título: %s, Precio: %d%n", contador2,
                                              pieza.getTitulo(), pieza.getPrecioCompra());
                            contador2++;
                        }
                        break;

                        
                    case 3:
                    	System.out.println("Ingrese el login del cliente:");
                    	scanner.nextLine();
                    	String loginClienteValor= scanner.nextLine();
                        int valorPiezas = galeria.getAdministrador().getValorColeccion(loginClienteValor);
                        
                        System.out.println("El valor es: " + valorPiezas);
                        
          
                        break;
                    case 4:
                        galeria.getInventario().verificarConsignacionPiezas();
                        System.out.println("Se verifico la consignacion correctamente!");
                    	
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                        break;
                }
            }
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }
}
