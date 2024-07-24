package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private LocalDate fecha;
    private List<Boleto> boletos;

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

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public void agregarBoleto(Boleto boleto) {
        boletos.add(boleto);
    }
}
