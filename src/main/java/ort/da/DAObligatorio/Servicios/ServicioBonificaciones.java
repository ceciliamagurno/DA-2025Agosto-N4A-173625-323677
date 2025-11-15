package ort.da.DAObligatorio.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;


public class ServicioBonificaciones {
    
    private List<Bonificacion> bonificaciones;
    private List<AsignacionDeBonificacion> asignaciones;

    public ServicioBonificaciones() {
        this.bonificaciones = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
    }

    public List<Bonificacion> obtenerBonificaciones(){
        return new ArrayList<Bonificacion>(bonificaciones);
    }

    public void agregarBonificacion(Bonificacion bn){
        if(bn != null && !bonificaciones.contains(bn)){
            bonificaciones.add(bn);
        }
    }
        
    public void asignarBonificacionAPropietario(Propietario propietario, Bonificacion b, Puesto p) throws PeajeException{

        if(propietario == null){
            throw new PeajeException("Propietario inválido para asignar bonificación.");
        }
        if(b == null){
            throw new PeajeException("Bonificación inválida para asignar.");
        }
        if(p == null){
            throw new PeajeException("Puesto inválido para asignar bonificación.");
        }
        // Verificar si el estado del propietario permite asignarle bonificaciones
        if (propietario.getEstado() != null && !propietario.getEstado().aplicaBonificaciones()) {
            throw new PeajeException("No se pueden asignar bonificaciones al propietario en su estado actual.");
        }
        AsignacionDeBonificacion asignacion = new AsignacionDeBonificacion(
            propietario,
            b,
            p,
            LocalDateTime.now()
        );
        asignaciones.add(asignacion);

        propietario.agregarAsignacionBonificacion(asignacion);
    }

    public List<AsignacionDeBonificacion> obtenerAsignacionesPorPropietario(Propietario propietario) {
        List<AsignacionDeBonificacion> resultado = new ArrayList<AsignacionDeBonificacion>();
        if (propietario == null) return resultado;

        for (AsignacionDeBonificacion a : asignaciones) {
            if (a != null && a.getPropietario() != null && propietario.coincideCedula(a.getPropietario().getCedula())) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    public Bonificacion buscarBonificacionPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return null;
        }
        for (Bonificacion b : bonificaciones) {
            if (b != null && nombre.equalsIgnoreCase(b.getNombre())) {
                return b;
            }
        }
        return null;
    }

}
