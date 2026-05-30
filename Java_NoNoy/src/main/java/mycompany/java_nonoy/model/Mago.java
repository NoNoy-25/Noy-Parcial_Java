package com.mycompany.java_nonoy;

public class Mago extends Personaje {
    private int mana;

    public Mago(String nombre, int nivel, int puntosVida, int mana) {
        super(nombre, nivel, puntosVida);
        this.mana = mana;
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("[HABILIDAD] El Mago " + getNombre() + " lanza ¡Bola de Fuego! Consume " + this.mana + " de maná.");
    }
    
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}