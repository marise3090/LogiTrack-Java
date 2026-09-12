package com.mycompany.logitrack.modelo;

import java.util.ArrayList;
import java.util.List;

public class ColaDinamica {

    private Nodo frente;
    private Nodo fin;

    public ColaDinamica() {
        this.frente = null;
        this.fin = null;
    }

    public void encolar(Paquete paquete) {

        if (paquete == null) {
            return;
        }

        Nodo nuevoNodo = new Nodo(paquete);

        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
    }

    public Paquete desencolar() {

        if (estaVacia()) {
            return null;
        }

        Paquete paquete = (Paquete) frente.getDato();
        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        return paquete;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    /*
     * Devuelve los paquetes como objetos.
     * La interfaz ya no necesita convertir texto usando split().
     */
    public List<Paquete> obtenerPaquetes() {

        List<Paquete> paquetes = new ArrayList<>();
        Nodo actual = frente;

        while (actual != null) {

            Paquete paquete = (Paquete) actual.getDato();
            paquetes.add(paquete);

            actual = actual.getSiguiente();
        }

        return paquetes;
    }

    public void limpiar() {
        frente = null;
        fin = null;
    }

    public int obtenerCantidad() {

        int cantidad = 0;
        Nodo actual = frente;

        while (actual != null) {
            cantidad++;
            actual = actual.getSiguiente();
        }

        return cantidad;
    }
}