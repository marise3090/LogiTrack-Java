package com.mycompany.logitrack.modelo;

import java.util.ArrayList;
import java.util.List;

public class ListaDoblePaquetes {

    private NodoDoble primero;
    private NodoDoble ultimo;
    private NodoDoble actual;

    public ListaDoblePaquetes() {
        primero = null;
        ultimo = null;
        actual = null;
    }

    public void agregar(Paquete paquete) {

        if (paquete == null) {
            return;
        }

        NodoDoble nuevo = new NodoDoble(paquete);

        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
            actual = nuevo;
            return;
        }

        ultimo.setSiguiente(nuevo);
        nuevo.setAnterior(ultimo);
        ultimo = nuevo;
    }

    public Paquete obtenerActual() {

        if (actual == null) {
            return null;
        }

        return actual.getPaquete();
    }

    public Paquete siguiente() {

        if (actual == null || actual.getSiguiente() == null) {
            return null;
        }

        actual = actual.getSiguiente();
        return actual.getPaquete();
    }

    public Paquete anterior() {

        if (actual == null || actual.getAnterior() == null) {
            return null;
        }

        actual = actual.getAnterior();
        return actual.getPaquete();
    }

    public void irAlInicio() {
        actual = primero;
    }

    public boolean eliminarActual() {

        if (actual == null) {
            return false;
        }

        NodoDoble anterior = actual.getAnterior();
        NodoDoble siguiente = actual.getSiguiente();

        if (anterior != null) {
            anterior.setSiguiente(siguiente);
        } else {
            primero = siguiente;
        }

        if (siguiente != null) {
            siguiente.setAnterior(anterior);
        } else {
            ultimo = anterior;
        }

        if (siguiente != null) {
            actual = siguiente;
        } else {
            actual = anterior;
        }

        return true;
    }

    /**
     * Busca un paquete por ID utilizando recursividad.
     * Caso base: nodo nulo o paquete encontrado.
     * Caso recursivo: continuar con el siguiente nodo.
     */
    public Paquete buscarRecursivo(int id) {
        return buscarRecursivo(primero, id);
    }

    private Paquete buscarRecursivo(NodoDoble nodo, int id) {
        if (nodo == null) {
            return null;
        }

        if (nodo.getPaquete().getId() == id) {
            actual = nodo;
            return nodo.getPaquete();
        }

        return buscarRecursivo(nodo.getSiguiente(), id);
    }

    public Paquete buscar(int id) {

        NodoDoble recorrido = primero;

        while (recorrido != null) {

            Paquete paquete = recorrido.getPaquete();

            if (paquete.getId() == id) {
                return paquete;
            }

            recorrido = recorrido.getSiguiente();
        }

        return null;
    }

    public List<Paquete> obtenerPaquetes() {

        List<Paquete> paquetes = new ArrayList<>();
        NodoDoble recorrido = primero;

        while (recorrido != null) {
            paquetes.add(recorrido.getPaquete());
            recorrido = recorrido.getSiguiente();
        }

        return paquetes;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public int obtenerCantidad() {

        int cantidad = 0;
        NodoDoble recorrido = primero;

        while (recorrido != null) {
            cantidad++;
            recorrido = recorrido.getSiguiente();
        }

        return cantidad;
    }
}