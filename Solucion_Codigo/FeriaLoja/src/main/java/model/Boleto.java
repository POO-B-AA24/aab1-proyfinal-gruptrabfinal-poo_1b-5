package model;

import java.time.LocalDate;

public class Boleto extends Persona {
    private static final long serialVersionUID = 1L;
    private String tipo;
    private LocalDate fecha;
    private double precio;

    public Boleto(String tipo, LocalDate fecha, double precio, String nombre, String cedula) {
        super(nombre, cedula);
        this.tipo = tipo;
        this.fecha = fecha;
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getPrecio() {
        return precio;
    }
}
