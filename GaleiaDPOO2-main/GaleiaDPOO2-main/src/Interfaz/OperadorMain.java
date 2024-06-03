package Interfaz;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import Model.Compra;
import Model.GaleriaDeArte;
import Model.PiezaSubastada;
import Pieza.Pieza;

public class OperadorMain {

	public static void main(GaleriaDeArte galeria) {
		
		Scanner scanner = new Scanner(System.in);

        System.out.println("Inicio de sesión como operador:");
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        if (galeria.iniciarSesionOperador(nombreUsuario, password)) {
            System.out.println("Inicio de sesión exitoso como Operador.");

            boolean salir = false;
            while (!salir) {
                System.out.println("\nMenú de Administrador:");
                System.out.println("1. Crear Subasta");
                System.out.println("2. Iniciar Subasta ");
                System.out.println("3. Apartar Pieza para subasta");
                System.out.println("4. Finalizar subasta ");
                System.out.println("5. Revisar registros subastas ");
                System.out.println("6. Revisar catalogo: ");
                System.out.println("7. Salir: ");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:

                    	galeria.getOperador().CrearSubasta();
                        
                    	System.out.println("la subasta se creo correctamente: ");
                        
                        break;
                    case 2:

                    	galeria.getOperador().iniciarSubasta();
                    	
                    	System.out.println("La subasta se inicio correctamente: ");
                        break;

                        
                    case 3:
                        System.out.println("Ingrese el codigo de la pieza que desea agregar: ");
                        scanner.nextLine(); // Consumir el salto de línea pendiente
                        String codigoPieza = scanner.nextLine();
                        galeria.getOperador().agregarPiezaSubasta(codigoPieza);
                        break;

                    case 4:
                    	
                    	Collection<PiezaSubastada> piezasFinal = galeria.getSubasta().getPiezasSubasta().values();
                    	
                    	for(PiezaSubastada pieza : piezasFinal) {
                    		System.out.println("--------------------");
                    		System.out.println("Titulo: " + pieza.getPiezaAsociada().getTitulo());
                    		System.out.println("Precio Venta: " + pieza.getMayorPuja());
                    		System.out.println("Ganador: " + pieza.getGanador().getLogin());
                    		System.out.println("--------------------");
                    	}
                    	galeria.getOperador().finalizarSubastaOperador();
                    	
                    	
                        break;
                    case 5:
                    	System.out.println(galeria.getRegistrosPorSubasta());                   	
                    	break;
                    	
                    case 6:
                    	
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
                    	
                    case 7:
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

