package view;

import com.opencsv.exceptions.CsvValidationException;
import controller.ControladorDeBoletos;
import model.GestorDeBoletos;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Menu {

    private static final String ARCHIVO_EVENTOS_CSV = "src/main/resources/events.csv";
    private static final String ARCHIVO_BOLETOS_CSV = "src/main/resources/tickets.csv";

    public static void main(String[] args) throws CsvValidationException {
        GestorDeBoletos gestorDeBoletos = new GestorDeBoletos();
        try {
            gestorDeBoletos.cargarEventosDesdeCSV(ARCHIVO_EVENTOS_CSV);
            gestorDeBoletos.cargarBoletosDesdeCSV(ARCHIVO_BOLETOS_CSV);
        } catch (IOException e) {
            System.out.println("No se pudo cargar los datos anteriores, iniciando un nuevo sistema.");
        }

        VistaConsola vistaConsola = new VistaConsola();
        ControladorDeBoletos controladorDeBoletos = new ControladorDeBoletos(gestorDeBoletos, vistaConsola);

        // Crear eventos si no hay eventos cargados
        if (gestorDeBoletos.obtenerEventos().isEmpty()) {
            controladorDeBoletos.crearEvento("Concierto Nacional", LocalDate.of(2024, 8, 31));
            controladorDeBoletos.crearEvento("Concierto Internacional", LocalDate.of(2024, 9, 7));
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSistema de Gestión de Entradas - Feria Internacional de Loja");
            System.out.println("1. Comprar boleto");
            System.out.println("2. Ver ganancias totales");
            System.out.println("3. Ver boletos por fecha");
            System.out.println("4. Ver eventos disponibles");
            System.out.println("5. Eliminar boleto");
            System.out.println("6. Ver asistencia por evento");
            System.out.println("7. Guardar y salir");
            System.out.print("Seleccione una opción: ");
            int eleccion = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva línea

            String fechaString;
            LocalDate fecha;

            switch (eleccion) {
                case 1:
                    System.out.print("Ingrese el tipo de boleto (Normal/Especial): ");
                    String tipo = scanner.nextLine();
                    System.out.print("Ingrese la fecha del evento (yyyy-mm-dd): ");
                    fechaString = scanner.nextLine();
                    fecha = LocalDate.parse(fechaString);
                    System.out.print("Ingrese el nombre de la persona: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la cédula de la persona: ");
                    String cedula = scanner.nextLine();
                    controladorDeBoletos.venderBoleto(tipo, fecha, nombre, cedula);
                    break;
                case 2:
                    controladorDeBoletos.mostrarGananciasTotales();
                    break;
                case 3:
                    System.out.print("Ingrese la fecha (yyyy-mm-dd): ");
                    fechaString = scanner.nextLine();
                    fecha = LocalDate.parse(fechaString);
                    controladorDeBoletos.mostrarBoletosPorFecha(fecha);
                    break;
                case 4:
                    controladorDeBoletos.mostrarEventosDisponibles();
                    break;
                case 5:
                    System.out.print("Ingrese el tipo de boleto a eliminar (Normal/Especial): ");
                    tipo = scanner.nextLine();
                    System.out.print("Ingrese la fecha del evento (yyyy-mm-dd): ");
                    fechaString = scanner.nextLine();
                    fecha = LocalDate.parse(fechaString);
                    System.out.print("Ingrese el precio del boleto: ");
                    double precio = scanner.nextDouble();
                    scanner.nextLine();  // Consumir la nueva línea
                    System.out.print("Ingrese la cédula de la persona: ");
                    cedula = scanner.nextLine();
                    controladorDeBoletos.eliminarBoleto(tipo, fecha, precio, cedula);
                    break;
                case 6:
                    System.out.print("Ingrese el nombre del evento: ");
                    String nombreEvento = scanner.nextLine();
                    controladorDeBoletos.mostrarAsistenciaPorEvento(nombreEvento);
                    break;
                case 7:
                    try {
                        gestorDeBoletos.guardarEventosEnCSV(ARCHIVO_EVENTOS_CSV);
                        gestorDeBoletos.guardarBoletosEnCSV(ARCHIVO_BOLETOS_CSV);
                    } catch (IOException e) {
                        System.out.println("Error al guardar los datos: " + e.getMessage());
                    }
                    System.out.println("Datos guardados. Saliendo del sistema.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente de nuevo.");
            }
        }
    }
}
