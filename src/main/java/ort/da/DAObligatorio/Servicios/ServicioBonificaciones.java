package ort.da.DAObligatorio.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.bonificaciones.ReglaBonificacion;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.peajes.Tarifa;
import ort.da.DAObligatorio.modelo.peajes.Transito;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;


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
        
    public void asignarBonificacionAPropietario(Propietario propietario, Bonificacion b, Puesto puesto) throws PeajeException{

        if(propietario == null){
            throw new PeajeException("Propietario inválido para asignar bonificación.");
        }
        if(b == null){
            throw new PeajeException("Bonificación inválida para asignar.");
        }
        if(puesto == null){
            throw new PeajeException("Puesto inválido para asignar bonificación.");
        }
        // Verificar si el estado del propietario permite asignarle bonificaciones
        if (propietario.getEstado() != null && !propietario.getEstado().aplicaBonificaciones()) {
            throw new PeajeException("No se pueden asignar bonificaciones al propietario en su estado actual.");
        }

        //verificamos que no exista alguna asignacion igual
        List<AsignacionDeBonificacion> asignacionesPropietario = propietario.getAsignacionesBonificacion();
        for(AsignacionDeBonificacion a : asignacionesPropietario){
            if(a != null && a.getBonificacion() != null 
                && a.getBonificacion().equals(b) && a.getPuesto() != null 
                && a.getPuesto().equals(puesto)){
                throw new PeajeException("El propietario ya tiene asignada esa bonificación en ese puesto.");
            }
        }

        AsignacionDeBonificacion asignacion = new AsignacionDeBonificacion(
            propietario,
            b,
            puesto,
            LocalDateTime.now()
        );
        asignaciones.add(asignacion);

        propietario.agregarAsignacionBonificacion(asignacion);

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

    public Bonificacion obtenerBonificacionAplicable(Propietario propietario,
                                                    Puesto puesto,
                                                    Vehiculo vehiculo,
                                                    LocalDateTime fechaHora,
                                                    List<Transito> transitosDelPropietario) {
        Bonificacion bonif = null;
        
        //busco las asignaciones del propietario para el puesto
        for(AsignacionDeBonificacion ab : asignaciones){
            if(ab != null && ab.getPropietario() == propietario && ab.getPuesto() != null && ab.getPuesto().equals(puesto)){
                bonif = ab.getBonificacion();
                break;
            }
        }
        return bonif;
    }

    public double calcularMontoConBonificacion(Bonificacion bonificacion,
                                           Tarifa tarifa,
                                           Propietario propietario,
                                           Vehiculo vehiculo,
                                           Puesto puesto,
                                           LocalDateTime fechaHora) {

        // Si no hay bonificación o no hay regla, devolvemos el monto base
        if (tarifa == null) {
            return 0; // o lanzar PeajeException si preferís
        }
        if (bonificacion == null || bonificacion.getRegla() == null) {
            return tarifa.getMonto();
        }

        ReglaBonificacion regla = bonificacion.getRegla();
        // Usamos el método de la interfaz
        return regla.aplicar(tarifa, propietario, vehiculo, puesto, fechaHora);
    }

    public Bonificacion obtenerBonificacionPara(Propietario propietario, Puesto puesto) {
        if(propietario == null || puesto == null){
            return null;
        }
        for(AsignacionDeBonificacion ab : asignaciones){
            if(ab != null && ab.getPropietario().coincideCedula(ab.getPropietario().getCedula()) 
                && ab.getPuesto() != null && ab.getPuesto().getNombre().equalsIgnoreCase(puesto.getNombre())){
                return ab.getBonificacion();
            }
        }
        return null;
    }
}
