package com.mycompany.logitrack.modelo;

public class Paquete {

    private int id;
    private String descripcion;
    private String destino;
    private String estado;

    public Paquete(int id, String descripcion, String destino, String estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.destino = destino;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDestino() {
        return destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}