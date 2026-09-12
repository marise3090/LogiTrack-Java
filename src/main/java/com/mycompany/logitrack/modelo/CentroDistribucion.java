package com.mycompany.logitrack.modelo;

public class CentroDistribucion {

    private int id;
    private String nombre;
    private String ubicacion;

    private ListaDoblePaquetes listaPaquetes;

    public CentroDistribucion(int id, String nombre, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.listaPaquetes = new ListaDoblePaquetes();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ListaDoblePaquetes getListaPaquetes() {
        return listaPaquetes;
    }

    public void setListaPaquetes(ListaDoblePaquetes listaPaquetes) {
        this.listaPaquetes = listaPaquetes;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}