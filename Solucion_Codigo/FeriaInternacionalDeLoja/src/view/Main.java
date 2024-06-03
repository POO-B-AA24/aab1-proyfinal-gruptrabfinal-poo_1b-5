package view;

import controller.TicketController;
import java.io.IOException;
import model.TicketManager;
import view.ConsoleView;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    private static final String FILENAME = "ticket_manager.dat";

    public static void main(String[] args) {
        TicketManager ticketManager = loadTicketManager();
        ConsoleView consoleView = new ConsoleView();
        TicketController ticketController = new TicketController(ticketManager, consoleView);

        // Crear eventos si no hay eventos cargados
        if (ticketManager.getEvents().isEmpty()) {
            ticketController.createEvent("Concierto Nacional", LocalDate.of(2024, 8, 31));
            ticketController.createEvent("Concierto Internacional", LocalDate.of(2024, 9, 7));
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nSistema de Gestion de Entradas - Feria Internacional de Loja");
            System.out.println("1. Comprar boleto");
            System.out.println("2. Ver ganancias totales");
            System.out.println("3. Ver boletos por fecha");
            System.out.println("4. Ver Eventos disponibles");
            System.out.println("5. Eliminar boleto");
            System.out.println("6. Ver asistencia por evento");
            System.out.println("7. Ver estadisticas de la feria");
            System.out.println("8. Guardar y salir");
            System.out.print("Seleccione una opcion: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consumir la nueva linea

            String dateString;
            LocalDate date;

            switch (choice) {
                case 1:
                    System.out.print("Ingrese el tipo de boleto (Normal/Especial): ");
                    String type = scanner.nextLine();
                    System.out.print("Ingrese la fecha del evento (yyyy-mm-dd): ");
                    dateString = scanner.nextLine();
                    date = LocalDate.parse(dateString);
                    System.out.print("Ingrese el nombre de la persona: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la cedula de la persona: ");
                    String cedula = scanner.nextLine();
                    ticketController.sellTicket(type, date, nombre, cedula);
                    System.out.println("Boleto comprado con exito.");
                    break;
                case 2:
                    ticketController.displayTotalEarnings();
                    break;
                case 3:
                    System.out.print("Ingrese la fecha (yyyy-mm-dd): ");
                    dateString = scanner.nextLine();
                    date = LocalDate.parse(dateString);
                    ticketController.displayTicketsByDate(date);
                    break;
                case 4:
                    ticketController.displayAvailableEvents();
                    break;
                case 5:
                    System.out.print("Ingrese el tipo de boleto (Normal/Especial): ");
                    String typeToRemove = scanner.nextLine();
                    System.out.print("Ingrese la fecha del evento (yyyy-mm-dd): ");
                    dateString = scanner.nextLine();
                    date = LocalDate.parse(dateString);
                    System.out.print("Ingrese el precio del boleto: ");
                    double priceToRemove = scanner.nextDouble();
                    scanner.nextLine();  // Consumir la nueva linea
                    System.out.print("Ingrese la cedula de la persona: ");
                    String cedulaToRemove = scanner.nextLine();
                    ticketController.removeTicket(typeToRemove, date, priceToRemove, cedulaToRemove);
                    break;
                case 6:
                    System.out.print("Ingrese el nombre del evento: ");
                    String eventName = scanner.nextLine();
                    ticketController.displayAttendanceByEvent(eventName);
                    break;
                case 7:
                    ticketController.displayStatistics();
                    break;
                case 8:
                    saveTicketManager(ticketManager);
                    System.out.println("Datos guardados. Saliendo del sistema. Hasta luego");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida. Por favor, intente nuevamente.");
            }
        }
    }

    private static TicketManager loadTicketManager() {
        try {
            return TicketManager.loadFromFile(FILENAME);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo cargar los datos anteriores, iniciando un nuevo sistema.");
            return new TicketManager();
        }
    }

    private static void saveTicketManager(TicketManager ticketManager) {
        try {
            ticketManager.saveToFile(FILENAME);
        } catch (IOException e) {
            System.err.println("Error guardando los datos: " + e.getMessage());
        }
    }
}
