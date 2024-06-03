package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private LocalDate date;
    private List<Ticket> tickets;

    public Event(String name, LocalDate date) {
        this.name = name;
        this.date = date;
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }
}
