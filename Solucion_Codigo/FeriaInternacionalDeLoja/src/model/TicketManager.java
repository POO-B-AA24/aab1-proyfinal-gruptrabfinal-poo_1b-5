package model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Event> events;
    private List<Ticket> soldTickets;

    private static final double NORMAL_TICKET_PRICE = 10.0;
    private static final double SPECIAL_TICKET_PRICE = 20.0;

    public TicketManager() {
        events = new ArrayList<>();
        soldTickets = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public Event getEventByDate(LocalDate date) {
        for (Event event : events) {
            if (event.getDate().equals(date)) {
                return event;
            }
        }
        return null;
    }

    public void sellTicket(Ticket ticket) {
        soldTickets.add(ticket);
        Event event = getEventByDate(ticket.getDate());
        if (event != null) {
            event.addTicket(ticket);
        }
    }

    public boolean removeTicket(String type, LocalDate date, double price, String cedula) {
        for (Ticket ticket : soldTickets) {
            if (ticket.getType().equals(type) && ticket.getDate().equals(date) && ticket.getPrice() == price && ticket.getCedula().equals(cedula)) {
                soldTickets.remove(ticket);
                Event event = getEventByDate(ticket.getDate());
                if (event != null) {
                    event.getTickets().remove(ticket);
                }
                return true;
            }
        }
        return false;
    }

    public List<Ticket> getSoldTickets() {
        return soldTickets;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Ticket> getTicketsByDate(LocalDate date) {
        List<Ticket> ticketsByDate = new ArrayList<>();
        for (Ticket ticket : soldTickets) {
            if (ticket.getDate().equals(date)) {
                ticketsByDate.add(ticket);
            }
        }
        return ticketsByDate;
    }

    public double calculateTotalEarnings() {
        double total = 0;
        for (Ticket ticket : soldTickets) {
            total += ticket.getPrice();
        }
        return total;
    }

    public double calculateTicketPrice(String type, LocalDate date) {
        double price = type.equals("Especial") ? SPECIAL_TICKET_PRICE : NORMAL_TICKET_PRICE;
        // Aplicar promociones para dias especificos si es necesario
        if (type.equals("Especial") && (date.getDayOfWeek().getValue() == 5 || date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7)) {
            price *= 0.9;  // 10% de descuento los viernes, sabados y domingos
        }
        return price;
    }

    public int getAttendanceByEvent(String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event.getTickets().size();
            }
        }
        return 0;
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public static TicketManager loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (TicketManager) ois.readObject();
        }
    }
}
