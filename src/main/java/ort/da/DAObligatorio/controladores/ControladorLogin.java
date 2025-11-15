package ort.da.DAObligatorio.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.DAObligatorio.excepciones.PeajeException;
import ort.da.DAObligatorio.modelo.usuarios.Administrador;
import ort.da.DAObligatorio.modelo.usuarios.Propietario;
import ort.da.DAObligatorio.modelo.usuarios.Usuario;
import ort.da.DAObligatorio.servicios.fachada.Fachada;
import ort.da.DAObligatorio.utils.Respuesta;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/login")
public class ControladorLogin {


    @PostMapping("/login")
    public List<Respuesta> login(HttpSession sesionHttp, 
                                @RequestParam String cedula,   
                                @RequestParam String contrasenia) throws PeajeException {
        
                                    // Intentamos autenticar el usuario
        Usuario usuarioLogueado  = Fachada.getInstancia().login(cedula, contrasenia);
        if (usuarioLogueado == null) {
            throw new PeajeException("Acceso denegado");
        }

        //verificamos si el propietario esta deshabilitado para curso alternativo
        if(usuarioLogueado instanceof Propietario){
            Propietario p = (Propietario) usuarioLogueado;
            if(p.getEstado() == null || !p.getEstado().permiteLogin()){
                throw new PeajeException("Usuario deshabilitado, no puede ingresar al sistema");
            }
        }
        // Guardamos el usuario en la sesión 
        sesionHttp.setAttribute("UsuarioConectado", usuarioLogueado);

        // Redirigimos según tipo de usuario
        if (usuarioLogueado instanceof Administrador) {
            return Respuesta.lista(new Respuesta("loginExitoso", "admin.html"));
        } else if (usuarioLogueado instanceof Propietario) {
            return Respuesta.lista(new Respuesta("loginExitoso", "menu.html"));
        } else {
            throw new PeajeException("Tipo de usuario no reconocido");
        }
    }

}
 