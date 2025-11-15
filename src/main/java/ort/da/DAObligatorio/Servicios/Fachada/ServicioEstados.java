package ort.da.DAObligatorio.servicios.fachada;

import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.estados.EstadoDeshabilitado;
import ort.da.DAObligatorio.modelo.estados.EstadoHabilitado;
import ort.da.DAObligatorio.modelo.estados.EstadoPenalizado;
import ort.da.DAObligatorio.modelo.estados.EstadoSuspendido;

public class ServicioEstados {
    
    private List<Estado> estados;

    public ServicioEstados() {
        this.estados = new ArrayList<>();
        //  estados disponibles
        this.estados.add(new EstadoHabilitado());
        this.estados.add(new EstadoDeshabilitado());
        this.estados.add(new EstadoSuspendido());
        this.estados.add(new EstadoPenalizado());    
    }

    public List<Estado> obtenerEstadosDisponibles(){
        return new ArrayList<Estado>(estados);
    }

    public Estado obtenerEstadoPorNombre(String nombreEstado){
        if(nombreEstado == null) return null;
        
        String buscado = nombreEstado.trim();
        
        for(Estado e: this.estados){
            if(e != null && e.nombre() != null && e.nombre().equalsIgnoreCase(buscado)){
                return e;
            }
        }
        return null;
    }
    
}
