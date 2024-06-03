package controller;

import model.Event;
import model.Ticket;
import model.TicketManager;
import view.ConsoleView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.temporal.WeekFields;

public class TicketController {
    private TicketManager ticketManager;
    private ConsoleView consoleView;

    public TicketController(TicketManager ticketManager, ConsoleView consoleView) {
        this.ticketManager = ticketManager;
        this.consoleView = consoleView;
    }

    public void createEvent(String name, LocalDate date) {
        Event event = new Event(name, date);
        ticketManager.addEvent(event);
    }

    public void sellTicket(String type, LocalDate date, String nombre, String cedula) {
        if (type.equals("Especial") && !isSpecialEventAllowed(date)) {
            System.out.println("Las funciones especiales solo se pueden reservar los jueves, viernes y sabados en horario de 5 pm a 2 am.");
            return;
        }
        double price = ticketManager.calculateTicketPrice(type, date);
        Ticket ticket = new Ticket(type, date, price, nombre, cedula);
        ticketManager.sellTicket(ticket);
    }

    private boolean isSpecialEventAllowed(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        LocalTime currentTime = LocalTime.now();
        return (day == DayOfWeek.THURSDAY || day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY) &&
               (currentTime.isAfter(LocalTime.of(17, 0)) || currentTime.isBefore(LocalTime.of(2, 0)));
    }

    public void removeTicket(String type, LocalDate date, double price, String cedula) {
        boolean removed = ticketManager.removeTicket(type, date, price, cedula);
        if (removed) {
            System.out.println("Boleto eliminado con exito.");
        } else {
            System.out.println("No se encontro un boleto que coincida con los detalles proporcionados.");
        }
    }

    public void displayEventDetails(String eventName) {
        for (Event event : ticketManager.getEvents()) {
            if (event.getName().equals(eventName)) {
                consoleView.displayEventDetails(event);
                return;
            }
        }
        System.out.println("Event not found.");
    }

    public void displayTotalEarnings() {
        double totalEarnings = ticketManager.calculateTotalEarnings();
        consoleView.displayTotalEarnings(totalEarnings);
    }

    public void displayTicketsByDate(LocalDate date) {
        List<Ticket> tickets = ticketManager.getTicketsByDate(date);
        consoleView.displayTickets(tickets);
    }

    public void displayAvailableEvents() {
        List<Event> events = ticketManager.getEvents();
        consoleView.displayAvailableEvents(events);
    }

    public void displayAttendanceByEvent(String eventName) {
        int attendance = ticketManager.getAttendanceByEvent(eventName);
        System.out.println("Asistencia para el evento " + eventName + ": " + attendance + " personas.");
    }

    public void displayStatistics() {
        double totalEarnings = ticketManager.calculateTotalEarnings();
        System.out.println("Ganancias Totales: $" + totalEarnings);

        List<Event> events = ticketManager.getEvents();
        for (Event event : events) {
            int attendance = event.getTickets().size();
            System.out.println("Evento: " + event.getName() + ", Fecha: " + event.getDate() + ", Asistencia: " + attendance + " personas.");
        }

        Map<LocalDate, Long> attendanceByDate = ticketManager.getSoldTickets().stream()
                .collect(Collectors.groupingBy(Ticket::getDate, Collectors.counting()));
        System.out.println("Asistencia por dia:");
        attendanceByDate.forEach((date, count) -> System.out.println(date + ": " + count + " personas"));

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        Map<Integer, Long> attendanceByWeek = ticketManager.getSoldTickets().stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getDate().get(weekFields.weekOfWeekBasedYear()), Collectors.counting()));
        System.out.println("Asistencia por semana:");
        attendanceByWeek.forEach((week, count) -> System.out.println("Semana " + week + ": " + count + " personas"));
    }
}
