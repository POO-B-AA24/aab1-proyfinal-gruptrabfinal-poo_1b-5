package model;

import java.time.LocalDate;

public class BoletoBase extends Boleto {

    public BoletoBase(String nombre, String cedula, String tipo, LocalDate fecha, double precio) {
        super(nombre, cedula, tipo, fecha, precio);
    }
}
