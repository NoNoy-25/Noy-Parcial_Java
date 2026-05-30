package com.mycompany.java_nonoy.controller;

import com.mycompany.java_nonoy.model.*;
import com.mycompany.java_nonoy.view.Vista;
import java.sql.*;

public class Control {
    private final Vista vista;

    public Control(Vista vista) {
        this.vista = vista;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1 -> crearPersonaje();
                case 2 -> listarPersonajes();
                case 3 -> buscarPersonaje();
                case 4 -> actualizarPersonaje();
                case 5 -> eliminarPersonaje();
                case 6 -> usarHabilidadEspecial();
                case 7 -> vista.mostrarMensaje("Saliendo del sistema...");
                default -> vista.mostrarError("[ERROR] Opcion invalida.");
            }
        } while (opcion != 7);
    }

    private void crearPersonaje() {
        vista.mostrarMensaje("\n--- CREAR E INSERTAR EN MYSQL ---");
        vista.mostrarMensaje("1. Guerrero | 2. Mago | 3. Arquero");
        int tipo = vista.pedirEntero("Seleccione el tipo de rol: ");

        String nombre = vista.pedirTexto("Ingrese el nombre: ");
        int nivel = vista.pedirEntero("Ingrese el nivel: ");
        int vida = vista.pedirEntero("Ingrese puntos de vida: ");

        Connection con = null;
        try {
            con = ConexionBD.obtenerConexion();
            con.setAutoCommit(false);

            String sqlPadre = "INSERT INTO personajes (nombre, nivel, puntos_vida) VALUES (?, ?, ?)";
            PreparedStatement psPadre = con.prepareStatement(sqlPadre, Statement.RETURN_GENERATED_KEYS);
            psPadre.setString(1, nombre);
            psPadre.setInt(2, nivel);
            psPadre.setInt(3, vida);
            psPadre.executeUpdate();

            ResultSet rsKeys = psPadre.getGeneratedKeys();
            int idGenerado = 0;
            if (rsKeys.next()) {
                idGenerado = rsKeys.getInt(1);
            }

            switch (tipo) {
                case 1 -> {
                    int fuerza = vista.pedirEntero("Ingrese la fuerza del Guerrero: ");
                    String sqlHija = "INSERT INTO guerreros (personaje_id, fuerza) VALUES (?, ?)";
                    PreparedStatement psHija = con.prepareStatement(sqlHija);
                    psHija.setInt(1, idGenerado);
                    psHija.setInt(2, fuerza);
                    psHija.executeUpdate();
                }
                case 2 -> {
                    int mana = vista.pedirEntero("Ingrese el maná del Mago: ");
                    String sqlHija = "INSERT INTO magos (personaje_id, mana) VALUES (?, ?)";
                    PreparedStatement psHija = con.prepareStatement(sqlHija);
                    psHija.setInt(1, idGenerado);
                    psHija.setInt(2, mana);
                    psHija.executeUpdate();
                }
                case 3 -> {
                    int destreza = vista.pedirEntero("Ingrese la destreza del Arquero: ");
                    String sqlHija = "INSERT INTO arqueros (personaje_id, destreza) VALUES (?, ?)";
                    PreparedStatement psHija = con.prepareStatement(sqlHija);
                    psHija.setInt(1, idGenerado);
                    psHija.setInt(2, destreza);
                    psHija.executeUpdate();
                }
                default -> throw new Exception("Rol no válido.");
            }

            con.commit();
            vista.mostrarMensaje("[EXITO] Personaje guardado en MySQL.");
        } catch (SQLException e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) {}
            vista.mostrarError("[MANEJO DE ERROR - CONEXION] " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("[ERROR] " + e.getMessage());
        } finally {
            if (con != null) try { con.close(); } catch (SQLException e) {}
        }
    }

    private void listarPersonajes() {
        vista.mostrarMensaje("\n--- PERSONAJES DESDE MYSQL ---");
        String sql = "SELECT id, nombre, nivel, puntos_vida FROM personajes";

        try (Connection con = ConexionBD.obtenerConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean tieneDatos = false;
            while (rs.next()) {
                tieneDatos = true;
                vista.mostrarMensaje("ID: " + rs.getInt("id") + 
                                      " | Nombre: " + rs.getString("nombre") + 
                                      " | Nivel: " + rs.getInt("nivel") + 
                                      " | Vida: " + rs.getInt("puntos_vida"));
            }
            if (!tieneDatos) vista.mostrarMensaje("La base de datos está vacía.");
        } catch (SQLException e) {
            vista.mostrarError("[MANEJO DE ERROR] No se pudo listar: " + e.getMessage());
        }
    }

  
    private void buscarPersonaje() {
        vista.mostrarMensaje("\n--- BUSCAR PERSONAJE EN MYSQL ---");
        String nombreBuscar = vista.pedirTexto("Ingrese el nombre exacto: ");
        String sql = "SELECT * FROM personajes WHERE nombre = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombreBuscar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vista.mostrarMensaje("[ENCONTRADO] ID: " + rs.getInt("id") + 
                                          " | Nombre: " + rs.getString("nombre") + 
                                          " | Nivel: " + rs.getInt("nivel"));
                } else {
                    throw new Exception("El nombre '" + nombreBuscar + "' no existe en los registros.");
                }
            }
        } catch (SQLException e) {
            vista.mostrarError("[MANEJO DE ERROR - CONEXIÓN] " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("[MANEJO DE ERROR - INEXISTENTE] " + e.getMessage());
        }
    }

    private void actualizarPersonaje() {
        vista.mostrarMensaje("\n--- ACTUALIZAR NIVEL ---");
        int id = vista.pedirEntero("Ingrese el ID del personaje a modificar: ");
        int nuevoNivel = vista.pedirEntero("Ingrese el nuevo nivel: ");

        String sql = "UPDATE personajes SET nivel = ? WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, nuevoNivel);
            ps.setInt(2, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                vista.mostrarMensaje("[EXITO] Nivel actualizado en la base de datos.");
            } else {
                throw new Exception("No se encontró ningun personaje con el ID: " + id);
            }
        } catch (SQLException e) {
            vista.mostrarError("[MANEJO DE ERROR] Fallo la actualizacion: " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("[MANEJO DE ERROR - ID INEXISTENTE] " + e.getMessage());
        }
    }

    private void eliminarPersonaje() {
        vista.mostrarMensaje("\n--- ELIMINAR PERSONAJE DESDE MYSQL ---");
        int id = vista.pedirEntero("Ingrese el ID del personaje a eliminar: ");

        String sql = "DELETE FROM personajes WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                vista.mostrarMensaje("[EXITO] El personaje fue borrado de MySQL.");
            } else {
                throw new Exception("No se pudo eliminar. El ID " + id + " no existe.");
            }
        } catch (SQLException e) {
            vista.mostrarError("[MANEJO DE ERROR] Fallo la eliminacion: " + e.getMessage());
        } catch (Exception e) {
            vista.mostrarError("[MANEJO DE ERROR - ID INEXISTENTE] " + e.getMessage());
        }
    }

    private void usarHabilidadEspecial() {
        vista.mostrarMensaje("\n--- POLIMORFISMO: EJECUTANDO HABILIDADES DESDE BD ---");
        
        String sqlGuerreros = "SELECT p.nombre, p.nivel, p.puntos_vida, g.fuerza FROM personajes p JOIN guerreros g ON p.id = g.personaje_id";
        String sqlMagos = "SELECT p.nombre, p.nivel, p.puntos_vida, m.mana FROM personajes p JOIN magos m ON p.id = m.personaje_id";
        String sqlArqueros = "SELECT p.nombre, p.nivel, p.puntos_vida, a.destreza FROM personajes p JOIN arqueros a ON p.id = a.personaje_id";

        try (Connection con = ConexionBD.obtenerConexion()) {
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sqlGuerreros)) {
                while (rs.next()) {
                    new Guerrero(rs.getString("nombre"), rs.getInt("nivel"), rs.getInt("puntos_vida"), rs.getInt("fuerza")).habilidadEspecial();
                }
            }
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sqlMagos)) {
                while (rs.next()) {
                    new Mago(rs.getString("nombre"), rs.getInt("nivel"), rs.getInt("puntos_vida"), rs.getInt("mana")).habilidadEspecial();
                }
            }
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sqlArqueros)) {
                while (rs.next()) {
                    new Arquero(rs.getString("nombre"), rs.getInt("nivel"), rs.getInt("puntos_vida"), rs.getInt("destreza")).habilidadEspecial();
                }
            }
        } catch (SQLException e) {
            vista.mostrarError("[MANEJO DE ERROR] Error al procesar habilidades: " + e.getMessage());
        }
    }
}
