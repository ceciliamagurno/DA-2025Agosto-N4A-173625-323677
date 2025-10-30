package ort.da.DAObligatorio.Servicios;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Bonificacion;
import ort.da.DAObligatorio.modelo.Estado;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Usuario;
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

    public Usuario autenticar(String cedula, String contrasenia) {
        // Buscar en propietarios
        for (Propietario p : propietarios) {
            if (p.coincideCedula(cedula) && p.verificarContrasenia(contrasenia)) {
                return p;
            }
        }
        // Buscar en administradores
        for (Administrador a : administradores) {
            if (a.coincideCedula(cedula) && a.verificarContrasenia(contrasenia)) {
                return a;
            }
        }
        return null; 
    }


    //COSAS PARA PROPIETARIOS
    public void agregarPropietario(Propietario propietario) throws PeajeException{
        if(propietario == null) return;
        //VALIDAMOS QUE LA MATRICULA NO EXISTA YA 
        for(Vehiculo v : propietario.getVehiculos()){
            if(buscarPropietarioPorMatricula(v.getMatricula()) != null) { 
                throw new PeajeException("Ya existe un propietario con un vehiculo de la misma matricula: " + v.getMatricula());
            }
        }
        propietarios.add(propietario);
    }

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
    
    public Propietario buscarPropietarioPorCedua(String cedula){
        if(cedula == null || cedula.isEmpty()) return null;

        for(Propietario p : propietarios){
            if(p.coincideCedula(cedula)){
                return p;
            }
        }
        return null;
    }
    
    public void asignarBonificacionPropietarios(String cedula, Bonificacion b) throws PeajeException {
        Propietario p = buscarPropietarioPorCedua(cedula);
        if(p != null && b !=null){
            p.agregarBonificacion(b);
        } else {
            throw new PeajeException("No se pudo asignar la bonificación.");
        }
    }

    public void cambiarEstadoPropietario(String cedula, Estado e) throws PeajeException {
        Propietario p = buscarPropietarioPorCedua(cedula);
        if(p != null && e !=null){
            p.setEstado(e);
        } else {
            throw new PeajeException("No se pudo cambiar el estado del propietario.");
        }
    }





    //COSAS PARA ADMINISTRADORES
    public void agregarAdministrador(Administrador administrador) {
        if(administrador == null) return;
        administradores.add(administrador);
    }

    public Administrador buscarAdministradorCedula(String cedula) {
        if(cedula == null || cedula.isEmpty())   return null;
        
        for (Administrador a : administradores) {
            if (a.coincideCedula(cedula)) {
                return a;
            }
        }
        return null; 
    }

    public List<Propietario> listaPropietarios() {
        return new ArrayList<>(propietarios);
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

   



}
