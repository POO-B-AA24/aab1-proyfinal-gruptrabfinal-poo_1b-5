package model;

import java.time.LocalDate;

public class Boleto {
    private String nombre;
    private String cedula;
    private String tipo;
    private LocalDate fecha;
    private double precio;

    public Boleto(String nombre, String cedula, String tipo, LocalDate fecha, double precio) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.tipo = tipo;
        this.fecha = fecha;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
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
