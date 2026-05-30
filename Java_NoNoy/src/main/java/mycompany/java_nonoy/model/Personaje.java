package com.mycompany.java_nonoy.model;

public class Personaje {
    
    private String nombre;
    private int nivel;
    private int puntosVida;

    public Personaje(String nombre, int nivel, int puntosVida) {
        setNombre(nombre);
        setNivel(nivel);
        setPuntosVida(puntosVida);
    }


    public void habilidadEspecial() {
        System.out.println(this.nombre + " realiza una acción básica.");
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("[ERROR] El nombre no puede estar vacío.");
            this.nombre = "Héroe Genérico";
        }
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if (nivel >= 1 && nivel <= 100) {
            this.nivel = nivel;
        } else {
            System.out.println("[ERROR] Nivel inválido (debe ser de 1 a 100). Se asignará nivel 1.");
            this.nivel = 1; 
        }
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        if (puntosVida >= 0) {
            this.puntosVida = puntosVida;
        } else {
            System.out.println("[ERROR] Los puntos de vida no pueden ser negativos. Se asignará 0.");
            this.puntosVida = 0;
        }
    }
}