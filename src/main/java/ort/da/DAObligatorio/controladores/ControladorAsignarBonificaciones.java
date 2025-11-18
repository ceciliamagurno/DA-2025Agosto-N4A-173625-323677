package ort.da.DAObligatorio.controladores;

import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ort.da.DAObligatorio.dtos.PropietarioDto;
import ort.da.DAObligatorio.dtos.AsignacionBonificacionDto;
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
        //actualizo asignaciones y las transformo a DTOs
        List<AsignacionDeBonificacion> asignacionesActual = f.obtenerAsignacionesPorPropietario(p);
        List<AsignacionBonificacionDto> asignDto = new ArrayList<>();
        if (asignacionesActual != null) {
            for (AsignacionDeBonificacion a : asignacionesActual) {
                if (a != null) asignDto.add(new AsignacionBonificacionDto(a));
            }
        }

        return Respuesta.lista(new Respuesta("asignaciones", asignDto), new Respuesta("propietario", new PropietarioDto(p)));
    }

    @GetMapping("/listas")
    public List<Respuesta> listarRecursos() {
        List<String> nombresBon = new ArrayList<>();
        List<String> nombresPuestos = new ArrayList<>();

        List<Bonificacion> bns = f.obtenerBonificaciones();
        if (bns != null) {
            for (Bonificacion b : bns) {
                if (b != null)
                    nombresBon.add(b.getNombre());
            }
        }

        List<Puesto> pts = f.getPuestos();
        if (pts != null) {
            for (Puesto p : pts) {
                if (p != null)
                    nombresPuestos.add(p.getNombre());
            }
        }

        return Respuesta.lista(new Respuesta("bonificaciones", nombresBon), new Respuesta("puestos", nombresPuestos));
    }

    @GetMapping("/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) {
        Propietario p = f.buscarPropietarioPorCedula(cedula);
        if (p == null) {
            return Respuesta.lista(new Respuesta("mensaje", "no existe el propietario"));
        }

        // Propietario DTO
        PropietarioDto dto = new PropietarioDto(p);

        // Asignaciones
        List<AsignacionDeBonificacion> asignaciones = f.obtenerAsignacionesPorPropietario(p);
        List<Map<String, Object>> listaAsign = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (asignaciones != null) {
            for (AsignacionDeBonificacion a : asignaciones) {
                if (a == null)
                    continue;
                Map<String, Object> m = new HashMap<>();
                Map<String, String> bon = new HashMap<>();
                Map<String, String> puesto = new HashMap<>();
                if (a.getBonificacion() != null)
                    bon.put("nombre", a.getBonificacion().getNombre());
                if (a.getPuesto() != null)
                    puesto.put("nombre", a.getPuesto().getNombre());
                m.put("bonificacion", bon);
                m.put("puesto", puesto);
                m.put("fechaAsignacion", a.getFechaAlta() == null ? "" : a.getFechaAlta().format(fmt));
                listaAsign.add(m);
            }
        }

        return Respuesta.lista(new Respuesta("propietario", dto), new Respuesta("asignaciones", listaAsign));
    }
}