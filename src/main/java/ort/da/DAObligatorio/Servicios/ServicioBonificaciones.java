package ort.da.DAObligatorio.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.Bonificacion;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Puesto;

public class ServicioBonificaciones {
    
    private List<Bonificacion> bonificaciones;
    private List<AsignacionDeBonificacion> asignaciones;

    public ServicioBonificaciones() {
        this.bonificaciones = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
    }

    public List<Bonificacion> obtenerBonificaciones(){
        return bonificaciones;
    }

    public void agregarBonificacion(Bonificacion bn){
        if(bn != null && !bonificaciones.contains(bn)){
            bonificaciones.add(bn);
        }
    }
        //aca hay que hacer las validaciones correspondientes al CU de asignar bonificaciones
    public void asignarBonificacionAPropietario(Propietario cedula, Bonificacion b, Puesto p) throws PeajeException{

        if(cedula == null){
            throw new PeajeException("Cédula inválida para asignar bonificación.");
        }
        if(b == null){
            throw new PeajeException("Bonificación inválida para asignar.");
        }
        if(p == null){
            throw new PeajeException("Puesto inválido para asignar bonificación.");
        }
        AsignacionDeBonificacion asignacion = new AsignacionDeBonificacion(cedula, b, p, new Date());
        asignaciones.add(asignacion);
    }

}
