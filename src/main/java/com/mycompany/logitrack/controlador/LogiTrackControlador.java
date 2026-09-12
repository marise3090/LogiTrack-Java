package com.mycompany.logitrack.controlador;

import com.mycompany.logitrack.modelo.ColaDinamica;
import com.mycompany.logitrack.modelo.PilaDinamica;
import com.mycompany.logitrack.modelo.ListaSimpleCentros;

public class LogiTrackControlador {

    private static final LogiTrackControlador instancia =
            new LogiTrackControlador();

    private final ColaDinamica colaDespacho;
    private final PilaDinamica historialEntregas;
    private final ListaSimpleCentros listaCentros;

    private LogiTrackControlador() {
        colaDespacho = new ColaDinamica();
        historialEntregas = new PilaDinamica();
        listaCentros = new ListaSimpleCentros();
    }

    public static LogiTrackControlador getInstancia() {
        return instancia;
    }

    public ColaDinamica getColaDespacho() {
        return colaDespacho;
    }

    public PilaDinamica getHistorialEntregas() {
        return historialEntregas;
    }
    public ListaSimpleCentros getListaCentros() {
    return listaCentros;
}
}