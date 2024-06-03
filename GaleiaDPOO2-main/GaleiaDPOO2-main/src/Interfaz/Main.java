package Interfaz;

import Model.GaleriaDeArte;
import Persistencia.PersistenciaUsuarios;
import Persistencia.PersistenciaPiezas;
import Persistencia.PersistenciaSubastaActual;
import Persistencia.PersistenciaSubastas;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GaleriaDeArte galeria = new GaleriaDeArte();

        // Cargar datos desde archivos
        cargarDatos(galeria);

        galeria.AgregarAdministrador("Qwer1234", "admin23", "Camilo");
        galeria.AgregarCajero("Qwer1234", "cajero23", "Ernesto");
        galeria.AgregarOperador("Qwer1234", "operador23", "Arturo");

        boolean salir = false;

        System.out.println("Bienvenido a la galería de arte.");

        while (!salir) {
            System.out.println("Por favor, seleccione su tipo de usuario:");
            System.out.println("1. Administrador");
            System.out.println("2. Operador");
            System.out.println("3. Cliente");
            System.out.println("4. Cerrar Aplicación");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer después de nextInt()

            switch (opcion) {
                case 1:
                    AdminMain.main(galeria);
                    break;
                case 2:
                    OperadorMain.main(galeria);
                    break;
                case 3:
                    ClienteMain.main(galeria);
                    break;
                case 4:
                    salir = true; // Salir del bucle y finalizar la aplicación
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }

        // Guardar datos en archivos antes de cerrar
        guardarDatos(galeria);

        scanner.close(); // Cerrar el escáner al salir del bucle
        System.out.println("Gracias por usar la aplicación. ¡Hasta luego!");
    }

    private static void cargarDatos(GaleriaDeArte galeria) {
        try {
            // Cargar usuarios
            String usuariosContent = new String(Files.readAllBytes(Paths.get("usuarios.json")));
            JSONArray jUsuarios = new JSONArray(usuariosContent);
            new PersistenciaUsuarios().CargarUsuarios(galeria, jUsuarios);

            // Cargar piezas
            String piezasContent = new String(Files.readAllBytes(Paths.get("piezas.json")));
            JSONObject jPiezas = new JSONObject(piezasContent);
            new PersistenciaPiezas().cargarPiezas(jPiezas, galeria);

            // Cargar subastas
            String subastasContent = new String(Files.readAllBytes(Paths.get("subastas.json")));
            JSONArray jSubastas = new JSONArray(subastasContent);
            List<List<String>> subastas = new PersistenciaSubastas().cargarSubastas(jSubastas);
            galeria.setRegistrosPorSubasta(subastas);

            // Cargar subasta actual
            String subastaActualContent = new String(Files.readAllBytes(Paths.get("subastaActual.json")));
            if (!subastaActualContent.isEmpty()) {
                JSONObject jSubastaActual = new JSONObject(subastaActualContent);
                new PersistenciaSubastaActual().cargarSubasta(galeria, jSubastaActual);
            }

            System.out.println("Datos cargados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }


    private static void guardarDatos(GaleriaDeArte galeria) {
        try {
            // Guardar usuarios
            JSONArray jUsuarios = new PersistenciaUsuarios().salvarUsuarios(galeria);
            Files.write(Paths.get("usuarios.json"), jUsuarios.toString().getBytes());

            // Guardar piezas
            JSONObject jPiezas = new PersistenciaPiezas().salvarPiezas(galeria);
            Files.write(Paths.get("piezas.json"), jPiezas.toString().getBytes());

            // Guardar subastas
            JSONArray jSubastas = new PersistenciaSubastas().salvarSubastas(galeria);
            Files.write(Paths.get("subastas.json"), jSubastas.toString().getBytes());

            // Guardar subasta actual
            JSONObject jSubastaActual = new PersistenciaSubastaActual().salvarSubasta(galeria);
            if (jSubastaActual != null) {
                Files.write(Paths.get("subastaActual.json"), jSubastaActual.toString().getBytes());
            }

            System.out.println("Datos guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

}

