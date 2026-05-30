package com.mycompany.java_nonoy;

public class Arquero extends Personaje {
    private int destreza;

    public Arquero(String nombre, int nivel, int puntosVida, int destreza) {
        super(nombre, nivel, puntosVida);
        this.destreza = destreza;
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("[HABILIDAD] El Arquero " + getNombre() + " ejecuta ¡Disparo Certero! Precisión basada en " + this.destreza + " de destreza.");
    }
    
    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }
}