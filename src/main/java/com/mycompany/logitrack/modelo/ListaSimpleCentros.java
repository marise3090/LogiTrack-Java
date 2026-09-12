package com.mycompany.logitrack.modelo;

import java.util.ArrayList;
import java.util.List;

public class ListaSimpleCentros {

    private Nodo primero;

    public ListaSimpleCentros() {
        primero = null;
    }
    
    public CentroDistribucion buscarRecursivo(int id) {
    return buscarRecursivo(primero, id);
}

private CentroDistribucion buscarRecursivo(Nodo actual, int id) {

    if (actual == null) {
        return null;
    }

    CentroDistribucion centro =
            (CentroDistribucion) actual.getDato();

    if (centro.getId() == id) {
        return centro;
    }

    return buscarRecursivo(actual.getSiguiente(), id);
}

    public void agregar(CentroDistribucion centro) {

        Nodo nuevo = new Nodo(centro);

        if (primero == null) {
            primero = nuevo;
            return;
        }

        Nodo actual = primero;

        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

        actual.setSiguiente(nuevo);
    }

    public CentroDistribucion buscar(int id) {

        Nodo actual = primero;

        while (actual != null) {

            CentroDistribucion centro =
                    (CentroDistribucion) actual.getDato();

            if (centro.getId() == id) {
                return centro;
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    public boolean modificar(
            int id,
            String nuevoNombre,
            String nuevaUbicacion) {

        CentroDistribucion centro = buscar(id);

        if (centro == null) {
            return false;
        }

        centro.setNombre(nuevoNombre);
        centro.setUbicacion(nuevaUbicacion);

        return true;
    }

    public boolean eliminar(int id) {

        if (primero == null) {
            return false;
        }

        CentroDistribucion primerCentro =
                (CentroDistribucion) primero.getDato();

        if (primerCentro.getId() == id) {
            primero = primero.getSiguiente();
            return true;
        }

        Nodo anterior = primero;
        Nodo actual = primero.getSiguiente();

        while (actual != null) {

            CentroDistribucion centro =
                    (CentroDistribucion) actual.getDato();

            if (centro.getId() == id) {
                anterior.setSiguiente(actual.getSiguiente());
                return true;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }

    public List<CentroDistribucion> obtenerCentros() {

        List<CentroDistribucion> centros = new ArrayList<>();
        Nodo actual = primero;

        while (actual != null) {

            CentroDistribucion centro =
                    (CentroDistribucion) actual.getDato();

            centros.add(centro);

            actual = actual.getSiguiente();
        }

        return centros;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public int obtenerCantidad() {

        int cantidad = 0;
        Nodo actual = primero;

        while (actual != null) {
            cantidad++;
            actual = actual.getSiguiente();
        }

        return cantidad;
    }
    
    public void limpiar() {
    primero = null;
}
    
    
}