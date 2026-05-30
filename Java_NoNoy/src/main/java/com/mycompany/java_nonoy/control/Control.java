package com.mycompany.java_nonoy.controller;

import com.mycompany.java_nonoy.model.*;
import com.mycompany.java_nonoy.view.Vista;
import java.util.ArrayList;

public class Control {
    private final Vista vista;
    private final ArrayList<Personaje> BD_Simulada;

    public Control(Vista vista) {
        this.vista = vista;
        this.BD_Simulada = new ArrayList<>();
        
        BD_Simulada.add(new Guerrero("Andres", 10, 100, 50));
        BD_Simulada.add(new Mago("Cesar", 12, 80, 150));
    }

    public void iniciar() {
        int opcion = 0;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1 -> crearPersonaje();
                case 2 -> listarPersonajes();
                case 3 -> buscarPersonaje();
                case 4 -> actualizarPersonaje();
                case 5 -> eliminarPersonaje();
                case 6 -> usarHabilidadEspecial();
                case 7 -> vista.mostrarMensaje("Saliendo del sistema de gestión RPG...");
                default -> vista.mostrarError("[ERROR] Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    private void crearPersonaje() {
        vista.mostrarMensaje("\n--- CREAR NUEVO PERSONAJE ---");
        vista.mostrarMensaje("1. Guerrero | 2. Mago | 3. Arquero");
        int tipo = vista.pedirEntero("Seleccione el tipo de rol: ");

        String nombre = vista.pedirTexto("Ingrese el nombre: ");
        int nivel = vista.pedirEntero("Ingrese el nivel: ");
        int vida = vista.pedirEntero("Ingrese puntos de vida: ");

        try {
            if (nombre.equalsIgnoreCase("fail")) {
                throw new Exception("Error de conexión fallida con el sistema de almacenamiento.");
            }

            switch (tipo) {
                case 1 -> {
                    int fuerza = vista.pedirEntero("Ingrese la fuerza del Guerrero: ");
                    BD_Simulada.add(new Guerrero(nombre, nivel, vida, fuerza));
                }
                case 2 -> {
                    int mana = vista.pedirEntero("Ingrese el maná del Mago: ");
                    BD_Simulada.add(new Mago(nombre, nivel, vida, mana));
                }
                case 3 -> {
                    int destreza = vista.pedirEntero("Ingrese la destreza del Arquero: ");
                    BD_Simulada.add(new Arquero(nombre, nivel, vida, destreza));
                }
                default -> {
                    vista.mostrarError("[ERROR] Tipo de rol no reconocido.");
                    return;
                }
            }
            vista.mostrarMensaje("[ÉXITO] Personaje registrado en la capa de persistencia.");
        } catch (Exception e) {
            vista.mostrarError("[Manejo de Error] " + e.getMessage());
        }
    }

    private void listarPersonajes() {
        vista.mostrarMensaje("\n--- LISTA DE PERSONAJES REGISTRADOS ---");
        if (BD_Simulada.isEmpty()) {
            vista.mostrarMensaje("No hay ningún personaje en la base de datos.");
            return;
        }
        for (int i = 0; i < BD_Simulada.size(); i++) {
            Personaje p = BD_Simulada.get(i);
            vista.mostrarMensaje("[" + i + "] Tipo: " + p.getClass().getSimpleName() + 
                                  " | Nombre: " + p.getNombre() + 
                                  " | Nivel: " + p.getNivel() + 
                                  " | Vida: " + p.getPuntosVida());
        }
    }

    private void buscarPersonaje() {
        vista.mostrarMensaje("\n--- BUSCAR PERSONAJE ---");
        String nombre = vista.pedirTexto("Ingrese el nombre a buscar: ");
        
        try {
            Personaje encontrado = localizarPorNombre(nombre);
            vista.mostrarMensaje("[ENCONTRADO] Es un " + encontrado.getClass().getSimpleName() + 
                                  " llamado " + encontrado.getNombre() + " con Nivel " + encontrado.getNivel());
        } catch (Exception e) {
            vista.mostrarError("[Manejo de Error] " + e.getMessage());
        }
    }

    private void actualizarPersonaje() {
        vista.mostrarMensaje("\n--- ACTUALIZAR DATOS ---");
        String nombre = vista.pedirTexto("Ingrese el nombre del personaje que desea modificar: ");
        
        try {
            Personaje p = localizarPorNombre(nombre);
            vista.mostrarMensaje("Modificando a " + p.getNombre());
            
            int nuevoNivel = vista.pedirEntero("Ingrese nuevo nivel: ");
            int nuevaVida = vista.pedirEntero("Ingrese nuevos puntos de vida: ");
            
            p.setNivel(nuevoNivel);
            p.setPuntosVida(nuevaVida);
            vista.mostrarMensaje("[ÉXITO] Datos actualizados correctamente.");
        } catch (Exception e) {
            vista.mostrarError("[Manejo de Error] " + e.getMessage());
        }
    }

    private void eliminarPersonaje() {
        vista.mostrarMensaje("\n--- ELIMINAR PERSONAJE ---");
        String nombre = vista.pedirTexto("Ingrese el nombre del personaje a borrar: ");
        
        try {
            Personaje p = localizarPorNombre(nombre);
            BD_Simulada.remove(p);
            vista.mostrarMensaje("[ÉXITO] El personaje fue removido de la base de datos.");
        } catch (Exception e) {
            vista.mostrarError("[Manejo de Error] " + e.getMessage());
        }
    }

    private void usarHabilidadEspecial() {
        vista.mostrarMensaje("\n--- DESATAR HABILIDADES ESPECIALES ---");
        if (BD_Simulada.isEmpty()) {
            vista.mostrarMensaje("No hay personajes para atacar.");
            return;
        }
        for (Personaje p : BD_Simulada) {
            p.habilidadEspecial(); 
        }
    }

    private Personaje localizarPorNombre(String nombre) throws Exception {
        for (Personaje p : BD_Simulada) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        throw new Exception("ID/Nombre '" + nombre + "' no encontrado en el sistema.");
    }
}