package ort.da.DAObligatorio.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ort.da.DAObligatorio.dtos.PropietarioDto;
import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.bonificaciones.Bonificacion;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Puesto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/bonificaciones")
public class ControladorAsignarBonificaciones {

    private final Fachada f = Fachada.getInstancia();

    @PostMapping("/asignar")

    public List<Respuesta> asignarBonificacion(@RequestParam String cedula, 
                                                @RequestParam String nombreBonificacion, 
                                                @RequestParam String nombrePuesto) {
        
        //Propietario
        Propietario p = f.buscarPropietarioPorCedula(cedula);
        if (p == null) {
           return Respuesta.lista(new Respuesta("mensaje","no existe el propietario"));
        }

        //valido los datos basicos
        if (nombreBonificacion == null || nombreBonificacion.trim().isEmpty()) {
            return Respuesta.lista(new Respuesta("mensaje","Debe especificar una bonificación"));
        }
        if (nombrePuesto == null || nombrePuesto.trim().isEmpty()) {
            return Respuesta.lista(new Respuesta("mensaje","Debe especificar un puesto"));
        }

        //busco bonificacion y puesto
        Bonificacion bon = f.buscarBonificacion(nombreBonificacion);
        if (bon == null) {
            return Respuesta.lista(new Respuesta("mensaje","Bonificación no encontrada"));
        }
        
        Puesto puesto = f.buscarPuestoPorNombre(nombrePuesto);
        if (puesto == null) {
            return Respuesta.lista(new Respuesta("mensaje","Puesto no encontrado"));
        }

        //verifico que no tenga ya una asignacion para ese puesto
        List<AsignacionDeBonificacion> asignacionesExistentes = f.obtenerAsignacionesPorPropietario(p);
            if(asignacionesExistentes != null){
                for (AsignacionDeBonificacion a : asignacionesExistentes) {
                    if (a != null && a.getPuesto() != null && 
                    a.getPuesto().getNombre() != null && 
                    a.getPuesto().getNombre().equalsIgnoreCase(puesto.getNombre())) {

                        return Respuesta.lista(new Respuesta("mensaje","Ya tiene una bonificación asignada para ese puesto"));
                    }
                }
            }
                
        //valido los datos del propietario
        if (p.getEstado() != null && !p.getEstado().aplicaBonificaciones()) {
            return Respuesta.lista(new Respuesta("mensaje","El propietario esta deshabilitado. No se pueden asignar bonificaciones"));
        }
        //asigno bonificacion 
        try {
            f.asignarBonificacionAPropietario(p, bon, puesto);
        } catch (PeajeException ex) {
            return Respuesta.lista(new Respuesta("mensaje","No se pudo asignar la bonificación: " + ex.getMessage()));
        }
        //avtualizo asignaciones 
        List<AsignacionDeBonificacion> asignacionesActual = f.obtenerAsignacionesPorPropietario(p);
        
        return Respuesta.lista(new Respuesta("asignaciones", asignacionesActual), new Respuesta("propietario", new PropietarioDto(p)));
    }

}
