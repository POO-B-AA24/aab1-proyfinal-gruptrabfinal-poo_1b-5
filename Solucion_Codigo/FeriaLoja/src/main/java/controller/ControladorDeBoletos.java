package controller;

import model.GestorDeBoletos;
import view.VistaConsola;
import model.Evento;
import model.Boleto;

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
        gestorDeBoletos.agregarEvento(new Evento(nombre, fecha));
        vistaConsola.mostrarMensaje("Evento creado: " + nombre + " en " + fecha);
    }

    public void venderBoleto(String tipo, LocalDate fecha, String nombre, String cedula) {
        double precio = gestorDeBoletos.calcularPrecioBoleto(tipo, fecha);
        Boleto boleto = new Boleto(tipo, fecha, precio, nombre, cedula);
        gestorDeBoletos.venderBoleto(boleto);
        vistaConsola.mostrarMensaje("Boleto vendido: " + tipo + " para " + fecha + " a " + nombre + " por " + precio);
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
        for (Evento evento : gestorDeBoletos.obtenerEventos()) {
            vistaConsola.mostrarMensaje("Evento: " + evento.getNombre() + " en " + evento.getFecha());
        }
    }

    public void mostrarBoletosVendidos() {
        for (Boleto boleto : gestorDeBoletos.obtenerBoletosVendidos()) {
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
        List<Evento> eventos = gestorDeBoletos.obtenerEventos();
        if (eventos.isEmpty()) {
            vistaConsola.mostrarMensaje("No hay eventos disponibles.");
        } else {
            for (Evento evento : eventos) {
                vistaConsola.mostrarMensaje("Evento: " + evento.getNombre() + " en " + evento.getFecha());
            }
        }
    }

    public void mostrarBoletosPorFecha(LocalDate fecha) {
        List<Boleto> boletos = gestorDeBoletos.obtenerBoletosPorFecha(fecha);
        if (boletos.isEmpty()) {
            vistaConsola.mostrarMensaje("No hay boletos vendidos para la fecha " + fecha);
        } else {
            for (Boleto boleto : boletos) {
                vistaConsola.mostrarMensaje("Boleto: " + boleto.getTipo() + " vendido a " + boleto.getNombre() + " por " + boleto.getPrecio());
            }
        }
    }

    public void mostrarGananciasTotales() {
        double gananciasTotales = gestorDeBoletos.calcularGananciasTotales();
        vistaConsola.mostrarMensaje("Ganancias totales: " + gananciasTotales);
    }

    public void mostrarAsistenciaPorEvento(String nombreEvento) {
        Evento evento = gestorDeBoletos.obtenerEventoPorNombre(nombreEvento);
        if (evento == null) {
            vistaConsola.mostrarMensaje("Evento no encontrado.");
        } else {
            int asistencia = gestorDeBoletos.calcularAsistenciaPorEvento(evento);
            vistaConsola.mostrarMensaje("Asistencia al evento '" + nombreEvento + "': " + asistencia);
        }
    }
}
