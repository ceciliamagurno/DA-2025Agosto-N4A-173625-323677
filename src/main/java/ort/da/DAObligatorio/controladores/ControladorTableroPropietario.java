package ort.da.DAObligatorio.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.DAObligatorio.dtos.BonificacionDto;
import ort.da.DAObligatorio.dtos.NotificacionDto;
import ort.da.DAObligatorio.dtos.PorpietarioTableroDto;

import ort.da.DAObligatorio.dtos.TransitoDto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
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
    public List<Respuesta> vistaConectada (
        @SessionAttribute(name = "UsuarioConectado", required = false) Usuario usuarioConectado) {

        //Sin sesión "usuarioNoAutenticado" y vamos al lgin
        if (usuarioConectado == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

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
       int cantidadVehiculos = (vehiculos == null) ? 0 : vehiculos.size();
       dto.setCantidadVehiculos(cantidadVehiculos);


       List<String> matriculas = new ArrayList<String>();
       if(vehiculos != null){
            for(Vehiculo v : vehiculos){
                if(v !=null && v.getMatricula() != null){
                    matriculas.add(v.getMatricula());
                }
            }
        }
        dto.setMatriculas(matriculas);

        //Transitos realizados por el propietario
        List<TransitoDto> transitosDto = new ArrayList<TransitoDto>();
        List<Transito> transitosModelo = f.obtenerTransitosPorPropietario(propietario);

        if(transitosModelo != null){
            for(Transito t : transitosModelo){
               transitosDto.add(new TransitoDto(t)); 
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
