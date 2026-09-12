package com.mycompany.logitrack;

import com.mycompany.logitrack.vista.FrmSeleccionRol;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmSeleccionRol().setVisible(true);
            }
        });
    }
}