package ort.da.DAObligatorio.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;

import ort.da.DAObligatorio.dtos.PropietarioDto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/propietarios")
public class ControladorPropietarios {

    private final Fachada f = Fachada.getInstancia();

    @GetMapping("/lista")
    public List<Respuesta> listarPropietarios() {
        List<PropietarioDto> propietariosDto = new ArrayList<>();
        for (Propietario p : f.listarPropietarios()) {
            propietariosDto.add(new PropietarioDto(p));
        }
        return Respuesta.lista(new Respuesta("propietarios", propietariosDto));
    }

    @GetMapping("/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) {
        Propietario p = f.buscarPropietarioPorCedula(cedula);
        if (p == null) {
            return Respuesta.lista(new Respuesta("mensaje", "No existe el propietario"));
        }

        PropietarioDto dto = new PropietarioDto(p);

        List<Estado> estados = f.getEstadosDisponibles();
        List<Map<String, String>> listaEstados = new ArrayList<>();
        if (estados != null) {
            for (Estado e : estados) {
                if (e != null) {
                    Map<String, String> m = new HashMap<>();
                    m.put("nombre", e.nombre());
                    listaEstados.add(m);
                }
            }
        }

        // Asignaciones del propietario 
        List<AsignacionDeBonificacion> asignaciones = f.obtenerAsignacionesPorPropietario(p);
        List<Map<String, Object>> listaAsign = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (asignaciones != null) {
            for (AsignacionDeBonificacion a : asignaciones) {
                if (a == null) continue;
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

        return Respuesta.lista(new Respuesta("propietario", dto), new Respuesta("estados", listaEstados), new Respuesta("asignaciones", listaAsign));
    }

}



