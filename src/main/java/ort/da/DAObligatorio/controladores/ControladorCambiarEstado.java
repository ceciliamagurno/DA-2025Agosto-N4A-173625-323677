package ort.da.DAObligatorio.controladores;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ort.da.DAObligatorio.dtos.PropietarioDto;
import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.estados.Estado;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/estados")
public class ControladorCambiarEstado {

    private final Fachada f = Fachada.getInstancia();

    @PostMapping("/cambiar")
    public List<Respuesta> cambiarEstado(@RequestParam String cedula, 
                                        @RequestParam String nombreEstado) {



        // Buscar el propietario por cédula
        Propietario p = f.buscarPropietarioPorCedula(cedula);
        if (p == null) {
            return Respuesta.lista(new Respuesta("mensaje","no existe el propietario"));
        }

        Estado estadoActual = p.getEstado();
        if (estadoActual != null && estadoActual.nombre() != null && estadoActual.nombre().equalsIgnoreCase(nombreEstado)) {
            return Respuesta.lista(new Respuesta("mensaje","El propietario ya esta en estado " + estadoActual.nombre()));
        }

        //buscar el estado por nombre
        List<Estado> estados = f.getEstadosDisponibles();
        boolean existe = false;
        if (estados != null) {
            for (Estado e : estados) {
                if (e != null && e.nombre() != null &&
                    e.nombre().equalsIgnoreCase(nombreEstado)) {
                    existe = true;
                    break;
                }
            }
        }

        if(!existe){
            return Respuesta.lista(new Respuesta("mensaje","El estado " + nombreEstado + " no es válido."));
        }

        
        try{
            f.cambiarEstadoPropietario(cedula, nombreEstado);
        }catch (PeajeException ex){
            return Respuesta.lista(
                new Respuesta("mensaje", " No se pudo cambiar el estado " + ex.getMessage()));
        }
       
       
        //notificar cambio de estado
       LocalDateTime fechaHoraCambio = LocalDateTime.now();
       String mensajeNotificacion = "El estado de su cuenta ha sido cambiado a " + nombreEstado + " el " + fechaHoraCambio.toString();;
       
       f.agregarNotificacion(mensajeNotificacion, p); 
       //devolver respuesta exitosa
         return Respuesta.lista(new Respuesta("estadoCambiado", new PropietarioDto(p)));
    }
        
}
