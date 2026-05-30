package com.mycompany.java_nonoy.model;

public class Arquero extends Personaje {
    private int destreza;

    public Arquero(String nombre, int nivel, int puntosVida, int destreza) {
        super(nombre, nivel, puntosVida);
        setDestreza(destreza);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("[HABILIDAD] El Arquero " + getNombre() + " ejecuta ¡Disparo Certero! Impacto crítico garantizado por su destreza de " + this.destreza + ".");
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        if (destreza >= 0) {
            this.destreza = destreza;
        } else {
            System.out.println("[ERROR] La destreza no puede ser negativa. Se asignará 0.");
            this.destreza = 0;
        }
    }
}