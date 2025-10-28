package ort.da.DAObligatorio.modelo.Servicios.Fachada;

import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Servicios.ServicioUsuarios;

public class Fachada {
    
    private static Fachada instancia;
    private ServicioUsuarios sUsuarios;
    // Otros atributos y servicios que maneja la fachada

    private Fachada() {
        // Inicialización de servicios y datos
        sUsuarios = new ServicioUsuarios();
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    public void agregarPropietario(Propietario propietario) {
        sUsuarios.agregarPropietario(propietario);
    }

    public void agregarAdministrador(Administrador administrador) {
        sUsuarios.agregarAdministrador(administrador);
    }



    
}
