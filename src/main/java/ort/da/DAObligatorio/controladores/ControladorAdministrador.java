package ort.da.DAObligatorio.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

//import java.util.List;

//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import ort.da.DAObligatorio.modelo.Sesion;
import ort.da.DAObligatorio.modelo.usuarios.Administrador;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.utils.Respuesta;

//import ort.da.DAObligatorio.utils.Respuesta;

@RestController
@RequestMapping("/administrador")
public class ControladorAdministrador {

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(
        name = "sesion", required = false) Sesion  sesion){
        

        //no hay sesion redirijo al login
        if(sesion == null || sesion.getUsuario() == null){
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        Usuario u = sesion.getUsuario();

        //si no es admin redirijo al login
        if(!(u instanceof Administrador)){
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        Administrador admin = (Administrador) u;

        return Respuesta.lista(
            new Respuesta("adminNombre", admin.getNombreCompleto()),
            new Respuesta("vista", "admin.html")
        );
    }        

}
