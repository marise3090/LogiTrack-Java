package com.mycompany.logitrack.modelo;

import java.util.ArrayList;
import java.util.List;

public class PilaDinamica {

    private Nodo cima;

    public PilaDinamica() {
        cima = null;
    }

    public void apilar(Entrega entrega) {

        if (entrega == null) {
            return;
        }

        Nodo nuevo = new Nodo(entrega);
        nuevo.setSiguiente(cima);
        cima = nuevo;
    }

    public Entrega desapilar() {

        if (estaVacia()) {
            return null;
        }

        Entrega entrega = (Entrega) cima.getDato();
        cima = cima.getSiguiente();

        return entrega;
    }

    public Entrega verCima() {

        if (estaVacia()) {
            return null;
        }

        return (Entrega) cima.getDato();
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public List<Entrega> obtenerEntregas() {

        List<Entrega> entregas = new ArrayList<>();
        Nodo actual = cima;

        while (actual != null) {

            Entrega entrega = (Entrega) actual.getDato();
            entregas.add(entrega);

            actual = actual.getSiguiente();
        }

        return entregas;
    }

    public void limpiar() {
        cima = null;
    }

    public int obtenerCantidad() {

        int cantidad = 0;
        Nodo actual = cima;

        while (actual != null) {
            cantidad++;
            actual = actual.getSiguiente();
        }

        return cantidad;
    }
}