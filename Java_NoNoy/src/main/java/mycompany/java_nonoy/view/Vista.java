package com.mycompany.java_nonoy.view;

import java.util.Scanner;

public class Vista {
    private final Scanner scanner;

    public Vista() {
        this.scanner = new Scanner(System.in);
    }

    public int mostrarMenu() {
        System.out.println("\n----------------------------------------");
        System.out.println("       INICIA LA AVENTURA        ");
        System.out.println("-----------------------------------------");
        System.out.println("1. Crear un nuevo Personaje");
        System.out.println("2. Listar todos los Personajes");
        System.out.println("3. Buscar un Personaje por Nombre");
        System.out.println("4. Actualizar datos de un Personaje");
        System.out.println("5. Eliminar un Personaje");
        System.out.println("6. Ejecutar Habilidad Especial");
        System.out.println("7. Salir del programa");
        System.out.println("-----------------------------------");
        System.out.print("Seleccione una opcion (1-7): ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int pedirEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Ingrese un numero entero valido.");
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}