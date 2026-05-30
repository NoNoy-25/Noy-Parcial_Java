package com.mycompany.java_nonoy.model;

public class Guerrero extends Personaje {
    private int fuerza;


    public Guerrero(String nombre, int nivel, int puntosVida, int fuerza) {
        super(nombre, nivel, puntosVida);
        setFuerza(fuerza);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("[HABILIDAD] El Guerrero " + getNombre() + " usa ¡Furia de Batalla! Su fuerza de " + this.fuerza + " rompe defensas.");
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        if (fuerza >= 0) {
            this.fuerza = fuerza;
        } else {
            System.out.println("[ERROR] La fuerza no puede ser negativa. Se asignará 0.");
            this.fuerza = 0;
        }
    }
}