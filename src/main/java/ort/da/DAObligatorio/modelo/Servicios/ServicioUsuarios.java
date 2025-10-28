package ort.da.DAObligatorio.modelo.Servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Propietario;

public class ServicioUsuarios {
    private List<Propietario> propietarios;
    private List<Administrador> administradores;
    //poner lista de sesiones si hace falta, crear la clase etc etc

    public ServicioUsuarios() {
        // Inicializar listas y otros atributos si es necesario
        this.propietarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    public void agregarPropietario(Propietario propietario) {
        propietarios.add(propietario);
    }

    public void agregarAdministrador(Administrador administrador) {
        administradores.add(administrador);
    }

    
}
