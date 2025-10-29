package ort.da.DAObligatorio.Servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Vehiculo;

public class ServicioUsuarios {
    private List<Propietario> propietarios;
    private List<Administrador> administradores;
    //poner lista de sesiones si hace falta, crear la clase etc etc

    public ServicioUsuarios() {
        // Inicializar listas y otros atributos si es necesario
        this.propietarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    public void agregarPropietario(Propietario propietario) throws PeajeException{
        if(propietario == null) return;

        for(Vehiculo v : propietario.getVehiculos()){
            if(buscarPropietarioPorMatricula(v.getMatricula()) != null) { 
                throw new PeajeException("Ya existe un propietario con un vehiculo de la misma matricula: " + v.getMatricula());
            }
        }
        propietarios.add(propietario);
    }

    public void agregarAdministrador(Administrador administrador) {
        if(administrador == null) return;
        administradores.add(administrador);
    }

    // metodo para buscar propietario por matricula
    public Propietario buscarPropietarioPorMatricula(String matricula) {
        if(matricula == null || matricula.isEmpty()) {
            return null;
        }
        for (Propietario p : propietarios) {
            if(p.tieneVehiculoConMatricula(matricula)){
                return p;
            }
        }
        return null; // Retorna null si no se encuentra ningún propietario con esa matrícula
    }
    
}
