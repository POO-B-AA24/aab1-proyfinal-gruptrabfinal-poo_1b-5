/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anthony
 */
public class Evento {

    private String nombre;
    private LocalDate fecha;
    private List<BoletoBase> boletos;

    public Evento(String nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.boletos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<BoletoBase> getBoletos() {
        return boletos;
    }

    public void agregarBoleto(BoletoBase boleto) {
        boletos.add(boleto);
    }
}
