package com.mycompany.java_nonoy;

import com.mycompany.java_nonoy.controller.Control;
import com.mycompany.java_nonoy.view.Vista;

public class Java_NoNoy {
    public static void main(String[] args) {
        Vista vista = new Vista();
        Control con = new Control(vista);
        con.iniciar();
    }
}