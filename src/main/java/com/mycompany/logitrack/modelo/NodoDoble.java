package com.mycompany.logitrack.modelo;

public class NodoDoble {

    private Paquete paquete;
    private NodoDoble anterior;
    private NodoDoble siguiente;

    public NodoDoble(Paquete paquete) {
        this.paquete = paquete;
        this.anterior = null;
        this.siguiente = null;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public NodoDoble getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }
}