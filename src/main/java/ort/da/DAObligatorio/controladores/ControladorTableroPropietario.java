package ort.da.DAObligatorio.controladores;

import java.util.ArrayList;
import java.util.List;


import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ort.da.DAObligatorio.dtos.BonificacionDto;
import ort.da.DAObligatorio.dtos.NotificacionDto;
import ort.da.DAObligatorio.dtos.PorpietarioTableroDto;
import ort.da.DAObligatorio.dtos.TransitoDto;
import ort.da.DAObligatorio.dtos.VehiculoTableroDto;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.modelo.vehiculos.Vehiculo;
import ort.da.DAObligatorio.observador.Observable;
import ort.da.DAObligatorio.observador.Observador;
import ort.da.DAObligatorio.modelo.Sesion;
import ort.da.DAObligatorio.modelo.peajes.AsignacionDeBonificacion;
import ort.da.DAObligatorio.modelo.peajes.Notificacion;
import ort.da.DAObligatorio.modelo.peajes.Transito;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;
import ort.da.DAObligatorio.utils.ConexionNavegador;


@RestController
@RequestMapping("/tablero")
@Scope("session") 
public class ControladorTableroPropietario implements Observador{

    private final Fachada f = Fachada.getInstancia();
    private final ConexionNavegador conexionNavegador;

    private Propietario propietarioSesion;

    public ControladorTableroPropietario(ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

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
        //guaro el propietario de la sesion
        this.propietarioSesion = (Propietario) usuarioConectado;
        //me registro como observador
        f.agregarObservador(this);


        //armo el dto del tablero
         PorpietarioTableroDto dto = armarTablero(propietarioSesion);
    


        //devuelvo el dto armado
        return Respuesta.lista(new Respuesta("tableroPropietario", dto));


    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }



    @Override
    public void actualizar(Object evento, Observable origen) {
        
        if(propietarioSesion == null){
            return;
        }

        Object[]ev = (Object[]) evento;
        if(ev.length<2) return;
        
        //propietario del evento
        if(!(ev[0] instanceof Propietario)) return;
        Propietario propEvento = (Propietario) ev[0];

        if(this.propietarioSesion == null || !this.propietarioSesion.equals(propEvento)){
            return;
        }

        //tipo de evento
        String tipoEvento = (String) ev[1];

        if("ESTADO_CAMBIADO".equals(tipoEvento)||
            "ASIGNACION_BONIFICACION".equals(tipoEvento)||
            "TRANSITO_REGISTRADO".equals(tipoEvento)){
                //rearmo el dto actualizado
            PorpietarioTableroDto dto = armarTablero(this.propietarioSesion);
                //envio notificaion SSE
                conexionNavegador.enviarJSON(new Respuesta("tableroPropietario", dto));
        
        }



    
    
    }



     private PorpietarioTableroDto armarTablero(Propietario propietario) {
        PorpietarioTableroDto dto = new PorpietarioTableroDto();

        List<Transito> transitosModelo = f.obtenerTransitosPorPropietario(propietario);
        List<Vehiculo> vehiculos = propietario.getVehiculos();
        List<AsignacionDeBonificacion> asignaciones = f.obtenerAsignacionesPorPropietario(propietario);
        List<Notificacion> notificacionesModelo = f.obtenerNotificacionesDelPropietario(propietario);


        dto.setNombreCompleto(propietario.getNombreCompleto());
        dto.setEstado(propietario.getEstado() == null ? "Habilitado" : propietario.getEstado().nombre());
        dto.setSaldoActual(propietario.getSaldo());
        dto.setSaldoMinimoAlerta(propietario.getSaldoMinimoAlerta());

        // Vehículos
        List<VehiculoTableroDto> vehiculosDto = new ArrayList<>();

        
        for(Vehiculo v: vehiculos){
            VehiculoTableroDto vDto = new VehiculoTableroDto();
            vDto.setMatricula(v.getMatricula());
            vDto.setModelo(v.getModelo());
            vDto.setColor(v.getColor());
            vDto.setCategoria(v.getCategoria()!= null ? v.getCategoria().name() :null);
        
            int cantidad = 0;
            double total = 0;
            for(Transito t: transitosModelo){
                if(t !=null && t.getMatricula().equalsIgnoreCase(v.getMatricula()));{
                    cantidad++;
                    total += t.getMontoCobrado();
                }
            }
            vDto.setCantidadTransitos(cantidad);
            vDto.setTotalGastado(total);
        
            vehiculosDto.add(vDto);
        
        }

         dto.setVehiculos(vehiculosDto);
        // int cantidadVehiculos = (vehiculos == null) ? 0 : vehiculos.size();
        // dto.setCantidadVehiculos(cantidadVehiculos);
        // dto.setVehiculos(vehiculosDto);

        

        // Tránsitos
        List<TransitoDto> transitosDto = new ArrayList<TransitoDto>();
        
        if (transitosModelo != null) {
            for (Transito t : transitosModelo) {
                if(t !=null){
                    transitosDto.add(new TransitoDto(t));
                }
            }
        }

        dto.setTransitos(transitosDto);
        dto.setCantidadTransitos(transitosDto.size());

        // Bonificaciones
        List<BonificacionDto> bonificacionesDto = new ArrayList<>();
        
        if (asignaciones != null) {
            for (AsignacionDeBonificacion a : asignaciones) {
                bonificacionesDto.add(new BonificacionDto(a));
            }
        }
        dto.setBonificaciones(bonificacionesDto);

        // Notificaciones
        List<NotificacionDto> notisDto = new ArrayList<>();
        
        if (notificacionesModelo != null) {
            for (Notificacion n : notificacionesModelo) {
                notisDto.add(new NotificacionDto(n));
            }
        }
        dto.setNotificaciones(notisDto);

        return dto;
    }
}

     

