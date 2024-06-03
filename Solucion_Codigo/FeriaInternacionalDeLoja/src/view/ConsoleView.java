package view;

import model.Event;
import model.Ticket;

import java.util.List;

public class ConsoleView {
    public void displayEventDetails(Event event) {
        System.out.println("Event: " + event.getName());
        System.out.println("Date: " + event.getDate());
        System.out.println("Tickets Sold: " + event.getTickets().size());
    }

    public void displayTotalEarnings(double totalEarnings) {
        System.out.println("Total Earnings: $" + totalEarnings);
    }

    public void displayTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            System.out.println("Ticket Type: " + ticket.getType() + ", Date: " + ticket.getDate() + ", Price: $" + ticket.getPrice() + ", Nombre: " + ticket.getNombre() + ", Cedula: " + ticket.getCedula());
        }
    }

    public void displayAvailableEvents(List<Event> events) {
        System.out.println("Available Events:");
        for (Event event : events) {
            System.out.println("Event: " + event.getName() + ", Date: " + event.getDate());
        }
    }
}
