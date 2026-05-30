package com.mycompany.java_nonoy;

public class Guerrero extends Personaje {
    private int fuerza;

    // Constructor que invoca al padre usando 'super'
    public Guerrero(String nombre, int nivel, int puntosVida, int fuerza) {
        super(nombre, nivel, puntosVida);
        this.fuerza = fuerza;
    }

    // Getter y Setter específico
    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }
}