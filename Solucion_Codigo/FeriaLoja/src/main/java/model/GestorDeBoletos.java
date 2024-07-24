package model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorDeBoletos  {

    private List<EventoBase> eventos;
    private List<BoletoBase> boletosVendidos;

    private static final double PRECIO_BOLETO_NORMAL = 10.0;
    private static final double PRECIO_BOLETO_ESPECIAL = 20.0;

    public GestorDeBoletos() {
        eventos = new ArrayList<>();
        boletosVendidos = new ArrayList<>();
    }

    public void agregarEvento(EventoBase evento) {
        eventos.add(evento);
    }

    public EventoBase obtenerEventoPorFecha(LocalDate fecha) {
        for (EventoBase evento : eventos) {
            if (evento.getFecha().equals(fecha)) {
                return evento;
            }
        }
        return null;
    }

    public void venderBoleto(BoletoBase boleto) {
        boletosVendidos.add(boleto);
        EventoBase evento = obtenerEventoPorFecha(boleto.getFecha());
        if (evento != null) {
            evento.agregarBoleto(boleto);
        }
    }

    public boolean eliminarBoleto(String tipo, LocalDate fecha, double precio, String cedula) {
        for (BoletoBase boleto : boletosVendidos) {
            if (boleto.getTipo().equals(tipo) && boleto.getFecha().equals(fecha) && boleto.getPrecio() == precio && boleto.getCedula().equals(cedula)) {
                boletosVendidos.remove(boleto);
                EventoBase evento = obtenerEventoPorFecha(boleto.getFecha());
                if (evento != null) {
                    evento.getBoletos().remove(boleto);
                }
                return true;
            }
        }
        return false;
    }

    public List<BoletoBase> obtenerBoletosVendidos() {
        return boletosVendidos;
    }

    public List<EventoBase> obtenerEventos() {
        return eventos;
    }

    public List<BoletoBase> obtenerBoletosPorFecha(LocalDate fecha) {
        List<BoletoBase> boletosPorFecha = new ArrayList<>();
        for (BoletoBase boleto : boletosVendidos) {
            if (boleto.getFecha().equals(fecha)) {
                boletosPorFecha.add(boleto);
            }
        }
        return boletosPorFecha;
    }

    public double calcularGananciasTotales() {
        double total = 0;
        for (BoletoBase boleto : boletosVendidos) {
            total += boleto.getPrecio();
        }
        return total;
    }

    public double calcularPrecioBoleto(String tipo, LocalDate fecha) {
        double precio = tipo.equals("Especial") ? PRECIO_BOLETO_ESPECIAL : PRECIO_BOLETO_NORMAL;
        // Aplicar promociones para días específicos si es necesario
        if (tipo.equals("Especial") && (fecha.getDayOfWeek().getValue() == 5 || fecha.getDayOfWeek().getValue() == 6 || fecha.getDayOfWeek().getValue() == 7)) {
            precio *= 0.9;  // 10% de descuento los viernes, sábados y domingos
        }
        return precio;
    }

    public int obtenerAsistenciaPorEvento(String nombreEvento) {
        for (EventoBase evento : eventos) {
            if (evento.getNombre().equals(nombreEvento)) {
                return evento.getBoletos().size();
            }
        }
        return 0;
    }

    public void cargarEventosDesdeCSV(String nombreArchivo) throws IOException, CsvValidationException {
        try (CSVReader lector = new CSVReader(new FileReader(nombreArchivo))) {
            String[] linea;
            while ((linea = lector.readNext()) != null) {
                String nombre = linea[0];
                LocalDate fecha = LocalDate.parse(linea[1]);
                EventoBase evento = new EventoBase(nombre, fecha);
                eventos.add(evento);
            }
        }
    }

    public void cargarBoletosDesdeCSV(String nombreArchivo) throws IOException, CsvValidationException {
        try (CSVReader lector = new CSVReader(new FileReader(nombreArchivo))) {
            String[] linea;
            while ((linea = lector.readNext()) != null) {
                String tipo = linea[0];
                LocalDate fecha = LocalDate.parse(linea[1]);
                double precio = Double.parseDouble(linea[2]);
                String nombre = linea[3];
                String cedula = linea[4];
                BoletoBase boleto = new BoletoBase(nombre, cedula, tipo, fecha, precio);
                boletosVendidos.add(boleto);
                EventoBase evento = obtenerEventoPorFecha(fecha);
                if (evento != null) {
                    evento.agregarBoleto(boleto);
                }
            }
        }
    }

    public void guardarEventosEnCSV(String nombreArchivo) throws IOException {
        try (CSVWriter escritor = new CSVWriter(new FileWriter(nombreArchivo))) {
            for (EventoBase evento : eventos) {
                String[] linea = {evento.getNombre(), evento.getFecha().toString()};
                escritor.writeNext(linea);
            }
        }
    }

    public void guardarBoletosEnCSV(String nombreArchivo) throws IOException {
        try (CSVWriter escritor = new CSVWriter(new FileWriter(nombreArchivo))) {
            for (BoletoBase boleto : boletosVendidos) {
                String[] linea = {boleto.getTipo(), boleto.getFecha().toString(), String.valueOf(boleto.getPrecio()), boleto.getNombre(), boleto.getCedula()};
                escritor.writeNext(linea);
            }
        }
    }

    public EventoBase obtenerEventoPorNombre(String nombreEvento) {
        for (EventoBase evento : eventos) {
            if (evento.getNombre().equals(nombreEvento)) {
                return evento;
            }
        }
        return null;
    }

    public int calcularAsistenciaPorEvento(EventoBase evento) {
        return evento.getBoletos().size();
    }
}
