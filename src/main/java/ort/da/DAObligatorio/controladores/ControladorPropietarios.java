package ort.da.DAObligatorio.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.DAObligatorio.dtos.PropietarioDto;
import ort.da.DAObligatorio.modelo.Propietario;
import ort.da.DAObligatorio.modelo.Usuario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/propietarios")
@Scope("session")
public class ControladorPropietarios {
   
    private final Fachada f = Fachada.getInstancia();


    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "UsuarioConectado", required = false) Usuario UsuarioConectado) {
        
        if (UsuarioConectado ==null) {
            return Respuesta.lista(propietariosRespuesta());
        } 
        if(UsuarioConectado instanceof Propietario) {
            return Respuesta.lista(propietariosRespuesta());
        }
        return Respuesta.lista(mensaje( "usuario no es propietario"));
    }

    private Respuesta mensaje(String msj) {
        return new Respuesta("mensaje", msj);
    }

    private Respuesta propietariosRespuesta() {
        List<PropietarioDto> propietariosDto = new ArrayList<>();
        for (Propietario p : f.getPropietarios()) {
            propietariosDto.add(new PropietarioDto(p));
        }
        return new Respuesta("propietarios", propietariosDto);
    }

    
}



