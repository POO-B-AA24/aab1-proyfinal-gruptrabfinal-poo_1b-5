package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private LocalDate date;
    private double price;
    private String nombre;
    private String cedula;

    public Ticket(String type, LocalDate date, double price, String nombre, String cedula) {
        this.type = type;
        this.date = date;
        this.price = price;
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }
}
