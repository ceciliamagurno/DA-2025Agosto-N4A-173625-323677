package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.Administrador;
import ort.da.DAObligatorio.modelo.Bonificacion;
import ort.da.DAObligatorio.modelo.Estado;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Usuario;
import ort.da.DAObligatorio.modelo.Vehiculo;

public class ServicioUsuarios {
    private List<Usuario> usuarios;
    //poner lista de sesiones si hace falta, crear la clase etc etc

    public ServicioUsuarios() {
        this.usuarios = new ArrayList<>();
    }


    //buscamos en todos los usuarios
    public Usuario login(String cedula, String contrasenia) {
        if(cedula == null) return null;
        for (Usuario u : usuarios) {
            if (u.coincideCedula(cedula) && u.verificarContrasenia(contrasenia)) {
                return u;
            }
        }
        
        return null; 
    }


    //COSAS PARA PROPIETARIOS
    public void agregarPropietario(Propietario propietario) throws PeajeException{
        if(propietario == null) return;
        //VALIDAMOS QUE LA MATRICULA NO EXISTA YA 
        for(Usuario u : usuarios){
            if(u instanceof Propietario){//cambiar estas partes del codigo para que apliquen experto u.esPropietario() metodo que devuelve  u instanceof Propietario aca y en todos los metodos similares
                Propietario p = (Propietario) u;
                for(Vehiculo vehiculiExistente : p.getVehiculos()){
                    String matriculaExistente = vehiculiExistente.getMatricula();
                    if(matriculaExistente == null) continue;
                    if(propietario.tieneVehiculoConMatricula(matriculaExistente)){
                        throw new PeajeException("Ya existe un vehiculo con la misma matricula: " + matriculaExistente);
                    }
                }
            }
        }
        
        usuarios.add(propietario);
    }

    public Propietario buscarPropietarioPorMatricula(String matricula) {
        if(matricula == null || matricula.isEmpty()) return null;
        
        for (Usuario u : usuarios) {
            if(u instanceof Propietario){
                Propietario p = (Propietario) u;
                if(p.tieneVehiculoConMatricula(matricula)) return p;
            
            } 
        }
        return null; // Retorna null si no se encuentra ningún propietario con esa matrícula
    }
    
    public Propietario buscarPropietarioPorCedula(String cedula){
        if(cedula == null || cedula.isEmpty()) return null;

         for (Usuario u : usuarios) {
            if(u instanceof Propietario){
                Propietario p = (Propietario) u;
                 if(p.coincideCedula(cedula)) return p;

            }
        }
        return null;
    }
    
    public void asignarBonificacionPropietarios(String cedula, Bonificacion b) throws PeajeException {
        Propietario p = buscarPropietarioPorCedula(cedula);
        if(p != null && b !=null){
            p.agregarBonificacion(b);
        } else {
            throw new PeajeException("No se pudo asignar la bonificación.");
        }
    }

    public void cambiarEstadoPropietario(String cedula, Estado e) throws PeajeException {
        Propietario p = buscarPropietarioPorCedula(cedula);
        if(p != null && e !=null){
            p.setEstado(e);
        } else {
            throw new PeajeException("No se pudo cambiar el estado del propietario.");
        }
    }





    //COSAS PARA ADMINISTRADORES
    public void agregarAdministrador(Administrador administrador) {
        if(administrador == null) return;
        usuarios.add(administrador);
    }

    public Administrador buscarAdministradorCedula(String cedula) {
        if(cedula == null || cedula.isEmpty())   return null;
        
        for (Usuario u : usuarios) {
            if (u instanceof Administrador) {
                Administrador a = (Administrador) u;
                if (a.coincideCedula(cedula)) return a;
            }
        }
        return null; 
    }

    public List<Propietario> listaPropietarios() {
        return usuarios.stream()
                .filter(u -> u instanceof Propietario)
                .map(u -> (Propietario) u)
                .collect(Collectors.toList());
    }

    public List<Administrador> listaAdministradores() {
        return usuarios.stream()
                .filter(u -> u instanceof Administrador)
                .map(u -> (Administrador) u)
                .collect(Collectors.toList());
    }

   



}
