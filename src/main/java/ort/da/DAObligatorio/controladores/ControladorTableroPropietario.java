package ort.da.DAObligatorio.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.DAObligatorio.dtos.BonificacionDto;
import ort.da.DAObligatorio.dtos.NotificacionDto;
import ort.da.DAObligatorio.dtos.PorpietarioTableroDto;

import ort.da.DAObligatorio.dtos.TransitoDto;
import ort.da.DAObligatorio.dtos.VehiculoTableroDto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.modelo.Sesion;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Notificacion;
import ort.da.DAObligatorio.modelo.peajes.Transito;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/tablero")
public class ControladorTableroPropietario {

    private final Fachada f = Fachada.getInstancia();

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista (
        @SessionAttribute(name = "sesion", required = false) Sesion sesion) {

        //Sin sesión "usuarioNoAutenticado" y vamos al lgin
        if (sesion == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        Usuario usuarioConectado = sesion.getUsuario();

        //no es propietario, mensaje simple
        if (!(usuarioConectado instanceof Propietario)) {
            return Respuesta.lista(new Respuesta("mensaje", "usuario no es propietario"));
        }

        //Es propietario armo dto del tablero
        Propietario propietario = (Propietario) usuarioConectado;
        PorpietarioTableroDto dto = new PorpietarioTableroDto();
        dto.setNombreCompleto(propietario.getNombreCompleto());
        dto.setEstado(propietario.getEstado() == null ? "Habilitado" : propietario.getEstado().nombre());
        dto.setSaldoActual(propietario.getSaldo());
        dto.setSaldoMinimoAlerta(propietario.getSaldoMinimoAlerta());


        //cantidad de vehiculos registrados
        List<Vehiculo> vehiculos = propietario.getVehiculos();
        List<VehiculoTableroDto> vehiculosDto = new ArrayList<>();
        Map<String, VehiculoTableroDto> vehiculosPorMatricula = new HashMap<>();

        if (vehiculos != null) {
            for (Vehiculo v : vehiculos) {
                if (v != null) {
                    VehiculoTableroDto vDto = new VehiculoTableroDto(v);
                    vehiculosDto.add(vDto);
                    vehiculosPorMatricula.put(v.getMatricula(), vDto);
                }
            }
        }

        dto.setVehiculos(vehiculosDto);
        dto.setCantidadVehiculos(vehiculosDto.size());

        
        List<Transito> transitosModelo = f.obtenerTransitosPorPropietario(propietario);
        List<TransitoDto> transitosDto = new ArrayList<TransitoDto>();

        if (transitosModelo != null) {
            for (Transito t : transitosModelo) {
                transitosDto.add(new TransitoDto(t));

                // acumular datos en el DTO del vehículo
                String mat = t.getMatricula();
                if(mat != null){
                    VehiculoTableroDto vDto = vehiculosPorMatricula.get(mat);
                    if (vDto != null) {
                        vDto.setCantidadTransitos(vDto.getCantidadTransitos() + 1);
                        vDto.setTotalGastado(vDto.getTotalGastado() + t.getMontoCobrado());
                    }
                }
            }
        }

        dto.setTransitos(transitosDto);
        dto.setCantidadTransitos(transitosDto.size());


        //Bonificaciones del propietario
        List<BonificacionDto> bonificacionesDto = new ArrayList<BonificacionDto>();
        List<AsignacionDeBonificacion> asignaciones = f.obtenerAsignacionesPorPropietario(propietario);

        if(asignaciones!= null){
            for(AsignacionDeBonificacion a : asignaciones){
                bonificacionesDto.add(new BonificacionDto(a));
            }
        }
        dto.setBonificaciones(bonificacionesDto);

        //Noticicaciones
        List<NotificacionDto> notisDto = new ArrayList<NotificacionDto>();
        List<Notificacion> notificacionessModelo = f.obtenerNotificacionesDelPropietario(propietario);

        if (notificacionessModelo != null) {
                for (Notificacion n : notificacionessModelo) {
                    notisDto.add(new NotificacionDto(n));
                }
            }
            dto.setNotificaciones(notisDto);


        //devuelvo el dto armado
        return Respuesta.lista(new Respuesta("tableroPropietario", dto));


    }

}
