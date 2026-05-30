package com.mycompany.java_nonoy.model;

public class Mago extends Personaje {
    private int mana;

    public Mago(String nombre, int nivel, int puntosVida, int mana) {
        super(nombre, nivel, puntosVida);
        setMana(mana);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("[HABILIDAD] El Mago " + getNombre() + " lanza ¡Bola de Fuego! Desata un hechizo usando sus " + this.mana + " puntos de maná.");
    }


    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        if (mana >= 0) {
            this.mana = mana;
        } else {
            System.out.println("[ERROR] El maná no puede ser negativo. Se asignará 0.");
            this.mana = 0;
        }
    }
}