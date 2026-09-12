package com.mycompany.logitrack.modelo;

public class Entrega {

    private int idPaquete;
    private String transportista;
    private String fecha;
    private String resultado;

    public Entrega(int idPaquete, String transportista, String fecha, String resultado) {
        this.idPaquete = idPaquete;
        this.transportista = transportista;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public String getTransportista() {
        return transportista;
    }

    public String getFecha() {
        return fecha;
    }

    public String getResultado() {
        return resultado;
    }
}