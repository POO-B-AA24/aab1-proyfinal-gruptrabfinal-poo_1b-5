package controller;

import model.GestorDeBoletos;
import view.VistaConsola;
import model.EventoBase;
import model.BoletoBase;

import java.time.LocalDate;
import java.util.List;

public class ControladorDeBoletos {

    private GestorDeBoletos gestorDeBoletos;
    private VistaConsola vistaConsola;

    public ControladorDeBoletos(GestorDeBoletos gestorDeBoletos, VistaConsola vistaConsola) {
        this.gestorDeBoletos = gestorDeBoletos;
        this.vistaConsola = vistaConsola;
    }

    public void crearEvento(String nombre, LocalDate fecha) {
        gestorDeBoletos.agregarEvento(new EventoBase(nombre, fecha));
        vistaConsola.mostrarMensaje("Evento creado: " + nombre + " en " + fecha);
    }

    public void venderBoleto(String tipo, LocalDate fecha, String nombre, String cedula) {
        double precio = gestorDeBoletos.calcularPrecioBoleto(tipo, fecha);
        BoletoBase boleto = new BoletoBase(nombre, cedula, tipo, fecha, precio);
        gestorDeBoletos.venderBoleto(boleto);
        vistaConsola.mostrarMensaje("Boleto vendido: " + tipo + " para " + fecha + " a " + nombre + " por " + precio);
           String factura = "----- FACTURA -----\n"
                + "Nombre: " + boleto.getNombre() + "\n"
                + "Cédula: " + boleto.getCedula() + "\n"
                + "Evento: " + boleto.getTipo() + "\n"
                + "Fecha: " + boleto.getFecha() + "\n"
                + "Precio: " + boleto.getPrecio() + "\n"
                + "-------------------";
        vistaConsola.mostrarMensaje(factura);
        
    }

    public void eliminarBoleto(String tipo, LocalDate fecha, double precio, String cedula) {
        boolean eliminado = gestorDeBoletos.eliminarBoleto(tipo, fecha, precio, cedula);
        if (eliminado) {
            vistaConsola.mostrarMensaje("Boleto eliminado.");
        } else {
            vistaConsola.mostrarMensaje("Boleto no encontrado.");
        }
    }

    public void mostrarEventos() {
        for (EventoBase evento : gestorDeBoletos.obtenerEventos()) {
            vistaConsola.mostrarMensaje("Evento: " + evento.getNombre() + " en " + evento.getFecha());
        }
    }

    public void mostrarBoletosVendidos() {
        for (BoletoBase boleto : gestorDeBoletos.obtenerBoletosVendidos()) {
            vistaConsola.mostrarMensaje("Boleto: " + boleto.getTipo() + " para " + boleto.getFecha() + " vendido a " + boleto.getNombre() + " por " + boleto.getPrecio());
        }
    }

    public void cargarEventos(String nombreArchivo) {
        try {
            gestorDeBoletos.cargarEventosDesdeCSV(nombreArchivo);
            vistaConsola.mostrarMensaje("Eventos cargados desde " + nombreArchivo);
        } catch (Exception e) {
            vistaConsola.mostrarMensaje("Error al cargar eventos: " + e.getMessage());
        }
    }

    public void cargarBoletos(String nombreArchivo) {
        try {
            gestorDeBoletos.cargarBoletosDesdeCSV(nombreArchivo);
            vistaConsola.mostrarMensaje("Boletos cargados desde " + nombreArchivo);
        } catch (Exception e) {
            vistaConsola.mostrarMensaje("Error al cargar boletos: " + e.getMessage());
        }
    }

    public void guardarEventos(String nombreArchivo) {
        try {
            gestorDeBoletos.guardarEventosEnCSV(nombreArchivo);
            vistaConsola.mostrarMensaje("Eventos guardados en " + nombreArchivo);
        } catch (Exception e) {
            vistaConsola.mostrarMensaje("Error al guardar eventos: " + e.getMessage());
        }
    }

    public void guardarBoletos(String nombreArchivo) {
        try {
            gestorDeBoletos.guardarBoletosEnCSV(nombreArchivo);
            vistaConsola.mostrarMensaje("Boletos guardados en " + nombreArchivo);
        } catch (Exception e) {
            vistaConsola.mostrarMensaje("Error al guardar boletos: " + e.getMessage());
        }
    }

    public void mostrarEventosDisponibles() {
        List<EventoBase> eventos = gestorDeBoletos.obtenerEventos();
        if (eventos.isEmpty()) {
            vistaConsola.mostrarMensaje("No hay eventos disponibles.");
        } else {
            for (EventoBase evento : eventos) {
                vistaConsola.mostrarMensaje("Evento: " + evento.getNombre() + " en " + evento.getFecha());
            }
        }
    }

    public void mostrarBoletosPorFecha(LocalDate fecha) {
        List<BoletoBase> boletos = gestorDeBoletos.obtenerBoletosPorFecha(fecha);
        if (boletos.isEmpty()) {
            vistaConsola.mostrarMensaje("No hay boletos vendidos para la fecha " + fecha);
        } else {
            for (BoletoBase boleto : boletos) {
                vistaConsola.mostrarMensaje("Boleto: " + boleto.getTipo() + " vendido a " + boleto.getNombre() + " por " + boleto.getPrecio());
            }
        }
    }

    public void mostrarGananciasTotales() {
        double gananciasTotales = gestorDeBoletos.calcularGananciasTotales();
        vistaConsola.mostrarMensaje("Ganancias totales: " + gananciasTotales);
    }

    public void mostrarAsistenciaPorEvento(String nombreEvento) {
        EventoBase evento = gestorDeBoletos.obtenerEventoPorNombre(nombreEvento);
        if (evento == null) {
            vistaConsola.mostrarMensaje("Evento no encontrado.");
        } else {
            int asistencia = gestorDeBoletos.calcularAsistenciaPorEvento(evento);
            vistaConsola.mostrarMensaje("Asistencia al evento '" + nombreEvento + "': " + asistencia);
        }
    }

    
}
