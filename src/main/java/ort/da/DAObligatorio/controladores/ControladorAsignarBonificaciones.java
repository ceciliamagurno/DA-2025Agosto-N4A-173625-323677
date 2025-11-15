package ort.da.DAObligatorio.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Respuesta> asignarBonificacion(@RequestParam String cedula, @RequestParam String nombreBonificacion, @RequestParam String nombrePuesto) {
        Propietario p = f.buscarPropietarioPorCedula(cedula);
        if (p == null) return Respuesta.lista(new Respuesta("mensaje","no existe el propietario"));

        if (nombreBonificacion == null || nombreBonificacion.trim().isEmpty()) return Respuesta.lista(new Respuesta("mensaje","Debe especificar una bonificación"));
        if (nombrePuesto == null || nombrePuesto.trim().isEmpty()) return Respuesta.lista(new Respuesta("mensaje","Debe especificar un puesto"));

        Bonificacion bon = null;
        for (Bonificacion b : f.getBonificaciones()) {
            if (b != null && b.getNombre() != null && b.getNombre().equalsIgnoreCase(nombreBonificacion)) { bon = b; break; }
        }
        Puesto puesto = null;
        for (Puesto pu : f.getPuestos()) {
            if (pu != null && pu.getNombre() != null && pu.getNombre().equalsIgnoreCase(nombrePuesto)) { puesto = pu; break; }
        }

        if (bon == null) return Respuesta.lista(new Respuesta("mensaje","Bonificación no encontrada"));
        if (puesto == null) return Respuesta.lista(new Respuesta("mensaje","Puesto no encontrado"));

        List<AsignacionDeBonificacion> asignacionesExistentes = f.obtenerAsignacionesPorPropietario(p);
        for (AsignacionDeBonificacion a : asignacionesExistentes) {
            if (a != null && a.getPuesto() != null && a.getPuesto().getNombre() != null && a.getPuesto().getNombre().equalsIgnoreCase(puesto.getNombre())) {
                return Respuesta.lista(new Respuesta("mensaje","Ya tiene una bonificación asignada para ese puesto"));
            }
        }

        if (p.getEstado() != null && !p.getEstado().permiteAsignarBonificacion()) {
            return Respuesta.lista(new Respuesta("mensaje","El propietario esta deshabilitado. No se pueden asignar bonificaciones"));
        }

        try {
            f.asignarBonificacionAPropietario(p, bon, puesto);
        } catch (Exception ex) {
            return Respuesta.lista(new Respuesta("mensaje","No se pudo asignar la bonificación: " + ex.getMessage()));
        }

        List<AsignacionDeBonificacion> asignacionesActual = f.obtenerAsignacionesPorPropietario(p);
        return Respuesta.lista(new Respuesta("asignaciones", asignacionesActual), new Respuesta("propietario", new ort.da.DAObligatorio.dtos.PropietarioDto(p)));
    }

}
