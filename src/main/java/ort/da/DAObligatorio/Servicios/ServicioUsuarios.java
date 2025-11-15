package ort.da.DAObligatorio.servicios;


import java.util.ArrayList;
import java.util.List;


import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.usuarios.Administrador;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.observador.Observable;

public class ServicioUsuarios extends Observable{
    private List<Usuario> usuarios;

    public ServicioUsuarios() {
        this.usuarios = new ArrayList<>();
    }


    //LOGIN
    public Usuario login(String cedula, String contrasenia) {
        Usuario u = buscarUsuarioPorCedula(cedula);
        if( u != null && u.verificarContrasenia(contrasenia)){
            return u;
        }
        return null;
    }


    //ALTAS
    public void agregarAdministrador(Administrador admin){
        if(admin != null && !usuarios.contains(admin)){
            usuarios.add(admin);
        }
    }

    public void agregarPropietario(Propietario prop){
        if(prop != null && !usuarios.contains(prop)){
            usuarios.add(prop);
        }
    }

    //BUSCAR POR CEDULA
    public Propietario buscarPropietarioPorCedula(String cedula){
        return traerUsuarioP(cedula);
    }

    public Administrador buscarAdministradorPorCedula(String cedula){
        return traerUsuarioA(cedula);
    }

    public Propietario buscarPropietarioPorMatricula(String matricula){
        for(Usuario u : usuarios){
            if(u instanceof Propietario){
                Propietario p = (Propietario) u;
                if(p.poseeVehiculoConMatricula(matricula)){
                    return p;
                }
            }
        }
        return null;
    }

    //LISTADOS

    public List<Propietario> listarPropietarios(){
        List<Propietario> resultado = new ArrayList<>();
        for(Usuario u : usuarios){
            if(u instanceof Propietario){
                resultado.add((Propietario) u);
            }
        }
        return resultado;
    }

    public List<Administrador> listarAdministradores(){
        List<Administrador> resultado = new ArrayList<>();
        for(Usuario u : usuarios){
            if(u instanceof Administrador){
                resultado.add((Administrador) u);
            }
        }
        return resultado;
    }

    //ESTADO Y BONIFICACION

    public void cambiarEstadoPropietario(String cedula, Estado e) throws PeajeException{
        Propietario p = buscarPropietarioPorCedula(cedula);
        if(p ==null){
            throw new PeajeException("No existe propietario con esa cedula.");
        }
        p.setEstado(e);
    }

    

        
       
    //Auxiliares 

    private Usuario buscarUsuarioPorCedula(String cedula){
        if(cedula == null) return null;

        for(Usuario u : usuarios){
            if(u.coincideCedula(cedula)){
                return u;
            }
        }
        return null;
    }

    private Propietario traerUsuarioP(String cedula){
        Usuario u = buscarUsuarioPorCedula(cedula);
        if(u != null && u instanceof Propietario){
            return (Propietario) u;
        }
        return null;
    }

    private Administrador traerUsuarioA(String cedula){
        Usuario u = buscarUsuarioPorCedula(cedula);
        if(u != null && u instanceof Administrador){
            return (Administrador) u;
        }
        return null;
    }
}

   

